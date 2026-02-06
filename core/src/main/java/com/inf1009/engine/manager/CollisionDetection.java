package com.inf1009.engine.manager;

import com.inf1009.engine.entity.ICollidable;
import java.util.ArrayList;
import java.util.List;

public class CollisionDetection {

    // Brute force pair detection (fine for small demo)
    public List<CollisionPair> detectAll(List<ICollidable> collidables) {
        List<CollisionPair> pairs = new ArrayList<>();

        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {
                ICollidable a = collidables.get(i);
                ICollidable b = collidables.get(j);

                // Rectangle overlap check
                if (a.getBounds().overlaps(b.getBounds())) {
                    pairs.add(new CollisionPair(a, b));
                }
            }
        }

        return pairs;
    }
}
