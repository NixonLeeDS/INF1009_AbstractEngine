package com.inf1009.engine.manager;

import com.badlogic.gdx.Gdx;
import com.inf1009.engine.input.InputDevice;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IInputInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Aggregates input from multiple devices
public class InputManager implements IInputInterface {

    // Registered input devices
    private List<InputDevice> inputDevices;

    // Stores merged player input
    private InputState playerInput;

    // Key bindings for action mapping
    private Map<String, Integer> keyBindings = new HashMap<>();

    // Default constructor
    public InputManager() {
        this.inputDevices = new ArrayList<>();
        this.playerInput = new InputState();
    }

    // Optional constructor matching UML
    public InputManager(List<InputDevice> inputDevices) {
        this.inputDevices = inputDevices != null ? inputDevices : new ArrayList<>();
        this.playerInput = new InputState();
    }

    // Registers input device
    public void registerDevice(InputDevice device) {
        if (device != null) inputDevices.add(device);
    }

    // Polls all devices and processes input
    @Override
    public void update() {
        for (InputDevice d : inputDevices) {
            d.readInput();
        }
        processInput();
    }

    // Merges device input states into playerInput
    public void processInput() {

        playerInput.reset();

        for (InputDevice d : inputDevices) {
            InputState s = d.getInputState();
            playerInput.setMoveX(playerInput.getMoveX() + s.getMoveX());
            playerInput.setMoveY(playerInput.getMoveY() + s.getMoveY());
            playerInput.setJump(playerInput.isJump() || s.isJump());
        }
    }

    // Returns merged player input
    @Override
    public InputState getInputState() {
        return playerInput;
    }

    // Binds action to key
    @Override
    public void rebindKey(String action, int keyCode) {
        keyBindings.put(action, keyCode);
    }

    // Retrieves bound key
    @Override
    public int getKeyBinding(String action) {
        return keyBindings.getOrDefault(action, -1);
    }

    // Checks if action key was pressed
    @Override
    public boolean isActionJustPressed(String action) {
        Integer keyCode = keyBindings.get(action);
        if (keyCode == null) return false;
        return Gdx.input.isKeyJustPressed(keyCode);
    }

    // Reads specific device state
    public InputState readDevice(int index) {
        if (index < 0 || index >= inputDevices.size()) return new InputState();
        return inputDevices.get(index).getInputState();
    }

    // Clears registered devices
    public void clearDevices() {
        inputDevices.clear();
    }
}
