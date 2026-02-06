package com.inf1009.engine.manager;

import com.inf1009.engine.entity.ICollidable;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    // Internal list (optional mode: register/unregister)
    private final List<ICollidable> collidables = new ArrayList<>();

    // Split responsibilities (UML: detection + handling)
    private final CollisionDetection detection;
    private final CollisionHandling handling;

    public CollisionManager() {
        // Create helpers once
        this.detection = new CollisionDetection();
        this.handling = new CollisionHandling();
    }

    // Add a collidable into internal list
    public void register(ICollidable c) {
        if (c == null) return;
        if (!collidables.contains(c)) collidables.add(c);
    }

    // Remove a collidable from internal list
    public void unregister(ICollidable c) {
        collidables.remove(c);
    }

    // Clear internal list (reset)
    public void clearCollisions() {
        collidables.clear();
    }

    // Detect + resolve using internal list
    public void checkCollisions() {
        List<CollisionPair> pairs = detection.detectAll(collidables);
        handling.resolve(pairs);
    }

    // Detect + resolve using a provided list (screen decides what to check)
    public void update(List<ICollidable> list) {
        if (list == null || list.isEmpty()) return;
        List<CollisionPair> pairs = detection.detectAll(list);
        handling.resolve(pairs);
    }

    // Convenience: update internal list mode
    public void update() {
        checkCollisions();
    }
}
