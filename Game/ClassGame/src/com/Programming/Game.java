package com.Programming;

import com.Programming.Entities.Enemy;
import com.Programming.Entities.Entity;
import com.Programming.Entities.Player;
import com.Programming.Input.InputManager;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// Threading
    private Thread thread;
    private boolean running;

    // Graphics
    private BufferStrategy bs;
    private Graphics g;
    private Frame frame;

    // Input
    private InputManager inputManager;

    // Entities
    private ArrayList<Entity> entities;
    private Player player;
    private Point startPos = new Point(50, 50);

//    private Map map;

    private long updateTimer = 0;

    public Game(int w, int h) {
        Dimension size = new Dimension(w, h);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        setFocusable(true);
        requestFocus();

        frame = new Frame();
//        frame.setUndecorated(true);
        frame.add(this);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                stop();
            }
        });
        frame.setVisible(true);

        inputManager = new InputManager(this);
        addKeyListener(inputManager.keyboard);
    }

    public synchronized void start() {
        if(running) return;
        running = true;

        createBufferStrategy(2);
        bs = getBufferStrategy();
        if(bs == null) return;

        player = new Player(startPos);

        entities = new ArrayList<>();
        entities.add(new Entity(0, 0, getWidth(), 10, "black", Entity.EntityType.Wall));
        entities.add(new Entity(0, getHeight() - 10, getWidth(), 10, "black", Entity.EntityType.Wall));
        entities.add(new Entity(0, 0, 10, getHeight(), "black", Entity.EntityType.Wall));
        entities.add(new Entity(getWidth() - 10, 0, 10, getHeight(), "black", Entity.EntityType.Wall));

        entities.add(new Enemy(500, 500));

        updateTimer = System.currentTimeMillis();

        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join(100);
            thread = null;
            frame.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void render() {
        g = bs.getDrawGraphics();

        g.setColor(Color.gray);
        g.fillRect(0, 0, getWidth(), getHeight());

        player.render(g);

        for(Entity e : entities)
            e.render(g);

        g.dispose();
        bs.show();
    }

    private void update() {
//        if(player.isOnGround(map))
        if(player.point.y < getHeight() - 90) {
            if(System.currentTimeMillis() - updateTimer >= 15) {
                player.yAccel += 1;
                updateTimer = System.currentTimeMillis();
            }
        } else {
            player.yAccel = 0;
            player.point.y = getHeight() - 90;
            inputManager.keyboard.canJump = true;
        }

        player.point.y += player.yAccel;

        if((boolean)inputManager.keyboard.keys.get("left"))
            player.point.x -= 3;
        if((boolean)inputManager.keyboard.keys.get("right"))
            player.point.x += 3;
        if((boolean)inputManager.keyboard.keys.get("jump")) {
            player.yAccel = -20;
            player.point.y -= 1;
            inputManager.keyboard.keys.put("jump", false);
        }

        int xD, yD;
        for(Iterator<Entity> i = entities.iterator(); i.hasNext();) {
            Entity e = i.next();
            if (e.type == Entity.EntityType.Enemy || e.type == Entity.EntityType.Player) { // To make sure walls don't move because they are counted as entities
                xD = player.point.x - e.point.x;
                yD = player.point.y - e.point.y;
                if(xD > 3)
                    e.point.x++;
                else if(xD < 3)
                    e.point.x--;
                if(yD > 3)
                    e.point.y++;
                else if(yD < 3)
                    e.point.y--;
            }

            if(player.point.x + player.size.width > e.point.x && player.point.x < e.point.x + e.size.width) {
                if (player.point.y + player.size.width > e.point.y && player.point.y < e.point.y + e.size.height) {
                    player.point = new Point(startPos);
                }
            }
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        final double ns = 1000000000.0 / 60.0;
        double delta = 0;

        int frames = 0, updates = frames;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + ", Updates: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
