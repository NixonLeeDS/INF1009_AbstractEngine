package com.inf1009.engine.manager;

import com.inf1009.engine.entity.Moveable;
import java.util.List;

public class MovementManager {

    public void applyMovement(List<Moveable> moveables) {
        for (Moveable m : moveables) {
            m.movement();
        }
    }
}
