package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.IMovable;

public class DynamicEntity extends GameEntity implements IMovable, ICollidable {

    private Vector2 velocity = new Vector2();
    private Vector2 acceleration = new Vector2();
    private Vector2 direction = new Vector2(1, 0);
    private float speed = 0f;

    public DynamicEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public Vector2 getVelocity() { return velocity; }

    @Override
    public void setVelocity(Vector2 v) {
        if (v != null) velocity.set(v);
    }

    @Override
    public Vector2 getAcceleration() { return acceleration; }

    @Override
    public void setAcceleration(float x, float y) {
        acceleration.set(x, y);
    }

    @Override
    public void applyVelocity(float dt) {
        // DO NOT modify velocity here anymore
        x += velocity.x * dt;
        y += velocity.y * dt;

        bounds.setPosition(x, y);
    }

    @Override
    public Vector2 getDirection() { return direction; }

    @Override
    public void setDirection(float dx, float dy) {
        direction.set(dx, dy);
        if (direction.len2() > 0) direction.nor();
    }

    @Override
    public float getSpeed() { return speed; }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void update(float dt) {

        // Horizontal movement via direction + speed
        velocity.x = direction.x * speed;

        // Vertical velocity already updated by MovementManager
        applyVelocity(dt);
    }

    @Override
    public void render(ShapeRenderer shape) {
        shape.setColor(0f, 0.8f, 1f, 1f);
        shape.rect(x, y, width, height);
    }


    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void onCollision(ICollidable other) {

        if (!(other instanceof GameEntity)) return;

        GameEntity g = (GameEntity) other;

        if (!isSolid() || !other.isSolid()) return;

        Rectangle a = this.getBounds();
        Rectangle b = g.getBounds();

        if (a.overlaps(b)) {

            float overlapX = Math.min(a.x + a.width - b.x, b.x + b.width - a.x);
            float overlapY = Math.min(a.y + a.height - b.y, b.y + b.height - a.y);

            if (overlapX < overlapY) {
                if (a.x < b.x) setPosition(getX() - overlapX, getY());
                else setPosition(getX() + overlapX, getY());
            } else {
                if (a.y < b.y) setPosition(getX(), getY() - overlapY);
                else setPosition(getX(), getY() + overlapY);
            }
        }
    }

}
