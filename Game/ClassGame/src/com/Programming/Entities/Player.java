package com.Programming.Entities;

import com.Programming.Map;

import java.awt.*;

public class Player extends Entity {

    public Player(Point p) {
        super(p.x, p.y, 50, 50, "blue", EntityType.Player);
    }

    public boolean isOnGround(Map map) {
        /*for (int x = 0; x < map.width; x++) {
            for (int y = 0; y < map.height; y++) {
                // Check for position of the current number * 50 against the position of the player
            }
        }*/
        return false;
    }
}
