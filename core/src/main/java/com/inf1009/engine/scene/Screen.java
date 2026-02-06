package com.inf1009.engine.scene;

// Screen lifecycle contract
public interface Screen {
    void show();
    void render(float dt);
    void hide();
    void dispose();
}
