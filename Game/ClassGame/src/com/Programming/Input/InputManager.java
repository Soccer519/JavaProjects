package com.Programming.Input;

import com.Programming.Game;

public class InputManager {

    public Keyboard keyboard;
//    public Mouse mouse;
//    public Motion mouseMotion;

    public InputManager(Game game) {
        keyboard = new Keyboard(game);
    }
}
