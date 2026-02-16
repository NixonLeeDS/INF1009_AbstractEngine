package com.inf1009.engine.manager;

import com.badlogic.gdx.Gdx;
import com.inf1009.engine.input.InputDevice;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IInputManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputManager implements IInputManager {

    private List<InputDevice> inputDevices = new ArrayList<>();

    // simple key binding storage
    private Map<String, Integer> keyBindings = new HashMap<>();

    // Register device
    public void registerDevice(InputDevice device) {
        if (device != null) inputDevices.add(device);
    }

    // IInputManager

    @Override
    public void update() {
        for (InputDevice d : inputDevices) {
            d.readInput();
        }
    }

    @Override
    public InputState getInputState() {
        InputState merged = new InputState();

        for (InputDevice d : inputDevices) {
            InputState s = d.getInputState();

            merged.setMoveX(merged.getMoveX() + s.getMoveX());
            merged.setMoveY(merged.getMoveY() + s.getMoveY());
            merged.setJump(merged.isJump() || s.isJump());
        }

        return merged;
    }

    @Override
    public void rebindKey(String action, int keyCode) {
        keyBindings.put(action, keyCode);
    }

    @Override
    public int getKeyBinding(String action) {
        return keyBindings.getOrDefault(action, -1);
    }

    @Override
    public boolean isActionJustPressed(String action) {
        Integer keyCode = keyBindings.get(action);
        if (keyCode == null) return false;
        return Gdx.input.isKeyJustPressed(keyCode);
    }


    // Optional helper

    public InputState readDevice(int index) {
        if (index < 0 || index >= inputDevices.size()) return new InputState();
        return inputDevices.get(index).getInputState();
    }

    public void clearDevices() {
        inputDevices.clear();
    }
}
