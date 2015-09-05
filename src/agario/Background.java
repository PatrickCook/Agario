/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author patrickcook
 */
public class Background {

    private BufferedImage playerImg;
    private double x,y,translateX, translateY;

    public Background() {
        x = 0;
        y = 0;
        translateX = 0;
        translateY = 0;
        try {
            playerImg = ImageIO.read(Background.class.getResourceAsStream("/res/grid.jpg"));
        } catch (IOException e) {
        }
    }

    public BufferedImage getImg() {
        return playerImg;
    }

    //Moves the background based off mouse movement
    public void setTranslate(double transX, double transY){
        //sets default direction so background still moves if mouse doesnt
        translateX = transX;
        translateY = transY;   
    }
    //this is called when background still needs to move but the mouse isnt
    public void translate(){
        x +=translateX/2;
        y +=translateY/2;     
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }
}
