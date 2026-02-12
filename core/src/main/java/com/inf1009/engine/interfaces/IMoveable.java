package com.inf1009.engine.interfaces;

import com.inf1009.engine.entity.InputState;

public interface IMoveable {

    // Apply input-based movement for this frame
    void movement(InputState input, float dt);
}
