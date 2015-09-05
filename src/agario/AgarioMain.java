/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author patrickcook
 */
public class AgarioMain extends JFrame {

    private int centerX, centerY;
    private int width = 800;
    private int height = 640;
    private Circle player;
    private Background bg;

    public AgarioMain() {
        //creates JFrame 
        super("Agario - Patrick Cook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setResizable(false);
        setContentPane(new GameCanvas());
        pack();
        setVisible(true);
        addKeyListener(new TAdapter());

        //makes cursor invisible when inside game
        /*
         setCursor(getToolkit().createCustomCursor(
         new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
         "null"));
         */
    }

    public class GameCanvas extends JPanel implements MouseMotionListener {

        //constructor of a JPanel that is then added to the JFrame
        public GameCanvas() {
            setPreferredSize(new Dimension(800, 640));
            //calculates center of screen
            centerY = height / 2;
            centerX = width / 2;

            player = new Circle();
            bg = new Background();

            addMouseMotionListener(this);
        }

        /**
         * Gets the coordinates of mouse. Makes sure they arent zero. Creates
         * direction out of X and Y from mouse. Moves background in the opposite
         * direction of mouse.
         *
         * @param evt
         */
        public void mouseMoved(MouseEvent evt) {
            //get position of mouse
            double mouseX = (evt.getX() - centerX);
            double mouseY = -(evt.getY() - centerY);
            //check if distance is 0 because we cant divide by zero
            if (mouseY != 0 && mouseX != 0) {
                //calculates angle of mouse in relation to center dot
                double angle = Math.atan2(mouseY, mouseX);
                double addX = Math.cos(angle);
                double addY = Math.sin(angle);
                bg.setTranslate(-addX, addY);
            }
            // schedule a repaint.
            repaint();

        }

        @Override
        public void paint(Graphics g) {        
            super.paint(g);
            Graphics2D canvas = (Graphics2D) g;
            /*
             * All game drawing is done here. This includes changing colors, positions of objects and painting.
             */
            canvas.setColor(Color.GREEN);
            //moves backgroun
            bg.translate();
            canvas.drawImage(bg.getImg(), bg.getX(), bg.getY(), this);
            canvas.fillOval(centerX, centerY, player.getRadius(), player.getRadius());
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

    }

//Handles Key Presses
    class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                //SPLIT Circle
            }
        }
    }

    public static void main(String[] args) {
        JFrame game = new AgarioMain();
    }

}
