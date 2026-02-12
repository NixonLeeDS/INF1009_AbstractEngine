package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.IMoveable;

public class DynamicEntity extends AbstractGameEntity implements IMoveable, ICollidable {

    private float velocityX = 0f;
    private float velocityY = 0f;
    private float speed;

    private boolean isGrounded = false;

    private float gravity = -900f;

    public DynamicEntity(float x, float y, float w, float h, float speed) {
        super(x, y, w, h);
        this.speed = speed;
    }

    @Override
    public void update(float dt) {
        isGrounded = false;
        applyGravity(dt);
        applyVelocity(dt);
    }

    private void applyGravity(float dt) {
        if (!isGrounded) {
            velocityY += gravity * dt;
        }
    }

    private void applyVelocity(float dt) {
        float nx = getX() + velocityX * dt;
        float ny = getY() + velocityY * dt;
        setPosition(nx, ny);
    }

    // IMoveable method
    @Override
    public void move(float dx, float dy) {
        float nx = getX() + dx;
        float ny = getY() + dy;
        setPosition(nx, ny);
    }

    public void jump(float force) {
        velocityY = force;
        isGrounded = false;
    }

    public void landOn(float groundY) {
        setPosition(getX(), groundY);
        velocityY = 0f;
        isGrounded = true;
    }

    // Velocity accessors
    @Override
    public float getVelocityX() {
        return velocityX;
    }

    @Override
    public float getVelocityY() {
        return velocityY;
    }

    @Override
    public void setVelocityX(float vx) {
        this.velocityX = vx;
    }

    @Override
    public void setVelocityY(float vy) {
        this.velocityY = vy;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isGrounded() {
        return isGrounded;
    }

    @Override
    public void onCollision(ICollidable other) {
        setVelocityX(-getVelocityX());
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void render(ShapeRenderer shape) {
        shape.rect(getX(), getY(), getW(), getH());
    }
}
