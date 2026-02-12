package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.interfaces.ICollidable;

public class StaticEntity extends AbstractGameEntity implements ICollidable {

    private boolean solid = true;

    public StaticEntity(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void update(float dt) {
        // Static entity does nothing per frame
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

    @Override
    public void render(ShapeRenderer shape) {
        shape.rect(getX(), getY(), getW(), getH());
    }
}
