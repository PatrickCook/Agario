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
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author patrickcook
 */
public class AgarioMain extends JFrame {

    private int centerX, centerY;
    private int width = 800;
    private int height = 640;
    private Player player;
    private Background bg;
    private ArrayList<Food> foodArray;
    private CollisionHandler collisionHandler;

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

        collisionHandler = new CollisionHandler();

        foodArray = new ArrayList<Food>();
        //add 100 food to map
        for (int x = 0; x < 100; x++) {
            foodArray.add(new Food());
        }

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

            player = new Player();
            bg = new Background();

            addMouseMotionListener(this);
        }

        /**
         * Gets the coordinates of mouse. Makes sure they aren't zero. Creates
         * direction out of X and Y from mouse. Moves background in the opposite
         * direction of mouse.
         *
         * @param evt
         */
        public synchronized void mouseMoved(MouseEvent evt) {
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
                for (Food f : foodArray) {
                    f.setTranslate(-addX, addY);
                }
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
            //moves background
            for (Food f : foodArray) {
                f.translate();
            }
            bg.translate();

            //draw background
            canvas.drawImage(bg.getImg(), bg.getX(), bg.getY(), this);

            foodArray = collisionHandler.calcCollisions(foodArray, player, centerX, centerY, bg);
            //draw food
            for (Food f : foodArray) {
                canvas.setColor(f.getRandomColor());
                canvas.fillOval(f.getX(), f.getY(), f.getRadius(), f.getRadius());
            }

            canvas.setColor(Color.GREEN);
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
