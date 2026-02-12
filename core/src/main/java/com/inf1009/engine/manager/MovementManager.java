package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.interfaces.IMoveable;

public class MovementManager {
    public void applyInput(IMoveable entity, InputState input, float dt) {
        if (entity == null || input == null) return;
        entity.movement(input, dt);
    }
}
