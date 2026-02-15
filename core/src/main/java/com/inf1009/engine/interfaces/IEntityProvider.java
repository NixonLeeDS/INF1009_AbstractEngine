package com.inf1009.engine.interfaces;

import com.inf1009.engine.entity.GameEntity;
import java.util.List;

public interface IEntityProvider {

    List<GameEntity> getEntities();

    void addEntity(GameEntity entity);

    void removeEntity(GameEntity entity);
}
