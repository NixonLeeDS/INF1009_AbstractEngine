package com.inf1009.engine.scene;

import com.inf1009.engine.GameMaster;

public abstract class AbstractScreen implements IScreen {

    protected final GameMaster game;

    protected AbstractScreen(GameMaster game) {
        this.game = game;
    }

    @Override
    public void hide() {
        // optional default behaviour
    }

    @Override
    public void dispose() {
        // optional default behaviour
    }
}
