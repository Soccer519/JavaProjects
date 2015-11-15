package com.Programming.Input;

import com.Programming.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Keyboard extends KeyAdapter {

    public HashMap<String, Object> keys;
    private int key;

    private Game game;

    public boolean canJump;

    public Keyboard(Game game) {
        this.game = game;
        keys = new HashMap<>();
        keys.put("forward", false);
        keys.put("back", false);
        keys.put("left", false);
        keys.put("right", false);
        keys.put("jump", false);
    }

    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
            keys.put("forward", true);
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
            keys.put("back", true);
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
            keys.put("left", true);
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
            keys.put("right", true);
        if(key == KeyEvent.VK_SPACE) {
            if(canJump) {
                keys.put("jump", true);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        key = e.getKeyCode();
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
            keys.put("forward", false);
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
            keys.put("back", false);
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
            keys.put("left", false);
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
            keys.put("right", false);
        if(key == KeyEvent.VK_ESCAPE)
            game.stop();
    }
}
