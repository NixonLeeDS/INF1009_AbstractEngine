package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;

public class EndScreen extends AbstractScreen {

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private BitmapFont font;

    private float resumeX = 220, resumeY = 260, resumeW = 200, resumeH = 60;
    private float menuX   = 220, menuY   = 180, menuW   = 200, menuH   = 60;
    private float exitX   = 220, exitY   = 100, exitW   = 200, exitH   = 60;

    public EndScreen(GameMaster game) {
        super(game);
    }

    @Override
    public void show() {
        batch = game.getBatch();
        shape = new ShapeRenderer();
        font = new BitmapFont();
    }

    @Override
    public void render(float dt) {

        Gdx.gl.glClearColor(0.02f, 0.02f, 0.02f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(resumeX, resumeY, resumeW, resumeH);
        shape.rect(menuX, menuY, menuW, menuH);
        shape.rect(exitX, exitY, exitW, exitH);
        shape.end();

        batch.begin();
        font.draw(batch, "PAUSED", 285, 370);
        font.draw(batch, "RESUME", resumeX + 60, resumeY + 38);
        font.draw(batch, "MAIN MENU", menuX + 40, menuY + 38);
        font.draw(batch, "EXIT", exitX + 80, exitY + 38);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getSceneManager().setScreen("sim");
        }

        if (isClicked(resumeX, resumeY, resumeW, resumeH)) {
            game.getSceneManager().setScreen("sim");
        } else if (isClicked(menuX, menuY, menuW, menuH)) {
            game.getSceneManager().setScreen("start");
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

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
        if (font != null) font.dispose();
    }
}
