package com.inf1009.engine.interfaces;

public interface IMoveable {

    void move(float dx, float dy);

    float getVelocityX();
    float getVelocityY();

    void setVelocityX(float vx);
    void setVelocityY(float vy);

    float getSpeed();
}
