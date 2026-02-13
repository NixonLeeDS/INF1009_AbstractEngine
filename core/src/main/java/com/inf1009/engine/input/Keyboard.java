package com.inf1009.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Keyboard extends AbstractInputDevice {

    private final int leftKey;
    private final int rightKey;
    private final int upKey;
    private final int downKey;
    private final int jumpKey;

    public Keyboard(int leftKey, int rightKey, int upKey, int downKey, int jumpKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.downKey = downKey;
        this.jumpKey = jumpKey;
    }

    @Override
    public InputState readInput() {

        float moveX = 0f;
        float moveY = 0f;

        if (Gdx.input.isKeyPressed(leftKey)) moveX -= 1f;
        if (Gdx.input.isKeyPressed(rightKey)) moveX += 1f;

        if (Gdx.input.isKeyPressed(upKey)) moveY += 1f;
        if (Gdx.input.isKeyPressed(downKey)) moveY -= 1f;

        boolean jump = Gdx.input.isKeyJustPressed(jumpKey);

        return new InputState(moveX, moveY, jump);
    }

    public static Keyboard wasd() {
        return new Keyboard(
                Input.Keys.A,
                Input.Keys.D,
                Input.Keys.W,
                Input.Keys.S,
                Input.Keys.SPACE
        );
    }

    public static Keyboard arrows() {
        return new Keyboard(
                Input.Keys.LEFT,
                Input.Keys.RIGHT,
                Input.Keys.UP,
                Input.Keys.DOWN,
                Input.Keys.ENTER
        );
    }
}
