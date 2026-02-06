package com.inf1009.engine.entity;

public class StaticEntity extends AbstractGameEntity implements ICollidable {

    // Solid by default (walls, floor)
    private boolean solid = true;

    public StaticEntity(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void update(float dt) {
        // Static entities do nothing each frame
    }

    @Override
    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    @Override
    public void onCollision(ICollidable other) {
        // Default: no reaction
    }
}
