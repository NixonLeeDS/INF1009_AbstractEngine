package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.input.AbstractInputDevice;
import com.inf1009.engine.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOManager {

    // UML: devices : List<AbstractInputDevice>
    private final List<AbstractInputDevice> devices = new ArrayList<>();

    // UML: playerInput : InputState
    private InputState playerInput = new InputState(0, 0, false);

    // UML: bindings : InputMap
    // (simple implementation to satisfy UML)
    private final Map<String, Integer> bindings = new HashMap<>();

    public IOManager() {
        // Engine-level device registration (still abstract)
        devices.add(Keyboard.player1WASD());
        devices.add(Keyboard.player2Arrows());
    }

    // UML: getDevices()
    public List<AbstractInputDevice> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    // UML: update()
    public void update() {
        processInput();
    }

    // UML: getInputState()
    public InputState getInputState() {
        return playerInput;
    }

    // UML: processInput()
    // Default behaviour: read from first device
    public void processInput() {
        if (!devices.isEmpty()) {
            playerInput = devices.get(0).readInput();
        }
    }

    // UML: setBinding(action, keyCode)
    public void setBinding(String action, int inputKeyCode) {
        bindings.put(action, inputKeyCode);
    }

    // ENGINE-SAFE helper (used by SimulatorScreen)
    public InputState readInput(int deviceIndex) {
        if (deviceIndex < 0 || deviceIndex >= devices.size()) {
            return new InputState(0, 0, false);
        }
        return devices.get(deviceIndex).readInput();
    }
}
