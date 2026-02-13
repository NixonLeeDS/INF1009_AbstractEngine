package com.inf1009.engine.manager;

import com.inf1009.engine.input.AbstractInputDevice;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.input.Keyboard;

import java.util.*;

public class IOManager {

    private final List<AbstractInputDevice> devices = new ArrayList<>();
    private final Map<String, Integer> bindings = new HashMap<>();

    private InputState playerInput = InputState.neutral();

    public IOManager() {
        devices.add(Keyboard.wasd());
        devices.add(Keyboard.arrows());
    }

    public List<AbstractInputDevice> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    public void addDevice(AbstractInputDevice device) {
        if (device == null) return;
        devices.add(device);
    }

    public InputState readDevice(int index) {
        if (index < 0 || index >= devices.size()) {
            return InputState.neutral();
        }
        return devices.get(index).readInput();
    }

    public void update() {
        if (!devices.isEmpty()) {
            playerInput = devices.get(0).readInput();
        }
    }

    public void processInput(int index) {
        playerInput = readDevice(index);
    }

    public InputState getInputState(int index) {
        return readDevice(index);
    }

    public InputState getPlayerInput() {
        return playerInput;
    }

    public void setBinding(String action, int keyCode) {
        bindings.put(action, keyCode);
    }

    public Integer getBinding(String action) {
        return bindings.get(action);
    }
}
