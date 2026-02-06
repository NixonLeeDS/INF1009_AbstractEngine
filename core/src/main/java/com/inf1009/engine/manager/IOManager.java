package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.input.AbstractInputDevice;
import com.inf1009.engine.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class IOManager {

    private final List<AbstractInputDevice> devices;
    private final AbstractInputDevice p1;
    private final AbstractInputDevice p2;

    public IOManager() {
        devices = new ArrayList<>();
        p1 = Keyboard.player1WASD();
        p2 = Keyboard.player2Arrows();
        devices.add(p1);
        devices.add(p2);
    }

    public InputState readP1() {
        return p1.readInput();
    }

    public InputState readP2() {
        return p2.readInput();
    }

    public List<AbstractInputDevice> getDevices() {
        return devices;
    }
}
