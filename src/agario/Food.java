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
public class Food extends Circle {

    private Color color;
    private Random rand;

    public Food() {
        color = super.getColor();
        x = (int) (Math.random() * 2499) + 1;
        y = (int) (Math.random() * 2499) + 1;
        radius = 10;
    }

}
