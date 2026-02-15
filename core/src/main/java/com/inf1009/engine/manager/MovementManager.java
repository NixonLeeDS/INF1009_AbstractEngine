package com.inf1009.engine.manager;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IMovable;

public class MovementManager {

    public void update(IMovable entity, InputState inputState, float dt) {

        if (entity == null) return;

        if (inputState != null) {
            setDirection(entity, inputState.getMoveX(), inputState.getMoveY());
        }

        updateVelocity(entity, dt);
        applyVelocity(entity, dt);
    }

    public void applyGravity(IMovable entity, float gravity, float dt) {
        if (entity == null) return;

        Vector2 velocity = entity.getVelocity();
        velocity.y -= gravity * dt;
        entity.setVelocity(velocity);
    }

    public void applyVelocity(IMovable entity, float dt) {
        entity.applyVelocity(dt);
    }

    public void updateVelocity(IMovable entity, float dt) {

        Vector2 acceleration = entity.getAcceleration();
        Vector2 velocity = entity.getVelocity();

        velocity.x += acceleration.x * dt;
        velocity.y += acceleration.y * dt;

        entity.setVelocity(velocity);
    }

    public void setDirection(IMovable entity, float dx, float dy) {
        entity.setDirection(dx, dy);
    }

    public void stop(IMovable entity) {
        entity.setVelocity(new Vector2(0, 0));
        entity.setSpeed(0f);
    }
}
