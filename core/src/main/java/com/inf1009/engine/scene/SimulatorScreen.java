package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.AbstractGameEntity;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.ICollidable;
import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.entity.StaticEntity;

import java.util.ArrayList;
import java.util.List;

public class SimulatorScreen extends AbstractScreen {

    private SpriteBatch batch;

    private Texture bg;
    private Texture playerATex;
    private Texture playerBTex;
    private Texture itemTex;

    private DynamicEntity agentA;
    private DynamicEntity agentB;
    private StaticEntity ground;
    private StaticEntity fallingItem;

    private static final float FALL_SPEED = 120f;
    private static final float SCREEN_W = 640;
    private static final float SCREEN_H = 480;

    private boolean initialized = false;

    public SimulatorScreen(GameMaster game) {
        super(game);
    }

    @Override
    public void show() {
        batch = game.getBatch();

        if (bg == null) {
            bg = new Texture("img/bg.png");
            playerATex = new Texture("img/playerA.png");
            playerBTex = new Texture("img/playerB.png");
            itemTex = new Texture("img/falling.png");
        }

        if (!initialized) {
            initialized = true;

            game.getEntityManager().clear();

            agentA = new DynamicEntity(80, 120, 32, 32, 220f);
            agentB = new DynamicEntity(140, 120, 32, 32, 220f);
            ground = new StaticEntity(0, 40, 640, 40);

            game.getEntityManager().addEntity(agentA);
            game.getEntityManager().addEntity(agentB);
            game.getEntityManager().addEntity(ground);

            spawnFallingItem();
        }
    }

    @Override
    public void render(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getSceneManager().setScreen("end");
            return;
        }

        clearScreen();
        handleMovement(dt);
        updateEntities(dt);
        updateFallingItem(dt);
    
        handleCatch();
        handleCollisions();
        renderWorld();
    }

    private void spawnFallingItem() {
        float x = (float) Math.random() * (SCREEN_W - 32);
        fallingItem = new StaticEntity(x, SCREEN_H, 32, 32);
        game.getEntityManager().addEntity(fallingItem);
    }


    private void handleMovement(float dt) {
        // Simulator decides mapping (game-specific)
        InputState inputA = game.getIOManager().readInput(0);
        InputState inputB = game.getIOManager().readInput(1);
        game.getMovementManager().applyInput(agentA, inputA, dt);
        game.getMovementManager().applyInput(agentB, inputB, dt);
    }


    private void updateEntities(float dt) {
        game.getEntityManager().update(dt);

        float groundTop = ground.getY() + ground.getH();

        if (agentA.getY() < groundTop) agentA.landOn(groundTop);
        if (agentB.getY() < groundTop) agentB.landOn(groundTop);
    }

    private void updateFallingItem(float dt) {
        if (fallingItem == null) return;

        float ny = fallingItem.getY() - FALL_SPEED * dt;
        fallingItem.setPosition(fallingItem.getX(), ny);

        if (ny < -40) {
            game.getEntityManager().removeEntity(fallingItem);
            fallingItem = null;
            spawnFallingItem();
        }
    }


    private void handleCollisions() {
        List<ICollidable> collidables = new ArrayList<>();

        for (AbstractGameEntity e : game.getEntityManager().getEntities()) {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        }

        game.getCollisionManager().update(collidables);
    }

    private void handleCatch() {
        if (fallingItem == null) return;

        boolean caught =
                agentA.getBounds().overlaps(fallingItem.getBounds()) ||
                agentB.getBounds().overlaps(fallingItem.getBounds());

        if (caught) {
            game.getOutput().playSound("audio/coin.wav", 0.1f);
            System.out.println("Pickup: coin. Collision Detected");
            game.getEntityManager().removeEntity(fallingItem);
            spawnFallingItem();
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderWorld() {
        batch.begin();

        batch.draw(bg, 0, 0, SCREEN_W, SCREEN_H);
        batch.draw(playerATex, agentA.getX(), agentA.getY(), agentA.getW(), agentA.getH());
        batch.draw(playerBTex, agentB.getX(), agentB.getY(), agentB.getW(), agentB.getH());

        if (fallingItem != null) {
            batch.draw(itemTex,
                    fallingItem.getX(),
                    fallingItem.getY(),
                    fallingItem.getW(),
                    fallingItem.getH());
        }

        batch.end();
    }
}
