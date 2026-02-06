package com.inf1009.engine.entity;

import com.badlogic.gdx.math.Rectangle;

public interface ICollidable {

    // Returns the rectangle used for overlap checks
    Rectangle getBounds();

    // Used to ignore non-solid things if needed
    boolean isSolid();

    // Collision callback (engine stays generic)
    void onCollision(ICollidable other);
}
