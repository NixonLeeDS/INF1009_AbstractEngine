package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractGameEntity {

    // Core transform data
    private float x;
    private float y;
    private float w;
    private float h;

    // Bounding box used for collision detection
    private final Rectangle bounds;

    // Marks entity for removal by manager
    private boolean destroyed;

    protected AbstractGameEntity(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bounds = new Rectangle(x, y, w, h);
        this.destroyed = false;
    }

    // Called every frame by EntityManager
    public abstract void update(float dt);

    // Returns collision bounds
    public final Rectangle getBounds() {
        return bounds;
    }

    // Position + size accessors
    public final float getX() { return x; }
    public final float getY() { return y; }
    public final float getW() { return w; }
    public final float getH() { return h; }

    // Lifecycle state
    public final boolean isDestroyed() { return destroyed; }
    public final void destroy() { destroyed = true; }

    // Updates position and keeps bounds aligned
    public final void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        bounds.setPosition(newX, newY);
    }

    // Updates size and keeps bounds aligned
    protected final void setSize(float newW, float newH) {
        this.w = newW;
        this.h = newH;
        bounds.setSize(newW, newH);
    }


    // Entities must define how they render themselves
    public abstract void render(ShapeRenderer shape);
}
