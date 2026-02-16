package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.entity.StaticEntity;
import com.inf1009.engine.interfaces.IEntityProvider;
import com.inf1009.engine.interfaces.IMovementManager;
import com.inf1009.engine.interfaces.IInputManager;
import com.inf1009.engine.manager.SceneManager;

import java.util.List;

public class SimulatorScene extends Scene {

    // Engine dependencies
    private final IEntityProvider entityProvider;
    private final IMovementManager movementManager;
    private final IInputManager inputManager;
    private final SceneManager sceneManager;
    private final SpriteBatch batch;

    // Simulation state
    private boolean simulationRunning = true;
    private float simulationTime = 0f;

    // Demo entities
    private DynamicEntity controllableEntity;
    private DynamicEntity autonomousEntity;
    private StaticEntity boundarySurface;

    // Rendering utilities
    private ShapeRenderer shapeRenderer;
    private BitmapFont overlayFont;

    public SimulatorScene(
            IEntityProvider entityProvider,
            IMovementManager movementManager,
            IInputManager inputManager,
            SpriteBatch batch,
            SceneManager sceneManager
    ) {
        this.entityProvider = entityProvider;
        this.movementManager = movementManager;
        this.inputManager = inputManager;
        this.batch = batch;
        this.sceneManager = sceneManager;
    }

    @Override
    public void show() {

        shapeRenderer = new ShapeRenderer(); // Initialize renderer
        overlayFont = new BitmapFont();      // Initialize overlay font

        entityProvider.clear(); // Reset simulation world

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        boundarySurface = new StaticEntity(0, 0, w, 40); // Ground surface
        controllableEntity = new DynamicEntity(200, 200, 45, 45); // Player entity
        autonomousEntity = new DynamicEntity(w / 2f, h - 80f, 80, 80); // Falling entity

        entityProvider.addEntity(boundarySurface);
        entityProvider.addEntity(controllableEntity);
        entityProvider.addEntity(autonomousEntity);

        simulationTime = 0f;
        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneManager.setScene("end"); // Transition to end scene
        }

        clearScreen(); // Clear frame

        if (simulationRunning) {
            update(dt); // Update simulation
        }

        renderWorld();   // Draw entities
        renderOverlay(); // Draw UI overlay
    }

    private void update(float dt) {

        simulationTime += dt; // Update timer

        inputManager.update(); // Update input system

        float moveX = inputManager.getInputState().getMoveX();
        boolean jump = inputManager.getInputState().isJump();

        movementManager.applyInput(controllableEntity, moveX, 0f, 300f); // Apply horizontal movement

        if (jump && Math.abs(controllableEntity.getVelocity().y) < 0.1f) {
            Vector2 v = controllableEntity.getVelocity();
            v.y = 500f;
            controllableEntity.setVelocity(v);
        }

        movementManager.applyGravity(controllableEntity, 1200f * dt); // Apply gravity to player
        movementManager.applyGravity(autonomousEntity, 900f * dt);    // Apply gravity to hazard

        movementManager.applyVelocity(controllableEntity, dt); // Update player position
        movementManager.applyVelocity(autonomousEntity, dt);   // Update hazard position

        clampToWorld(controllableEntity); // Constrain player inside screen

        if (autonomousEntity.getY() <= boundarySurface.getY() + boundarySurface.getHeight()) {
            respawnAutonomousEntity(); // Respawn falling entity
        }

        if (controllableEntity.getY() <= boundarySurface.getY() + boundarySurface.getHeight()) {

            controllableEntity.setPosition(
                    controllableEntity.getX(),
                    boundarySurface.getY() + boundarySurface.getHeight()
            );

            Vector2 v = controllableEntity.getVelocity();
            v.y = 0;
            controllableEntity.setVelocity(v);
        }
    }

    private void respawnAutonomousEntity() {

        entityProvider.removeEntity(autonomousEntity); // Remove old instance

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        autonomousEntity = new DynamicEntity(
                100 + (float)Math.random() * (w - 200),
                h - 80f,
                80, 80
        );

        entityProvider.addEntity(autonomousEntity); // Add new instance
    }

    private void clampToWorld(DynamicEntity entity) {

        float worldWidth = Gdx.graphics.getWidth();
        float worldHeight = Gdx.graphics.getHeight();

        if (entity.getX() < 0)
            entity.setPosition(0, entity.getY());

        if (entity.getX() + entity.getWidth() > worldWidth)
            entity.setPosition(worldWidth - entity.getWidth(), entity.getY());

        if (entity.getY() + entity.getHeight() > worldHeight) {
            entity.setPosition(entity.getX(),
                    worldHeight - entity.getHeight());

            Vector2 v = entity.getVelocity();
            v.y = 0;
            entity.setVelocity(v);
        }
    }

    private void renderWorld() {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        List<GameEntity> entities = entityProvider.getEntities();

        for (GameEntity e : entities) {

            if (e == controllableEntity)
                shapeRenderer.setColor(0f, 0.8f, 1f, 1f);
            else
                shapeRenderer.setColor(1f, 0f, 0f, 1f);

            shapeRenderer.rect(e.getX(), e.getY(),
                    e.getWidth(), e.getHeight());
        }

        shapeRenderer.end();
    }

    private void renderOverlay() {

        batch.begin();

        overlayFont.draw(batch,
                "Press ESC to End Simulation",
                20,
                Gdx.graphics.getHeight() - 20);

        String timeText = "Time: " + String.format("%.2f", simulationTime);

        overlayFont.draw(batch,
                timeText,
                Gdx.graphics.getWidth() - 150,
                Gdx.graphics.getHeight() - 20);

        batch.end();
    }

    private void clearScreen() {

        Gdx.gl.glClearColor(0.1f, 0.12f, 0.14f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override public void hide() {}
    @Override public void resize(int width, int height) {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        overlayFont.dispose();
    }

    public void spawnEntity(GameEntity entity) {
        entityProvider.addEntity(entity); // Add entity to simulation
    }
}
