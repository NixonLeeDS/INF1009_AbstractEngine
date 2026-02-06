package com.inf1009.engine.entity;

public final class InputState {

    // Axis movement (-1 to 1)
    private final float moveX;
    private final float moveY;

    // Jump action (single press)
    private final boolean jump;

    public InputState(float moveX, float moveY, boolean jump) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.jump = jump;
    }

    public float getMoveX() { return moveX; }
    public float getMoveY() { return moveY; }
    public boolean isJump() { return jump; }

    // Default no-input state
    public static InputState neutral() {
        return new InputState(0f, 0f, false);
    }
}
