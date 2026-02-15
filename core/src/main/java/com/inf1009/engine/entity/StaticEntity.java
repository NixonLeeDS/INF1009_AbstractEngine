package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.interfaces.ICollidable;

public class StaticEntity extends GameEntity implements ICollidable {

    // Field
    private boolean isSolid;

    // Constructor (UML shows 4 parameters)
    public StaticEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.isSolid = true;
    }

    // Lifecycle
    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void render(ShapeRenderer shape) {
        shape.setColor(0.3f, 0.3f, 0.3f, 1f);
        shape.rect(x, y, width, height);
    }


    // ICollidable methods
    @Override
    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        this.isSolid = solid;
    }

    @Override
    public void onCollision(ICollidable other) {
    }
}
