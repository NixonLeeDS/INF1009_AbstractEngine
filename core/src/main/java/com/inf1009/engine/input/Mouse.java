package com.inf1009.engine.input;

import com.inf1009.engine.entity.InputState;

public class Mouse extends AbstractInputDevice {

    @Override
    public InputState readInput() {
        // Not used yet, return neutral input
        return InputState.neutral();
    }
}
