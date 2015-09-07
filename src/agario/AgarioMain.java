/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

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
    public boolean gameRunning = false;
    public long lastFpsTime = 0;
    public int fps = 0;

    public AgarioMain() {
        //creates JFrame 
        super("Agario - Patrick Cook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setResizable(false);
        GameCanvas gamePanel = new GameCanvas();
        setContentPane(gamePanel);
        pack();
        setVisible(true);
        addKeyListener(new TAdapter());

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
        gameRunning = true;
        gamePanel.gameLoop();
    }

    public class GameCanvas extends JPanel implements MouseMotionListener {

        //constructor of a JPanel that is then added to the JFrame
        public GameCanvas() {
            setPreferredSize(new Dimension(800, 640));
            //calculates center of screen
            centerY = height / 2;
            centerX = width / 2;

            player = new Player(centerX, centerY);
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
        @Override
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
                GameProp.addX = -addX;
                GameProp.addY = addY;
            }
        }

        public void gameLoop() {
            long lastLoopTime = System.nanoTime();
            final int TARGET_FPS = 60;
            final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

            // keep looping round til the game ends
            while (gameRunning) {
                // work out how long its been since the last update, this
                // will be used to calculate how far the entities should
                // move this loop
                long now = System.nanoTime();
                long updateLength = now - lastLoopTime;
                lastLoopTime = now;
                double delta = updateLength / ((double) OPTIMAL_TIME);

                // update the frame counter
                lastFpsTime += updateLength;
                fps++;

                // update our FPS counter if a second has passed since
                // we last recorded
                if (lastFpsTime >= 1000000000) {
                    System.out.println("(FPS: " + fps + ")");
                    lastFpsTime = 0;
                    fps = 0;
                }
                //update game
                updateGame();
                //render game
                repaint();
                // we want each frame to take 10 milliseconds, to do this
                // we've recorded when we started the frame. We add 10 milliseconds
                // to this and then factor in the current time to give 
                // us our final value to wait for
                // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
                try {
                    Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
                } catch (Exception e) {
                }
            }
        }
        public void updateGame(){
            //update all calculations from paint method
            bg.translate();
            //calculates collisions
            calcCollisions();
            //translate food
            for (Food f : foodArray) {
                f.translate();     
            }
        }

        /*
         * All game drawing is done here. This includes changing colors, positions of objects and painting.
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D canvas = (Graphics2D) g;

            //moves background      
            canvas.drawImage(bg.getImg(), bg.getX(), bg.getY(), this);

            //draw food
            for (Food f : foodArray) {
                canvas.setColor(f.getColor());
                canvas.fillOval(f.getX(), f.getY(), f.getRadius(), f.getRadius());
            }
            //draw player
            canvas.setColor(player.getColor());
            canvas.fillOval(centerX, centerY, player.getRadius(), player.getRadius());

        }

        public void calcCollisions() {
            int newFood = 0;
            Iterator<Food> it = foodArray.iterator();
            while (it.hasNext()) {
                if (player.collides(player, it.next())) {
                    newFood++;
                    it.remove();
                    bg.decreaseSpeed(player.getRadius());
                    player.increaseRadius();
                }
            }
            for (int i = 0; i <= newFood;i++)
                foodArray.add(new Food());
            newFood = 0;
        }
        @Override
        public void mouseDragged(MouseEvent e) {}

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
        AgarioMain game = new AgarioMain();
    }

}
