package com.Programming.Entities;

import java.awt.*;

public class Enemy extends Entity {

    public Enemy(int x, int y) {
        super(x, y, 50, 50, "red", EntityType.Enemy);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(point.x, point.y, size.width, size.height);
    }
}
