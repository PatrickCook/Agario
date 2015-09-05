/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author patrickcook
 */
public class Food  extends BackgroundObject{
    private int radius;

    private Random rand;
    public Food (){
        super();
        rand = new Random();
        x = (int)(Math.random() * 2499)+1;
        y = (int)(Math.random() * 2499)+1;
        radius = 10;
    }
    public Color getRandomColor(){
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            return new Color(r,g,b);
    }
  
    public int getRadius(){
        return radius;
    }
}
