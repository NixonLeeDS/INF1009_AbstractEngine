package com.inf1009.engine.entity;

/**
 * Immutable-ish input snapshot used by IOManager and MovementManager.
 */
public class InputState {

    private final float moveX;
    private final float moveY;
    private final boolean jump;

    public InputState(float moveX, float moveY, boolean jump) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.jump = jump;
    }

    public float getMoveX() { return moveX; }
    public float getMoveY() { return moveY; }
    public boolean isJump() { return jump; }

    public static InputState neutral() {
        return new InputState(0f, 0f, false);
    }
}
