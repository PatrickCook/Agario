/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author patrickcook
 */
public class CollisionHandler {

    HashSet removeFood = new HashSet();

    /**
     *
     * @param foodArray
     * @param p
     * @param centerX
     * @param centerY
     * @param bg background
     * @return ArrayList<Food> that has eaten food removed
     * @throws InterruptedException
     */
    public ArrayList<Food> calcCollisions(ArrayList<Food> foodArray, Player p, int centerX, int centerY, Background bg) {

        for (final Food f : foodArray) {
            //creates new thread to check collision
            int x1 = centerX; //player x
            int y1 = centerY; //player y   
            int x2 = f.getX(); //food x
            int y2 = f.getY(); //food y
            int r1 = p.getRadius(); //player radius
            int r2 = f.getRadius(); //food radius
            //calculates distance between two objects
            int distance = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
            if (distance <= (r1 + r2) * (r1 + r2)) {
                removeFood.add(f);
                p.increaseRadius();
            }

        }
        foodArray.removeAll(removeFood);

        for (int x = 0; x < removeFood.size(); x++) {
            foodArray.add(new Food());
        }

        removeFood.clear();

        return foodArray;

    }
}
