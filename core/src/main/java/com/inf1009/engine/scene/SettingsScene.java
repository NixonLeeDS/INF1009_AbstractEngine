package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.manager.SceneManager;
import com.inf1009.engine.manager.InputManager;
import com.inf1009.engine.interfaces.ISoundManager;

public class SettingsScene extends Scene {

    private final SceneManager sceneManager;
    private final InputManager inputManager;
    private final SpriteBatch batch;
    private final ISoundManager soundManager;

    private BitmapFont font;

    public SettingsScene(
            SceneManager sceneManager,
            InputManager inputManager,
            ISoundManager soundManager,
            SpriteBatch batch
    ) {
        this.sceneManager = sceneManager;
        this.inputManager = inputManager;
        this.soundManager = soundManager;
        this.batch = batch;
    }

    @Override
    public void show() {
        font = new BitmapFont();
        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        update(dt);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "SETTINGS", 300, 320);
        font.draw(batch, "Press M to Mute", 260, 280);
        font.draw(batch, "Press U to Unmute", 250, 250);
        font.draw(batch, "Press ESC to Return", 230, 220);
        batch.end();
    }

    public void update(float dt) {

        inputManager.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            soundManager.mute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            soundManager.unmute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneManager.setScene("start");
        }
    }

    @Override public void hide() {}
    @Override public void resize(int w, int h) {}

    @Override
    public void dispose() {
        if (font != null) font.dispose();
    }
}
