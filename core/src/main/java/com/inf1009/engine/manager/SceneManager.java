package com.inf1009.engine.manager;

import com.inf1009.engine.scene.Scene;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private Scene currentScene;
    private Map<String, Scene> scenes = new HashMap<>();

    public void addScene(String name, Scene scene) {
        if (name == null || scene == null) return;
        scenes.put(name, scene);
    }

    public void setScene(String name) {
        if (currentScene != null) currentScene.hide();
        currentScene = scenes.get(name);
        if (currentScene != null) currentScene.show();
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void update(float deltaTime) {
        if (currentScene != null) currentScene.render(deltaTime);
    }

    public void dispose() {
        for (Scene s : scenes.values()) s.dispose();
        scenes.clear();
        currentScene = null;
    }
}
