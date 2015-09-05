/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

/**
 *
 * @author patrickcook
 */
public class Player extends Shape{
    //default size at beginning
    private final int defaultRadius = 20;
    //radius that grows as game continues
    private int radius = 20;
    
    public void move(){}
    
    //Return radius if it is bigger than the defaultRadius
    public int getRadius(){
        if (radius > defaultRadius)
            return radius;
        else 
            return defaultRadius;
    }
    public void increaseRadius(){
        radius++;
    }
    
}
