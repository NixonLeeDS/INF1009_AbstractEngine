package com.inf1009.engine.input;

public final class InputState {

    private float moveX;
    private float moveY;
    private float moveZ;
    private boolean jump;

    public InputState(float moveX, float moveY, boolean jump) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.moveZ = 0f;
        this.jump = jump;
    }

    public float getMoveX() { return moveX; }
    public float getMoveY() { return moveY; }
    public float getMoveZ() { return moveZ; }
    public boolean isJump() { return jump; }

    public void reset() {
        moveX = 0f;
        moveY = 0f;
        moveZ = 0f;
        jump = false;
    }

    public static InputState neutral() {
        return new InputState(0f, 0f, false);
    }
}
