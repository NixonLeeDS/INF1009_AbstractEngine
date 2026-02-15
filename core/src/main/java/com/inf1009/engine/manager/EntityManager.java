package com.inf1009.engine.manager;

import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.interfaces.IEntityProvider;

import java.util.ArrayList;
import java.util.List;

public class EntityManager implements IEntityProvider {

    private List<GameEntity> entities = new ArrayList<>();
    private List<GameEntity> entitiesToAdd = new ArrayList<>();
    private List<GameEntity> entitiesToRemove = new ArrayList<>();

    public void update(float deltaTime) {

        // Add queued entities
        if (!entitiesToAdd.isEmpty()) {
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
        }

        // Remove queued entities
        if (!entitiesToRemove.isEmpty()) {
            entities.removeAll(entitiesToRemove);
            entitiesToRemove.clear();
        }

        // Update entities
        for (GameEntity e : entities) {

            e.update(deltaTime);

        }

        removeDeadEntities();
    }

    public void addEntity(GameEntity e) {
        if (e != null) entitiesToAdd.add(e);
    }

    public void removeEntity(GameEntity entity) {
    if (entity != null) entitiesToRemove.add(entity);
    }

    public List<GameEntity> getEntities() {
        return entities;
    }

    public List<GameEntity> getEntitiesAt(float x, float y) {
        List<GameEntity> result = new ArrayList<>();
        for (GameEntity e : entities) {
            if (e.getBounds().contains(x, y)) {
                result.add(e);
            }
        }
        return result;
    }

    private void removeDeadEntities() {
        entities.removeIf(GameEntity::isDestroyed);
    }

    public void clear() {
        entities.clear();
        entitiesToAdd.clear();
        entitiesToRemove.clear();
    }
}
