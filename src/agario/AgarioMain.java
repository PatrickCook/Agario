/*
 * To change this license header, choute License Headers in Project Properties.
 * To change this template file, choute Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public boolean gameRunning;
    public long lastFpsTime = 0;
    public int fps = 0;
    
    private final String host = "localhost";
    private final int port = 4444;
    private BufferedReader in;
    private PrintWriter out;


    public AgarioMain() {
        //creates JFrame 
        super("Agario - Patrick Cook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 800);
        setResizable(false);
        GameCanvas gamePanel = new GameCanvas();
        setContentPane(gamePanel);
        pack();
        setVisible(true);
        addKeyListener(new TAdapter());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //what todo when closed
            }
        });
        try {
            connectToServer();
        } catch (IOException ex) {
            System.out.println("Unable to connect to server.");
        }
        //add 100 food to map
        foodArray = new ArrayList<>();
        for (int x = 0; x < 200; x++) {
            foodArray.add(new Food());
        }
        //Gets user name
        String playerName = JOptionPane.showInputDialog(this, "Enter name:");
        player.setName(playerName);
       
        //sets game to running
        gameRunning = true;
        gamePanel.gameLoop();
    }
    /**
     * Connects client to server.
     * @throws IOException 
     */
    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(
            this,
            "Enter IP Address of the Server:",
            "Welcome to the Agar.io",
            JOptionPane.QUESTION_MESSAGE);

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        
    }

    public class GameCanvas extends JPanel implements MouseMotionListener {

        Font defaultFont = new Font("Arvo", Font.BOLD, 18);

        //constructor of a JPanel that is then added to the JFrame
        public GameCanvas() {
            setPreferredSize(new Dimension(800, 640));
            setBackground(Color.WHITE);
            addMouseMotionListener(this);
            //calculates center of screen
            centerY = height / 2;
            centerX = width / 2;

            player = new Player(centerX, centerY);
            bg = new Background();

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

                // update the frame counter
                lastFpsTime += updateLength;
                fps++;

                // update our FPS counter if a second has passed since
                // we last recorded
                if (lastFpsTime >= 1000000000) {
                    lastFpsTime = 0;
                    fps = 0;
                }
                //update game
                //updateGame();
                //send data to server
                updateServer();
                //render game
                //repaint();
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

        public void updateGame() {
            //update all calculations from paint method
            bg.translate();
            //calculates collisions
            calcCollisions();
            //translate food
            for (Food f : foodArray) {
                f.translate();
            }
        }

        public void updateServer() {
                //this is what is sent to server
                out.println("");
                   String response;
                try {
                    //this is what comes back from server
                    response = in.readLine();
                    if (response == null || response.equals("")) {
                          System.exit(0);
                    }
                } catch (IOException ex) {
                       response = "Error: " + ex;
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
            canvas.setColor(new Color(0, 204, 0));
            drawCenteredCircle(canvas, centerX, centerY, (int) (1.25 * player.getRadius()));
            canvas.setColor(Color.GREEN);
            drawCenteredCircle(canvas, centerX, centerY, player.getRadius());
            canvas.setColor(Color.BLACK);

            FontMetrics fontMetrics = canvas.getFontMetrics(defaultFont);
            int nameCenter = (fontMetrics.stringWidth(player.getName())) / 2;
            canvas.setFont(defaultFont);
            canvas.drawString(player.getName(), centerX - nameCenter, centerY + 5);

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

        /**
         * Calculates the collisions between the food and the main player
         */
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
            for (int i = 0; i < newFood; i++) {
                foodArray.add(new Food());
            }
            newFood = 0;
        }

        public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
            x = x - (r / 2);
            y = y - (r / 2);
            g.fillOval(x, y, r, r);
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
        AgarioMain game = new AgarioMain();
    }

}
