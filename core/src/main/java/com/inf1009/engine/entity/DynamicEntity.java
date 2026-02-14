package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.IMovable;

public class DynamicEntity extends GameEntity implements IMovable, ICollidable {

    // Fields
    private Vector2 velocity;
    private Vector2 acceleration;
    private Vector2 direction;
    private float speed;

    // Constructor
    public DynamicEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.direction = new Vector2(1, 0);
        this.speed = 0f;
    }

    // IMovable methods
    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector2 v) {
        if (v != null) velocity.set(v);
    }

    @Override
    public Vector2 getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(float x, float y) {
        this.acceleration.set(x, y);
    }

    @Override
    public void applyVelocity(float dt) {
        velocity.x += acceleration.x * dt;
        velocity.y += acceleration.y * dt;
    }

    @Override
    public Vector2 getDirection() {
        return direction;
    }

    @Override
    public void setDirection(float dx, float dy) {
        direction.set(dx, dy);
        if (direction.len2() > 0) direction.nor();
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // Lifecycle
    @Override
    public void update(float deltaTime) {
        applyVelocity(deltaTime);

        x += direction.x * speed * deltaTime + velocity.x * deltaTime;
        y += direction.y * speed * deltaTime + velocity.y * deltaTime;

        bounds.setPosition(x, y);
    }

    @Override
    public void render(ShapeRenderer shape) {
        shape.rect(x, y, width, height);
    }

    // ICollidable methods
    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void onCollision(ICollidable other) {

        if (other == null) return;

        // Simple vertical stop logic for demo
        if (other.getBounds().y <= this.y) {
            velocity.y = 0;
            setAcceleration(0f, 0f);
        }
    }


    public void onCollisionEnter(ICollidable other) {
    }
}
