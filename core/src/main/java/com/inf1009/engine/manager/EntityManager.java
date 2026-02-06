package com.inf1009.engine.manager;

import com.inf1009.engine.entity.AbstractGameEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private final List<AbstractGameEntity> entityList = new ArrayList<>();

    public void addEntity(AbstractGameEntity entity) {
        entityList.add(entity);
    }

    public void update(float dt) {
        for (AbstractGameEntity e : entityList) {
            e.update(dt);
        }
        entityList.removeIf(AbstractGameEntity::isDestroyed);
    }

    public List<AbstractGameEntity> getEntities() {
        return entityList;
    }


    public void clear() {

    }
}