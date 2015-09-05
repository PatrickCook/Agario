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
    public ArrayList<Food> calcCollisions(ArrayList<Food> foodArray, Player p, int centerX, int centerY,Background bg) {

        for (Food f : foodArray) {
            int x = (int)f.x;
            int y = (int)f.y;
            if (x <(400 + p.getRadius()) && x > 400 && y < (320 + p.getRadius()) && y >320) { 
                removeFood.add(f);
                p.increaseRadius(); 
                bg.decreaseSpeed();
            }
        }
        foodArray.removeAll(removeFood);
        
        for (int x = 0; x < removeFood.size(); x++)
            foodArray.add(new Food());
        
        removeFood.clear();
        
        return foodArray;
    }
}
