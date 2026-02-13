package com.inf1009.engine.manager;

import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IMoveable;

public class MovementManager {

    // Main update method
    public void update(IMoveable entity, InputState input, float dt) {
        if (entity == null || input == null) return;

        updateVelocity(entity, input);
        applyVelocity(entity, dt);
    }

    // Converts input into velocity
    public void updateVelocity(IMoveable entity, InputState input) {

        float vx = input.getMoveX() * entity.getSpeed();
        float vy = input.getMoveY() * entity.getSpeed();

        entity.setVelocityX(vx);
        entity.setVelocityY(vy);
    }

    // Applies velocity to position
    public void applyVelocity(IMoveable entity, float dt) {

        float dx = entity.getVelocityX() * dt;
        float dy = entity.getVelocityY() * dt;

        entity.move(dx, dy);
    }

    // Direct input wrapper
    public void applyInput(IMoveable entity, InputState input, float dt) {
        update(entity, input, dt);
    }

    // Set direction explicitly
    public void setDirection(IMoveable entity, float dx, float dy) {
        if (entity == null) return;
        entity.setVelocityX(dx * entity.getSpeed());
        entity.setVelocityY(dy * entity.getSpeed());
    }

    // Stop movement
    public void stop(IMoveable entity) {
        if (entity == null) return;
        entity.setVelocityX(0f);
        entity.setVelocityY(0f);
    }
}
