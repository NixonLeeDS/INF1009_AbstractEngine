package com.inf1009.engine.entity;

import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.IMoveable;

public class DynamicEntity extends AbstractGameEntity implements IMoveable, ICollidable {

    // UML-style fields
    private float velocityX = 0f;
    private float velocityY = 0f;
    private float speed;
    private boolean isGrounded = false;

    // Physics constants (you can tune)
    private float gravity = -900f;
    private float jumpImpulse = 380f;

    public DynamicEntity(float x, float y, float w, float h, float speed) {
        super(x, y, w, h);
        this.speed = speed;
    }

    // UML: update(dt) applies gravity and moves using velocity
    @Override
    public void update(float dt) {
        isGrounded = false;
        applyGravity(gravity, dt);
        move(velocityX, velocityY, dt);
    }

    // UML: movement(input, dt) from IMoveable, converts input to velocity
    @Override
    public void movement(InputState input, float dt) {
        // Convert input into horizontal velocity (per second)
        velocityX = input.getMoveX() * speed;

        // Optional: if you want vertical input movement (top-down), uncomment:
        // velocityY = input.getMoveY() * speed;

        if (input.isJump() && isGrounded) {
            jump(jumpImpulse);
        }
    }

    // UML: applyGravity(gravity, dt)
    public void applyGravity(float gravity, float dt) {
        if (!isGrounded) {
            velocityY += gravity * dt;
        }
    }

    // UML: move(dx, dy, dt)
    // Here dx/dy are velocities in px/s
    public void move(float dx, float dy, float dt) {
        float nx = getX() + dx * dt;
        float ny = getY() + dy * dt;
        setPosition(nx, ny);
    }

    // UML: jump(force)
    public void jump(float force) {
        velocityY = force;
        isGrounded = false;
    }

    // Keep your existing helper so SimulatorScreen does not need big changes
    public void landOn(float groundY) {
        setPosition(getX(), groundY);
        velocityY = 0f;
        isGrounded = true;
    }

    // UML getters
    public float getVelocityX() { return velocityX; }
    public float getVelocityY() { return velocityY; }
    public void setVelocityX(float vx) { this.velocityX = vx; }
    public void setVelocityY(float vy) { this.velocityY = vy; }


    // extra getters/setters that help other code and UML
    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public boolean isGrounded() { return isGrounded; }

    @Override
    public void onCollision(ICollidable other) {
            // Reverse horizontal direction
    setVelocityX(-getVelocityX());
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
