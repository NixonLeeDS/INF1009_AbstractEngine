package com.inf1009.engine.manager;

import com.inf1009.engine.collision.CollisionDetection;
import com.inf1009.engine.collision.CollisionHandling;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.ICollidableListener;
import com.inf1009.engine.interfaces.IEntityProvider;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    private IEntityProvider entityProvider;
    private CollisionDetection detection = new CollisionDetection();
    private CollisionHandling handling = new CollisionHandling();
    private List<ICollidableListener> listeners = new ArrayList<>();

    public CollisionManager(IEntityProvider provider) {
        this.entityProvider = provider;
    }

    public void addCollisionListener(ICollidableListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeCollisionListener(ICollidableListener listener) {
        listeners.remove(listener);
    }

    public void checkCollisions() {

        List<ICollidable> collidables = new ArrayList<>();

        for (GameEntity e : entityProvider.getEntities()) {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        }

        for (CollisionDetection.Pair p : detection.detectAll(collidables)) {

            handling.resolve(p.a, p.b);

            GameEntity e1 = (GameEntity) p.a;
            GameEntity e2 = (GameEntity) p.b;

            for (ICollidableListener listener : listeners) {
                listener.onCollision(e1, e2);
            }
        }
    }

    public void update() {
        checkCollisions();
    }
}
