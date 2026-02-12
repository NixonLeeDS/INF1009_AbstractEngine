package com.inf1009.engine.manager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.entity.AbstractGameEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityManager {

    private final List<AbstractGameEntity> entities = new ArrayList<>();
    private final List<AbstractGameEntity> entitiesToRemove = new ArrayList<>();

    public void addEntity(AbstractGameEntity e) {
        if (e == null) return;
        entities.add(e);
    }

    public void removeEntity(AbstractGameEntity e) {
        entitiesToRemove.add(e);
    }

    public void clear() {
        entities.clear();
        entitiesToRemove.clear();
    }

    public List<AbstractGameEntity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    public void update(float dt) {

        for (AbstractGameEntity e : entities) {
            e.update(dt);
        }

        if (!entitiesToRemove.isEmpty()) {
            entities.removeAll(entitiesToRemove);
            entitiesToRemove.clear();
        }

        entities.removeIf(AbstractGameEntity::isDestroyed);
    }

    public void render(ShapeRenderer shape) {
        for (AbstractGameEntity e : entities) {
            e.render(shape);
        }
    }

}
