package com.inf1009.engine.entity;

public class DynamicEntity extends AbstractGameEntity implements Moveable, ICollidable {

    // Movement speed (horizontal movement)
    private float speed;

    // Simple vertical physics
    private float vy = 0f;
    private float gravity = -900f;      // px/s^2
    private float jumpImpulse = 380f;   // px/s

    public DynamicEntity(float x, float y, float w, float h, float speed) {
        super(x, y, w, h);
        this.speed = speed;
    }

    @Override
    public void update(float dt) {
        // Apply gravity to vertical velocity
        vy += gravity * dt;

        // Apply vertical movement
        setPosition(getX(), getY() + vy * dt);
    }

    @Override
    public void movement(InputState input, float dt) {
        // Horizontal movement using input snapshot
        float nx = getX() + input.getMoveX() * speed * dt;
        setPosition(nx, getY());

        // Jump only if requested and currently grounded
        if (input.isJump() && isOnGround()) {
            vy = jumpImpulse;
        }
    }

    // Snap entity onto ground and stop falling
    public void landOn(float groundY) {
        setPosition(getX(), groundY);
        vy = 0f;
    }

    // Lightweight ground check for demo
    private boolean isOnGround() {
        return Math.abs(vy) < 0.01f;
    }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public float getVy() { return vy; }
    public void setVy(float vy) { this.vy = vy; }

    @Override
    public void onCollision(ICollidable other) {
        // Default: no reaction (keeps engine generic)
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
