package com.inf1009.engine.manager;

import com.inf1009.engine.input.InputDevice;
import com.inf1009.engine.input.InputState;
import java.util.ArrayList;
import java.util.List;

public class InputManager {

    // Fields
    private List<InputDevice> inputDevices = new ArrayList<>();

    // Register device
    public void registerDevice(InputDevice device) {
        if (device != null) inputDevices.add(device);
    }

    // Update all devices
    public void update() {
        for (InputDevice d : inputDevices) {
            d.readInput();
        }
    }

    // Read a specific device input by index
    public InputState readDevice(int index) {
        if (index < 0 || index >= inputDevices.size()) return new InputState();
        return inputDevices.get(index).getInputState();
    }

    // Optional merged input (not used for 2 players)
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

    // Clear devices
    public void clearDevices() {
        inputDevices.clear();
    }
}
