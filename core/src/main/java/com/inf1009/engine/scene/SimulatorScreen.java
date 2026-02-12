package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.AbstractGameEntity;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.StaticEntity;
import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.IScreen;
import com.inf1009.engine.manager.CollisionManager;
import com.inf1009.engine.manager.EntityManager;
import com.inf1009.engine.manager.IOManager;
import com.inf1009.engine.manager.MovementManager;
import com.inf1009.engine.entity.InputState;

import java.util.ArrayList;
import java.util.List;

public class SimulatorScreen implements IScreen {

    private final GameMaster game;

    private final EntityManager entityManager;
    private final CollisionManager collisionManager;
    private final MovementManager movementManager;
    private final IOManager ioManager;

    private ShapeRenderer shape;

    private DynamicEntity entityA;
    private DynamicEntity entityB;
    private DynamicEntity fallingCircle;

    private StaticEntity ground;

    private boolean initialized = false;

    public SimulatorScreen(GameMaster game) {
        this.game = game;
        this.entityManager = game.getEntityManager();
        this.collisionManager = game.getCollisionManager();
        this.movementManager = game.getMovementManager();
        this.ioManager = game.getIOManager();
    }

    @Override
    public void show() {
        if (shape == null) {
            shape = new ShapeRenderer();
        }

        if (!initialized) {
            initialized = true;
            resetSimulation();
        }
    }

    @Override
    public void render(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getSceneManager().setScreen("end");
            return;
        }

        clearScreen();

        handleInput(dt);

        entityManager.update(dt);

        applyGroundLanding(entityA);
        applyGroundLanding(entityB);

        handleCollisions();

        renderWorld();
    }

    private void handleInput(float dt) {

        InputState inputA = ioManager.readDevice(0);
        InputState inputB = ioManager.readDevice(1);

        movementManager.applyInput(entityA, inputA, dt);
        movementManager.applyInput(entityB, inputB, dt);
    }

    private void applyGroundLanding(DynamicEntity d) {

        float groundTop = ground.getY() + ground.getH();

        if (d.getY() <= groundTop) {
            d.landOn(groundTop);
        }
    }

    private void handleCollisions() {

        List<ICollidable> collidables = new ArrayList<ICollidable>();

        for (AbstractGameEntity e : entityManager.getEntities()) {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        }

        collisionManager.update(collidables);

        if (fallingCircle != null) {

            if (fallingCircle.getY() <= 0 ||
                fallingCircle.getBounds().overlaps(entityA.getBounds()) ||
                fallingCircle.getBounds().overlaps(entityB.getBounds())) {

                entityManager.removeEntity(fallingCircle);
                spawnCircle();
            }
        }
    }

    private void spawnCircle() {

        float worldWidth = Gdx.graphics.getWidth();
        float worldHeight = Gdx.graphics.getHeight();

        fallingCircle = new DynamicEntity(
                50 + (float)Math.random() * (worldWidth - 100),
                worldHeight - 60,
                40,
                40,
                0f
        );

        fallingCircle.setVelocityY(-300f);

        entityManager.addEntity(fallingCircle);
    }

    private void renderWorld() {

        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (AbstractGameEntity e : entityManager.getEntities()) {

            if (e == entityA) {
                shape.setColor(0f, 0.8f, 1f, 1f);
                shape.rect(e.getX(), e.getY(), e.getW(), e.getH());
            }
            else if (e == entityB) {
                shape.setColor(1f, 0.4f, 0f, 1f);
                float x = e.getX();
                float y = e.getY();
                float w = e.getW();
                float h = e.getH();
                shape.triangle(x, y, x + w, y, x + w / 2f, y + h);
            }
            else if (e == fallingCircle) {
                shape.setColor(0.9f, 0.1f, 0.1f, 1f);
                shape.circle(
                        e.getX() + e.getW() / 2f,
                        e.getY() + e.getH() / 2f,
                        e.getW() / 2f
                );
            }
            else if (e instanceof StaticEntity) {
                shape.setColor(0.3f, 0.3f, 0.3f, 1f);
                shape.rect(e.getX(), e.getY(), e.getW(), e.getH());
            }
        }

        shape.end();
    }

    public void resetSimulation() {

        entityManager.clear();

        float worldWidth = Gdx.graphics.getWidth();

        ground = new StaticEntity(0, 0, worldWidth, 40);
        entityManager.addEntity(ground);

        entityA = new DynamicEntity(100, 200, 45, 45, 220f);
        entityB = new DynamicEntity(250, 200, 45, 45, 220f);

        entityManager.addEntity(entityA);
        entityManager.addEntity(entityB);

        spawnCircle();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.10f, 0.12f, 0.14f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
