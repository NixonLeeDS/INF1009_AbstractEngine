package com.inf1009.engine.manager;

import java.util.List;

public class CollisionHandling {

    // Applies collision responses for all detected collision pairs
    public void resolve(List<CollisionPair> pairs) {

        // Loop through every detected collision
        for (CollisionPair p : pairs) {

            // Notify first object it collided with second
            p.getA().onCollision(p.getB());

            // Notify second object it collided with first
            p.getB().onCollision(p.getA());
        }
    }
}
