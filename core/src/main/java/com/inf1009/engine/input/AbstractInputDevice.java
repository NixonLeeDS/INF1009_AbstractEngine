package com.inf1009.engine.input;

import com.inf1009.engine.entity.InputState;

public abstract class AbstractInputDevice {

    // Return an input snapshot for this frame
    public abstract InputState readInput();
}
