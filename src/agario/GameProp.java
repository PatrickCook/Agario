/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author patrickcook
 */
public class GameProp implements Serializable{
    protected int radius;
    protected double x;
    protected double y;
    public static double addX, addY;
    public static double speedRate;
    protected ArrayList<Color> colors = new ArrayList<Color>
                 (Arrays.asList(Color.RED, Color.ORANGE,
                               Color.YELLOW, Color.GREEN,
                               Color.BLUE, Color.PINK,Color.MAGENTA));
    protected Color color;
    //default constructor
    public GameProp(){
        this.x = 0;
        this.y = 0;
        this.radius = 40;
        int random = (int)(Math.random() * colors.size()); 
        color = colors.get(random);
    }

    //Increases radius when player eats food
    public void increaseRadius(){
        radius++;
    }
    //moves background props in opposite direction of mouse movement
    public void translate() { 
        x += addX * speedRate;
        y += addY * speedRate;
    }
    public void decreaseSpeed(double radius) {
        //gradual speed decay graph
        speedRate = 7 * Math.pow(Math.E, -((radius-20)/100));
    }
    /**
     * Checks collision between two circles
     * @param c2
     * @return boolean if collided
     */
    public boolean collides(GameProp c1, GameProp c2){
        //adding half of the radius to x and y to make x and y 
        //the center of the circle instead of top left corner
        double dx = c1.x - 400;
        double dy = c1.y - 320;
        double distance = dx * dx + dy * dy;
        float radiusSum = c1.radius/2 + c2.radius/2;
        return distance < radiusSum * radiusSum;
    }
    public Color getColor(){return color;}
    public int getRadius(){ return radius;}
    public int getX(){ return (int)Math.ceil(x);}
    public int getY(){ return (int)Math.ceil(y);}
    
}
