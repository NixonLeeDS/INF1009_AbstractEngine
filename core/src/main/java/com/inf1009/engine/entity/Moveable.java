package com.inf1009.engine.entity;


//Implemented by entities that can be moved via an InputState.

public interface Moveable {
    void movement(InputState input, float dt);
}
