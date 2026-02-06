package com.inf1009.engine.entity;

import com.badlogic.gdx.math.Rectangle;

public interface ICollidable {
    Rectangle getBounds();
    boolean isSolid();
    void onCollision(ICollidable other);
}
