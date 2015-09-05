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
public class BackgroundObject {
    public double x, y, translateX, translateY, speedRate;
    
    public BackgroundObject(){
        x = 0;
        y = 0;
        translateX = 0;
        translateY = 0;
        speedRate = 2;
    }
    //this is called when background still needs to move but the mouse isnt
    public void translate() {
        //prevents going out of bounds   
        x += translateX / speedRate;
        y += translateY / speedRate;       
    }
    
    //sets default direction so background still moves if mouse doesnt
    public void setTranslate(double transX, double transY) {

        translateX = transX;
        translateY = transY;
    }
     public int getX(){
        return (int)x;
    }
    public int getY(){
        return (int)y;
    }
    public void decreaseSpeed(){
        speedRate+= 0.1;
    }
}
