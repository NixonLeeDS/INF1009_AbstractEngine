package com.inf1009.engine.scene;

import com.inf1009.engine.GameMaster;

public abstract class AbstractScreen {

    protected final GameMaster game;

    protected AbstractScreen(GameMaster game) {
        this.game = game;
    }

    public abstract void show();

    public abstract void render(float dt);

    public void hide() {
        // optional override
    }

    public void dispose() {
        // optional override
    }
}
