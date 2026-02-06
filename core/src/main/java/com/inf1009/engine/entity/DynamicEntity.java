package com.inf1009.engine.entity;

/**
 * A moving entity with configurable speed and basic velocity storage.
 * Engine-level class (generic).
 */
public class DynamicEntity extends AbstractGameEntity implements Moveable, ICollidable {

    private float speed;

    // Optional for platformer later, harmless now
    private float velX;
    private float velY;
    private boolean grounded;

    private boolean solid = true;

    public DynamicEntity(float x, float y, float w, float h, float speed) {
        super(x, y, w, h);
        this.speed = speed;
    }

    @Override
    public void update(float dt) {
        // Default: nothing autonomous.
        // If you later use velX/velY, you can update position here.
    }

    @Override
    public void movement(InputState input, float dt) {
        float nx = getX() + input.getMoveX() * speed * dt;
        float ny = getY() + input.getMoveY() * speed * dt;
        setPosition(nx, ny);
    }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public float getVelX() { return velX; }
    public float getVelY() { return velY; }
    public void setVelX(float velX) { this.velX = velX; }
    public void setVelY(float velY) { this.velY = velY; }

    public boolean isGrounded() { return grounded; }
    public void setGrounded(boolean grounded) { this.grounded = grounded; }

    @Override
    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    @Override
    public void onCollision(ICollidable other) {
        // Default: no reaction. Keep engine generic.
    }
}
