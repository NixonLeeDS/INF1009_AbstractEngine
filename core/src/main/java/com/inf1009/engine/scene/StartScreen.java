package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;

public class StartScreen implements Screen {

    private final GameMaster game;

    private ShapeRenderer shape;
    private SpriteBatch batch;
    private BitmapFont font;

    public StartScreen(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();
        batch = game.getBatch();     // batch is created in GameMaster
        font = new BitmapFont();     // default LibGDX font (no assets needed)
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // simple placeholder "button"
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(200, 220, 240, 60);
        shape.end();

        // draw prompt text
        batch.begin();
        font.draw(batch, "Press SPACE to start", 210, 255);
        font.draw(batch, "Press ESC to exit", 220, 210);
        batch.end();

        // input
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.getSceneManager().setScreen("sim");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void hide() {
        // nothing
    }

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
        if (font != null) font.dispose();
        // DO NOT dispose batch here (GameMaster owns it)
    }
}
