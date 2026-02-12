package com.inf1009.engine.manager;

import com.inf1009.engine.collision.CollisionDetection;
import com.inf1009.engine.collision.CollisionHandling;
import com.inf1009.engine.interfaces.ICollidable;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    // Internal list of registered collidables
    private final List<ICollidable> collidables = new ArrayList<>();

    // Helpers
    private final CollisionDetection detection;
    private final CollisionHandling handling;

    public CollisionManager() {
        this.detection = new CollisionDetection();
        this.handling = new CollisionHandling();
    }

    // Register object for collision checking
    public void register(ICollidable c) {
        if (c == null) return;
        if (!collidables.contains(c)) {
            collidables.add(c);
        }
    }

    // Remove object from collision checking
    public void unregister(ICollidable c) {
        collidables.remove(c);
    }

    // Clear all registered objects
    public void clear() {
        collidables.clear();
    }

    // Update using internal list
    public void update() {
        if (collidables.isEmpty()) return;
        detection.detectAll(collidables, handling);
    }

    // Update using provided list
    public void update(List<ICollidable> list) {
        if (list == null || list.isEmpty()) return;
        detection.detectAll(list, handling);
    }
}
