package com.inf1009.engine.manager;

import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.InputState;


//Applies InputState to entities.

public class MovementManager {

    public void applyInput(DynamicEntity entity, InputState input, float dt) {
        if (entity == null || input == null) return;
        entity.movement(input, dt);
    }
}
