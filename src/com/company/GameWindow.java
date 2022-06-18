package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {
    private final Image drop;
    private final Image back;
    private final Image go;
    private long time;
    private int vel = 200;
    private float d_left = 200;
    private float d_top = -100;
    private int score=0;
    GameWindow() throws IOException {
        super("Пробное окно");
        time = System.nanoTime();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(200, 100);
        setSize(906, 478);
        setResizable(false);
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean is_drop=
                                e.getX()>=d_left&&
                                e.getY()>=d_top&&
                                e.getX()<=(d_left+drop.getWidth(null))&&
                                e.getY()<=(d_top+drop.getHeight(null));
                if (is_drop){
                    d_top=-100;
                   d_left=(int)(Math.random()*(game_field.getWidth()-drop.getWidth(null)));
                   vel+=20;
                   score++;
                   setTitle("Score:"+score);
                }
            }
        });
        add(game_field);

        back = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
        go = ImageIO.read(GameWindow.class.getResourceAsStream("gover.png"));
    }

    public void onRepaint(Graphics graphics) {
        long curr = System.nanoTime();
        float delta = (curr - time) * 0.000000001f;
        time = curr;
        d_top = d_top + delta * vel;
        graphics.drawImage(back, 0, 0, null);
        graphics.drawImage(drop, (int) d_left, (int) d_top, null);
        if (d_top > getHeight()) graphics.drawImage(go, 0, 0, null);
    }

    public class GameField extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
