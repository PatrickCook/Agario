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
public class Background extends BackgroundObject{

    private BufferedImage playerImg;
    private double x, y, translateX, translateY, speedRate;

    public Background() {
        
        try {
            playerImg = ImageIO.read(Background.class.getResourceAsStream("/res/grid.jpg"));
        } catch (IOException e) {
        }
    }

    public BufferedImage getImg() {
        return playerImg;
    }

}
