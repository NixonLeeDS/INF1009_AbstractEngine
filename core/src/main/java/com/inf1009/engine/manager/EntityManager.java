package com.inf1009.engine.manager;

import com.inf1009.engine.entity.AbstractGameEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Manages lifecycle of all game entities
public class EntityManager {

    // Internal entity list (owned by manager)
    private final List<AbstractGameEntity> entities = new ArrayList<>();

    // Add a new entity to the world
    public void addEntity(AbstractGameEntity entity) {
        if (entity == null) return;
        entities.add(entity);
    }

    // Remove a specific entity
    public void removeEntity(AbstractGameEntity entity) {
        entities.remove(entity);
    }

    // Remove all entities (scene reset)
    public void clear() {
        entities.clear();
    }

    // Read-only access for rendering / collision
    public List<AbstractGameEntity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    // Update all entities and clean up destroyed ones
    public void update(float dt) {
        for (AbstractGameEntity e : entities) {
            e.update(dt);
        }

        // Remove entities marked as destroyed
        entities.removeIf(AbstractGameEntity::isDestroyed);
    }
}
