package com.inf1009.engine.manager;

import java.util.HashMap;
import java.util.Map;

import com.inf1009.engine.scene.AbstractScreen;

public class SceneManager {

    private final Map<String, AbstractScreen> screens = new HashMap<>();
    private AbstractScreen current;

    public void addScreen(String name, AbstractScreen screen) {
        screens.put(name, screen);
    }

    public void setScreen(String name) {

        AbstractScreen next = screens.get(name);
        if (next == null) return;

        if (current != null) current.hide();

        current = next;
        current.show();
    }

    public void render(float dt) {
        if (current != null) current.render(dt);
    }

    public void dispose() {
        for (AbstractScreen s : screens.values()) {
            s.dispose();
        }
        screens.clear();
    }
}
