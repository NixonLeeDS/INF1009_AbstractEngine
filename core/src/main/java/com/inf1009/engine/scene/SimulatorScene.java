package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.entity.StaticEntity;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.input.KeyboardDevice;
import com.inf1009.engine.interfaces.IEntityProvider;

import java.util.List;

public class SimulatorScene extends Scene {

    // UML fields
    private boolean simulationRunning = true;
    private float simulationTime = 0f;
    private boolean showCollisionBounds = false;

    // Core
    private final GameMaster game;
    private final IEntityProvider entityProvider;

    // Rendering
    private ShapeRenderer shape;
    private SpriteBatch batch;
    private BitmapFont font;

    // Demo entities
    private DynamicEntity entityA;
    private DynamicEntity entityB;
    private DynamicEntity fallingHazard;

    private StaticEntity ground;
    private StaticEntity leftWall;
    private StaticEntity rightWall;
    private StaticEntity topWall;

    private boolean gravityEnabled = true;

    public SimulatorScene(GameMaster game) {
        this.game = game;
        this.entityProvider = game.getEntityManager();
    }

    @Override
    public void show() {

        if (shape == null) shape = new ShapeRenderer();
        if (batch == null) batch = new SpriteBatch();
        if (font == null) font = new BitmapFont();

        isLoaded = true;

        game.getInputManager().clearDevices();
        game.getInputManager().registerDevice(KeyboardDevice.wasd());
        game.getInputManager().registerDevice(KeyboardDevice.arrows());

        resetWorld();
    }

    @Override
    public void render(float dt) {

        handleSystemKeys();
        clearScreen();

        if (simulationRunning) {
            update(dt);
        }

        renderWorld();
        renderOverlay();
    }

    // UML update
    public void update(float dt) {

        simulationTime += dt;

        game.getInputManager().update();

        InputState inputA = game.getInputManager().readDevice(0);
        InputState inputB = game.getInputManager().readDevice(1);

        applyInput(entityA, inputA);
        applyInput(entityB, inputB);

        applyGravity(entityA, dt);
        applyGravity(entityB, dt);
        applyGravity(fallingHazard, dt);

        game.getEntityManager().update(dt);

        clampToGround(entityA);
        clampToGround(entityB);

        clampToWalls(entityA);
        clampToWalls(entityB);

        handleHazardLogic();
    }

    // Toggle methods (UML)
    public void toggleSimulation() {
        simulationRunning = !simulationRunning;
    }

    public void toggleGravity() {
        gravityEnabled = !gravityEnabled;
    }

    public void spawnEntity(GameEntity entity) {
        entityProvider.addEntity(entity);
    }

    private void handleSystemKeys() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            toggleSimulation();

        if (Gdx.input.isKeyJustPressed(Input.Keys.G))
            toggleGravity();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
            resetWorld();

        if (Gdx.input.isKeyJustPressed(Input.Keys.C))
            showCollisionBounds = !showCollisionBounds;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            game.getSceneManager().setScene("end");
    }

    private void applyInput(DynamicEntity entity, InputState input) {

        if (entity == null || input == null) return;

        entity.setDirection(input.getMoveX(), 0f);
        entity.setSpeed(220f);

        if (input.isJump() && entity.getVelocity().y == 0f) {
            Vector2 v = entity.getVelocity();
            v.y = 320f;
            entity.setVelocity(v);
        }
    }

    private void applyGravity(DynamicEntity entity, float dt) {

        if (entity == null || !gravityEnabled) return;

        Vector2 v = entity.getVelocity();
        v.y += -900f * dt;
        entity.setVelocity(v);
    }

    private void clampToGround(DynamicEntity d) {

        float groundTop = ground.getY() + ground.getHeight();

        if (d.getY() < groundTop) {
            d.setPosition(d.getX(), groundTop);
            Vector2 v = d.getVelocity();
            v.y = 0f;
            d.setVelocity(v);
        }
    }

    private void clampToWalls(DynamicEntity d) {

        float leftLimit = leftWall.getX() + leftWall.getWidth();
        float rightLimit = rightWall.getX() - d.getWidth();
        float topLimit = topWall.getY();

        if (d.getX() < leftLimit)
            d.setPosition(leftLimit, d.getY());

        if (d.getX() > rightLimit)
            d.setPosition(rightLimit, d.getY());

        if (d.getY() > topLimit - d.getHeight())
            d.setPosition(d.getX(), topLimit - d.getHeight());
    }

    private void handleHazardLogic() {

        if (fallingHazard == null) return;

        boolean hitGround = fallingHazard.getY() <= 0;
        boolean hitA = fallingHazard.getBounds().overlaps(entityA.getBounds());
        boolean hitB = fallingHazard.getBounds().overlaps(entityB.getBounds());

        if (hitGround || hitA || hitB) {
            entityProvider.removeEntity(fallingHazard);
            spawnHazard();
        }
    }

    private void spawnHazard() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        fallingHazard = new DynamicEntity(
                60f + (float) Math.random() * (w - 120f),
                h - 80f,
                36f,
                36f
        );

        fallingHazard.setVelocity(new Vector2(0f, -240f));
        entityProvider.addEntity(fallingHazard);
    }

    private void resetWorld() {

        simulationTime = 0f;

        game.getEntityManager().clear();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        ground = new StaticEntity(0, 0, w, 40);
        leftWall = new StaticEntity(0, 0, 20, h);
        rightWall = new StaticEntity(w - 20, 0, 20, h);
        topWall = new StaticEntity(0, h - 20, w, 20);

        entityA = new DynamicEntity(120, 220, 45, 45);
        entityB = new DynamicEntity(260, 220, 45, 45);

        entityProvider.addEntity(ground);
        entityProvider.addEntity(leftWall);
        entityProvider.addEntity(rightWall);
        entityProvider.addEntity(topWall);
        entityProvider.addEntity(entityA);
        entityProvider.addEntity(entityB);

        spawnHazard();
    }

    private void renderWorld() {

        shape.begin(ShapeRenderer.ShapeType.Filled);

        List<GameEntity> entities = entityProvider.getEntities();

        for (GameEntity e : entities) {

            if (e == entityA) {
                shape.setColor(0f, 0.8f, 1f, 1f);
            }
            else if (e == entityB) {
                shape.setColor(1f, 0.4f, 0f, 1f);
            }
            else if (e == fallingHazard) {
                shape.setColor(0.9f, 0.1f, 0.1f, 1f);
            }
            else {
                shape.setColor(0.3f, 0.3f, 0.3f, 1f);
            }

            shape.rect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
        }

        shape.end();
    }

    private void renderOverlay() {

        batch.begin();

        font.draw(batch,
                "Press ESC to end simulation",
                20,
                Gdx.graphics.getHeight() - 20);

        font.draw(batch,
                String.format("Time: %.2f", simulationTime),
                Gdx.graphics.getWidth() - 140,
                Gdx.graphics.getHeight() - 20);

        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.10f, 0.12f, 0.14f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

}

