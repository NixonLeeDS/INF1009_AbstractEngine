package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.ICollidable;
import com.inf1009.engine.entity.StaticEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatorScreen implements Screen {

    private static final float AGENT_SPEED = 240f;
    private static final float FALL_SPEED = 140f;
    private static final int SPAWN_EVERY_FRAMES = 25;

    private final GameMaster game;
    private ShapeRenderer shape;
    private final Random rng = new Random();

    private DynamicEntity agentA;
    private DynamicEntity agentB;

    private StaticEntity wallLeft;
    private StaticEntity wallRight;

    private int frameCounter = 0;

    public SimulatorScreen(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {
        //reset game
        game.getEntityManager().clear();
        frameCounter = 0;

        shape = new ShapeRenderer();

        // Controlled agents (generic)
        agentA = new DynamicEntity(80, 80, 28, 28, AGENT_SPEED);
        agentB = new DynamicEntity(140, 80, 28, 28, AGENT_SPEED);

        // Static obstacles (generic)
        wallLeft = new StaticEntity(220, 0, 20, 480);
        wallRight = new StaticEntity(420, 0, 20, 480);

        game.getEntityManager().addEntity(agentA);
        game.getEntityManager().addEntity(agentB);
        game.getEntityManager().addEntity(wallLeft);
        game.getEntityManager().addEntity(wallRight);
    }

    @Override
    public void render(float dt) {
        // ESC returns to menu (demo state change)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getSceneManager().setScreen("start");
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Movement demo: two different input streams
        game.getMovementManager().applyInput(agentA, game.getIOManager().readP1(), dt);
        game.getMovementManager().applyInput(agentB, game.getIOManager().readP2(), dt);

        // Spawn falling objects periodically (scaling + lifecycle demo)
        frameCounter++;
        if (frameCounter % SPAWN_EVERY_FRAMES == 0) {
            spawnFallingObject();
        }

        // Update entities
        game.getEntityManager().update(dt);

        // Build collidables generically
        List<ICollidable> collidables = new ArrayList<>();
        game.getEntityManager().getEntities().forEach(e -> {
            if (e instanceof ICollidable) collidables.add((ICollidable) e);
        });

        // Collision demo
        game.getCollisionManager().update(collidables);

        // Render entities (simple shapes)
        shape.begin(ShapeRenderer.ShapeType.Filled);
        game.getEntityManager().getEntities().forEach(e ->
                shape.rect(e.getX(), e.getY(), e.getW(), e.getH())
        );
        shape.end();
    }

    private void spawnFallingObject() {
        float x = 40 + rng.nextFloat() * 520f;
        float y = 480f;

        DynamicEntity falling = new DynamicEntity(x, y, 16, 16, 0f) {

            @Override
            public void update(float dt) {
                // fall down every frame
                setPosition(getX(), getY() - FALL_SPEED * dt);

                // destroy off-screen (lifecycle demo)
                if (getY() < -40) destroy();
            }

            @Override
            public void onCollision(ICollidable other) {
                // generic interaction: touching either agent removes the object
                if (other == agentA || other == agentB) {
                    destroy();
                }
            }
        };

        game.getEntityManager().addEntity(falling);
    }

    @Override public void hide() { }

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
