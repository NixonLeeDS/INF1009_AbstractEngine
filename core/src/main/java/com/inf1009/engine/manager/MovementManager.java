package com.inf1009.engine.manager;

import com.inf1009.engine.entity.IMoveable;
import com.inf1009.engine.entity.InputState;

public class MovementManager {
    public void applyInput(IMoveable entity, InputState input, float dt) {
        if (entity == null || input == null) return;
        entity.movement(input, dt);
    }
}
