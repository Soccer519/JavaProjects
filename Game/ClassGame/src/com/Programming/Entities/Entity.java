package com.Programming.Entities;

import java.awt.*;
import java.lang.reflect.Field;

public class Entity {

    public enum EntityType {
        Enemy("Enemy"), Wall("Player"), Player("Player");

        private String value;
        EntityType(String s) {
            value = s;
        }
        public String getValue() { return value; }
    }

    public Point point;
    public Dimension size;
    public Color color;
    public EntityType type;
    public int yAccel, xAccel;

    public Entity(int x, int y, int w, int h, String colorStr, EntityType type) {
        this(colorStr);
        point = new Point(x, y);
        size = new Dimension(w, h);
        this.type = type;
        yAccel = 0;
        xAccel = 0;
    }

    public Entity(String str) {
        try {
            Field field = Class.forName("java.awt.Color").getField(str);
            color = (Color)field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(point.x, point.y, size.width, size.height);
    }
}
