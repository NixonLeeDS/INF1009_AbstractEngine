package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.GameMaster;

public class EndScene extends Scene {

    private final GameMaster game;

    private ShapeRenderer shape;
    private BitmapFont font;
    private SpriteBatch batch;

    // Only two buttons
    private float restartX = 220, restartY = 240, restartW = 200, restartH = 60;
    private float menuX    = 220, menuY    = 160, menuW    = 200, menuH    = 60;

    public EndScene(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {

        if (shape == null) shape = new ShapeRenderer();
        if (font == null) font = new BitmapFont();
        if (batch == null) batch = game.getBatch();

        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        // Clear screen
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw buttons
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(restartX, restartY, restartW, restartH);
        shape.rect(menuX, menuY, menuW, menuH);
        shape.end();

        // Draw labels
        batch.begin();
        font.draw(batch, "SIMULATION ENDED", 230, 340);
        font.draw(batch, "RESTART", restartX + 60, restartY + 38);
        font.draw(batch, "MAIN MENU", menuX + 40, menuY + 38);
        batch.end();

        // Click handling
        if (isClicked(restartX, restartY, restartW, restartH)) {
            game.getSceneManager().setScene("sim");
        }
        else if (isClicked(menuX, menuY, menuW, menuH)) {
            game.getSceneManager().setScene("start");
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
