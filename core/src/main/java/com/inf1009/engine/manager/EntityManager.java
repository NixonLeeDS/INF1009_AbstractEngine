package com.inf1009.engine.manager;

import com.inf1009.engine.entity.AbstractGameEntity;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private List<AbstractGameEntity> entities;

    public EntityManager() {
        entities = new ArrayList<>();
    }

    public void addEntity(AbstractGameEntity entity) {
        entities.add(entity);
    }

    public void update(float deltaTime) {
        for (AbstractGameEntity entity : entities) {
            entity.update(deltaTime);
        }
    }

    public List<AbstractGameEntity> getEntities() {
        return entities;
    }
}
