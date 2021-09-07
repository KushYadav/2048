package com.example.hp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.example.hp.gfx.Assets;
import com.example.hp.gfx.Display;
import com.example.hp.input.KeyManager;

public class Game implements Runnable {

    private String title;
    private int width;
    private int height;
    private boolean running;

    private Display display;
    private KeyManager keyManager;
    private Graphics g;
    private BufferStrategy bs;
    private Thread thread;
    private Movement movement;



    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init() {
        Assets.init();
        movement = new Movement();
        keyManager = new KeyManager();
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
    }

    public void tick() {
        keyManager.tick();
        movement.tick();
    }

    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.decode("#581845"));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(0xB4B6C2));
        g.fillRect(0, 94, width, height);
        movement.render(g);

        // g.drawImage(Assets.map.get(1), 0, 104, 64, 64, null);
        // g.drawImage(Assets.map.get(1), 0, 178, 64, 64, null);
        // g.drawImage(Assets.map.get(1), 0, 252, 64, 64, null);
        // g.drawImage(Assets.map.get(1), 0, 326, 64, 64, null);
        // g.drawImage(Assets.map.get(1), 74, 178, 64, 64, null);
        // g.drawImage(Assets.map.get(1), 148, 252, 64, 64, null);
        // g.drawImage(Assets.map.get(1), 222, 326, 64, 64, null);

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        long now;
        long lastTime = System.nanoTime();
        int ticks = 0;
        int timer = 0;
        double delta = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                delta = 0;
                tick();
                render();
                ticks++;
            }
            if (timer >= 1000000000) {
                System.out.println("Ticks: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}