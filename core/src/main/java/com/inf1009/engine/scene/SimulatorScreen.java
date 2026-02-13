package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.AbstractGameEntity;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.StaticEntity;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IEntity;
import com.inf1009.engine.interfaces.ICollidable;

import java.util.ArrayList;
import java.util.List;

public class SimulatorScreen extends AbstractScreen implements IEntity {

    private boolean simulationRunning = true;
    private float simulationTime = 0f;
    private boolean showCollisionBounds = false;

    private ShapeRenderer shape;

    private DynamicEntity entityA;
    private DynamicEntity entityB;
    private DynamicEntity fallingCircle;
    private StaticEntity ground;

    public SimulatorScreen(GameMaster game) {
        super(game);
    }

    @Override
    public List<AbstractGameEntity> getEntities() {
        return game.getEntityManager().getEntities();
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();
        resetSimulation();
    }

    @Override
    public void render(float dt) {

        if (!simulationRunning) return;

        simulationTime += dt;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            simulationRunning = !simulationRunning;
        }

        clearScreen();

        handleInput(dt);

        game.getEntityManager().update(dt);

        applyGroundLanding(entityA);
        applyGroundLanding(entityB);

        handleCollisions();

        clampInsideWorld(entityA);
        clampInsideWorld(entityB);

        renderWorld();
    }

    public void spawnEntity(AbstractGameEntity entity) {
        game.getEntityManager().addEntity(entity);
    }

    public void toggleCollisionVisuals() {
        showCollisionBounds = !showCollisionBounds;
    }

    public void resetSimulation() {

        game.getEntityManager().clear();
        simulationTime = 0f;

        float worldWidth = Gdx.graphics.getWidth();

        ground = new StaticEntity(0, 0, worldWidth, 40);
        spawnEntity(ground);

        entityA = new DynamicEntity(100, 200, 45, 45, 220f);
        entityB = new DynamicEntity(250, 200, 45, 45, 220f);

        spawnEntity(entityA);
        spawnEntity(entityB);

        spawnFallingCircle();
    }

    public void clearAllShapes() {
        game.getEntityManager().clear();
    }

    public void handleInput(float dt) {

        InputState inputA = game.getIOManager().readDevice(0);
        InputState inputB = game.getIOManager().readDevice(1);

        game.getMovementManager().applyInput(entityA, inputA, dt);
        game.getMovementManager().applyInput(entityB, inputB, dt);
    }

    private void spawnFallingCircle() {

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

        spawnEntity(fallingCircle);
    }

    private void handleCollisions() {

        List<ICollidable> collidables = new ArrayList<>();

        for (AbstractGameEntity e : game.getEntityManager().getEntities()) {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        }

        game.getCollisionManager().update(collidables);

        if (fallingCircle != null) {

            if (fallingCircle.getY() <= 0 ||
                fallingCircle.getBounds().overlaps(entityA.getBounds()) ||
                fallingCircle.getBounds().overlaps(entityB.getBounds())) {

                game.getEntityManager().removeEntity(fallingCircle);
                spawnFallingCircle();
            }
        }
    }

    private void applyGroundLanding(DynamicEntity d) {

        float groundTop = ground.getY() + ground.getH();

        if (d.getY() <= groundTop) {
            d.landOn(groundTop);
        }
    }

    private void clampInsideWorld(DynamicEntity d) {

        float worldWidth = Gdx.graphics.getWidth();
        float worldHeight = Gdx.graphics.getHeight();

        float x = d.getX();
        float y = d.getY();

        if (x < 0) x = 0;
        if (x + d.getW() > worldWidth) x = worldWidth - d.getW();
        if (y < 0) y = 0;
        if (y + d.getH() > worldHeight) y = worldHeight - d.getH();

        d.setPosition(x, y);
    }

    private void renderWorld() {

        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (AbstractGameEntity e : game.getEntityManager().getEntities()) {

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
            else {
                shape.setColor(0.4f, 0.4f, 0.4f, 1f);
                shape.rect(e.getX(), e.getY(), e.getW(), e.getH());
            }

            if (showCollisionBounds) {
                shape.setColor(0f, 1f, 0f, 1f);
                shape.rect(e.getX(), e.getY(), e.getW(), e.getH());
            }
        }

        shape.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.10f, 0.12f, 0.14f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
