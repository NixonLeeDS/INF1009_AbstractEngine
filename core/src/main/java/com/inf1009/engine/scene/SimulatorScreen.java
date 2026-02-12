package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.AbstractGameEntity;
import com.inf1009.engine.entity.DynamicEntity;
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
    private DynamicEntity fallingCircle; // only one circle

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
        handleCollisions();

        float worldWidth = Gdx.graphics.getWidth();
        float worldHeight = Gdx.graphics.getHeight();

        for (AbstractGameEntity e : entityManager.getEntities()) {
            if (e instanceof DynamicEntity) {
                clampInside((DynamicEntity) e, worldWidth, worldHeight);
            }
        }

        renderWorld();
    }

    private void spawnCircle() {

        float worldWidth = Gdx.graphics.getWidth();
        float worldHeight = Gdx.graphics.getHeight();

        fallingCircle = new DynamicEntity(
                50 + (float)Math.random() * (worldWidth - 100),
                worldHeight - 60,
                50,   // bigger size
                50,
                0f
        );

        fallingCircle.setVelocityX(0);
        fallingCircle.setVelocityY(-350f); // faster drop

        entityManager.addEntity(fallingCircle);
    }

    private void handleInput(float dt) {
        InputState inputA = ioManager.readDevice(0);
        InputState inputB = ioManager.readDevice(1);
        movementManager.applyInput(entityA, inputA, dt);
        movementManager.applyInput(entityB, inputB, dt);
    }

    private void handleCollisions() {

        List<ICollidable> collidables = new ArrayList<>();

        for (AbstractGameEntity e : entityManager.getEntities()) {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        }

        collisionManager.update(collidables);

        if (fallingCircle == null) return;

        boolean remove = false;

        // Touch bottom
        if (fallingCircle.getY() <= 0) {
            remove = true;
        }

        // Hit player
        if (fallingCircle.getBounds().overlaps(entityA.getBounds()) ||
            fallingCircle.getBounds().overlaps(entityB.getBounds())) {

            System.out.println("Collision detected!");
            remove = true;
        }

        if (remove) {
            entityManager.removeEntity(fallingCircle);
            spawnCircle(); // immediately spawn next
        }
    }

    private void clampInside(DynamicEntity e, float worldWidth, float worldHeight) {

        float newX = e.getX();
        float newY = e.getY();

        if (newX < 0) {
            newX = 0;
            e.setVelocityX(0);
        }

        if (newX + e.getW() > worldWidth) {
            newX = worldWidth - e.getW();
            e.setVelocityX(0);
        }

        if (newY < 0) {
            newY = 0;
            e.setVelocityY(0);
        }

        if (newY + e.getH() > worldHeight) {
            newY = worldHeight - e.getH();
            e.setVelocityY(0);
        }

        e.setPosition(newX, newY);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.10f, 0.12f, 0.14f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
            else {
                shape.setColor(0.9f, 0.1f, 0.1f, 1f);
                shape.circle(
                        e.getX() + e.getW() / 2f,
                        e.getY() + e.getH() / 2f,
                        e.getW() / 2f
                );
            }
        }

        shape.end();
    }

    public void resetSimulation() {

        entityManager.clear();

        entityA = new DynamicEntity(100, 150, 45, 45, 220f);
        entityB = new DynamicEntity(250, 200, 45, 45, 220f);

        entityManager.addEntity(entityA);
        entityManager.addEntity(entityB);

        spawnCircle(); // start first circle
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
