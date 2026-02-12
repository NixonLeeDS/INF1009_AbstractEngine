package com.inf1009.engine.collision;

import java.util.List;

import com.inf1009.engine.interfaces.ICollidable;

public class CollisionDetection {

    // Brute force O(n^2) collision check
    public void detectAll(List<ICollidable> collidables,
                          CollisionHandling handling) {

        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {

                ICollidable a = collidables.get(i);
                ICollidable b = collidables.get(j);

                if (a.getBounds().overlaps(b.getBounds())) {
                    handling.resolve(a, b);
                }
            }
        }
    }
}
