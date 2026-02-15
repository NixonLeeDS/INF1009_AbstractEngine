package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.GameMaster;

public class StartScene extends Scene {

    // Core reference
    private final GameMaster game;

    // Rendering
    private ShapeRenderer shape;
    private BitmapFont font;
    private SpriteBatch batch;

    // Button bounds
    private float startX = 220, startY = 240, startW = 200, startH = 60;
    private float exitX  = 220, exitY  = 160, exitW  = 200, exitH  = 60;

    public StartScene(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();
        font = new BitmapFont();
        batch = game.getBatch();
        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(startX, startY, startW, startH);
        shape.rect(exitX, exitY, exitW, exitH);
        shape.end();

        batch.begin();
        font.draw(batch, "START", startX + 70, startY + 38);
        font.draw(batch, "EXIT", exitX + 80, exitY + 38);
        batch.end();

        if (isClicked(startX, startY, startW, startH)) {
            game.getSceneManager().setScene("sim");
        } else if (isClicked(exitX, exitY, exitW, exitH)) {
            Gdx.app.exit();
        }
    }

    private boolean isClicked(float x, float y, float w, float h) {
        if (!Gdx.input.justTouched()) return false;

        float mx = Gdx.input.getX();
        float my = Gdx.graphics.getHeight() - Gdx.input.getY();

        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
        if (font != null) font.dispose();
    }


    @Override
    public void resize(int width, int height) {
    }

    

}
