package com.inf1009.engine.entity;

import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractGameEntity {

    // Position + size
    private float x;
    private float y;
    private float w;
    private float h;

    // Collision bounds (kept in sync with x,y,w,h)
    private final Rectangle bounds;

    // Lifecycle flag (EntityManager can remove if destroyed)
    private boolean destroyed;

    protected AbstractGameEntity(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bounds = new Rectangle(x, y, w, h);
        this.destroyed = false;
    }

    // Each entity defines its own update behaviour
    public abstract void update(float dt);

    // Bounds used for collision checks
    public final Rectangle getBounds() {
        return bounds;
    }

    // Basic getters
    public final float getX() { return x; }
    public final float getY() { return y; }
    public final float getW() { return w; }
    public final float getH() { return h; }

    // Destroy flag helpers
    public final boolean isDestroyed() { return destroyed; }
    public final void destroy() { destroyed = true; }

    // Updates position and keeps bounds in sync
    public final void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        bounds.setPosition(newX, newY);
    }

    // Updates size and keeps bounds in sync
    protected final void setSize(float newW, float newH) {
        this.w = newW;
        this.h = newH;
        bounds.setSize(newW, newH);
    }
}
