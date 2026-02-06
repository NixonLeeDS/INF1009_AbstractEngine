package com.inf1009.engine.input;

import com.inf1009.engine.entity.InputState;

public class Mouse extends AbstractInputDevice {
    @Override
    public InputState readInput() {
        return InputState.neutral();
    }
}
