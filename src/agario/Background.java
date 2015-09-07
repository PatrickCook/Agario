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
 * @author patrickcook
 */
public class Background extends GameProp{
    final int width = 5000;
    final int height = 5000;;
    private BufferedImage playerImg;

    public Background() {
        x = 0;
        y = 0;
        speedRate = 5*Math.pow(Math.E, 0);
        try {
            playerImg = ImageIO.read(Background.class.getResourceAsStream("/res/grid.jpg"));
        } catch (IOException e) {
        }
    }
    public BufferedImage getImg() { return playerImg; }

}

