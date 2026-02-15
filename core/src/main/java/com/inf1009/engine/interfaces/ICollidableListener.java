package com.inf1009.engine.interfaces;

import com.inf1009.engine.entity.GameEntity;

public interface ICollidableListener {
    void onCollision(GameEntity entity1, GameEntity entity2);
}
