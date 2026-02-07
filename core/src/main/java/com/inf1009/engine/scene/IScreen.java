package com.inf1009.engine.scene;

public interface IScreen {
    void show();
    void render(float dt);
    void hide();
    void dispose();
}
