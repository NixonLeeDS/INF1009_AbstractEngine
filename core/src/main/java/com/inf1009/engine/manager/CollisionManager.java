package com.inf1009.engine.manager;

import com.inf1009.engine.collision.CollisionDetection;
import com.inf1009.engine.collision.CollisionHandling;
import com.inf1009.engine.interfaces.ICollidable;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    private final List<ICollidable> collidables = new ArrayList<>();
    private final CollisionDetection detection;
    private final CollisionHandling handling;

    public CollisionManager() {
        this.detection = new CollisionDetection();
        this.handling = new CollisionHandling();
    }

    public void register(ICollidable c) {
        if (c == null) return;
        if (!collidables.contains(c)) {
            collidables.add(c);
        }
    }

    public void unregister(ICollidable c) {
        collidables.remove(c);
    }

    public void clear() {
        collidables.clear();
    }

    // Internal list update
    public void update() {

        if (collidables.isEmpty()) return;

        List<ICollidable[]> collisions = detection.detectAll(collidables);

        for (ICollidable[] pair : collisions) {
            handling.resolve(pair[0], pair[1]);
        }
    }

    // External list update
    public void update(List<ICollidable> list) {

        if (list == null || list.isEmpty()) return;

        List<ICollidable[]> collisions = detection.detectAll(list);

        for (ICollidable[] pair : collisions) {
            handling.resolve(pair[0], pair[1]);
        }
    }
}
