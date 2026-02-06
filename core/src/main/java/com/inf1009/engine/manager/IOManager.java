package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates input devices.
 * Demo implementation: two keyboard devices (P1 and P2).
 */
public class IOManager {

    private final List<Keyboard> devices;
    private final Keyboard p1;
    private final Keyboard p2;

    public IOManager() {
        this.devices = new ArrayList<>();
        this.p1 = Keyboard.player1WASD();
        this.p2 = Keyboard.player2Arrows();
        devices.add(p1);
        devices.add(p2);
    }

    public InputState readP1() {
        return p1.readInput();
    }

    public InputState readP2() {
        return p2.readInput();
    }

    public List<Keyboard> getDevices() {
        return devices;
    }
}
