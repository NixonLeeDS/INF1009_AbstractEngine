package com.inf1009.engine.manager;

import com.inf1009.engine.scene.IScreen;
import java.util.HashMap;
import java.util.Map;

// Manages screen lifecycle and transitions
public class SceneManager {

    // Registered screens by name
    private final Map<String, IScreen> screens = new HashMap<>();

    // Currently active screen
    private IScreen current;

    // Add a new screen
    public void addScreen(String name, IScreen screen) {
        screens.put(name, screen);
    }

    // Switch to another screen
    public void setScreen(String name) {
        IScreen next = screens.get(name);
        if (next == null) return;

        if (current != null) current.hide();
        current = next;
        current.show();
    }

    // Render current screen
    public void render(float dt) {
        if (current != null) current.render(dt);
    }

    // Dispose all screens
    public void dispose() {
        for (IScreen s : screens.values()) {
            s.dispose();
        }
    }
}
