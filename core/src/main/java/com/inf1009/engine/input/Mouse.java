package com.inf1009.engine.input;

import com.badlogic.gdx.Gdx;

public class Mouse extends AbstractInputDevice {

    @Override
    public InputState readInput() {

        float moveX = Gdx.input.getDeltaX();
        float moveY = -Gdx.input.getDeltaY();

        boolean click = Gdx.input.justTouched();

        return new InputState(moveX, moveY, click);
    }
}
