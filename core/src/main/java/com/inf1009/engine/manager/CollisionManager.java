package com.inf1009.engine.manager;

import com.inf1009.engine.entity.ICollidable;
import java.util.List;

public class CollisionManager {

    public void checkCollisions(List<ICollidable> collidables) {
        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {
                ICollidable a = collidables.get(i);
                ICollidable b = collidables.get(j);

                if (a.getBounds().overlaps(b.getBounds())) {
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }
    }
}
