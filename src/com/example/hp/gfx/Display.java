package com.example.hp.gfx;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Canvas;

public class Display {

    String title;
    int width;
    int height;
    private JFrame frame;
    private Canvas canvas;

    public Display(String title,int width,int height){
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }
    
    public void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setMaximumSize(new Dimension(width,height));
        canvas.setMinimumSize(new Dimension(width,height));
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack();
    }

    public JFrame getFrame(){
        return frame;
    }

    public Canvas getCanvas(){
        return canvas;
    }
}
