package com.inf1009.engine.entity;


// * A non-moving entity (walls, platforms, obstacles).

public class StaticEntity extends AbstractGameEntity implements ICollidable {

    private boolean solid = true;

    public StaticEntity(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void update(float dt) {
        // Static entities do not update by default.
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
        // Default: no reaction.
    }
}
