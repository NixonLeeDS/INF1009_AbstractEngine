package com.inf1009.engine.collision;

import java.util.ArrayList;
import java.util.List;
import com.inf1009.engine.interfaces.ICollidable;

public class CollisionDetection {

    // Detect collision between two objects
    public boolean detect(ICollidable a, ICollidable b) {
        return a.getBounds().overlaps(b.getBounds());
    }

    // Detect all collision pairs
    public List<ICollidable[]> detectAll(List<ICollidable> collidables) {

        List<ICollidable[]> collisions = new ArrayList<>();

        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {

                ICollidable a = collidables.get(i);
                ICollidable b = collidables.get(j);

                if (detect(a, b)) {
                    collisions.add(new ICollidable[]{a, b});
                }
            }
        }

        return collisions;
    }
}
