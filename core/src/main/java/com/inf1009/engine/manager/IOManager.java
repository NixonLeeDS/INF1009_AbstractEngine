package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

// Coordinates input devices and player inputs
public class IOManager {

    // All registered input devices
    private final List<Keyboard> devices;

    // Player 1 and Player 2 keyboards
    private final Keyboard p1;
    private final Keyboard p2;

    public IOManager() {
        this.devices = new ArrayList<>();

        // Default player key mappings
        this.p1 = Keyboard.player1WASD();
        this.p2 = Keyboard.player2Arrows();

        devices.add(p1);
        devices.add(p2);
    }

    // Read Player 1 input snapshot
    public InputState readP1() {
        return p1.readInput();
    }

    // Read Player 2 input snapshot
    public InputState readP2() {
        return p2.readInput();
    }

    // Returns all input devices
    public List<Keyboard> getDevices() {
        return devices;
    }
}
