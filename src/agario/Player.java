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
public class Player extends GameProp{
    private String name;
    private int virtX, virtY;
   public Player (int x, int y){
       super(x,y,30);
       name = "";
   }   
   public void setName(String str){
       name = str;
   }
   public String getName(){
       return name;
   }
   public int getVirtX(Background bg){
       virtX = 400 - bg.getX();
       return virtX;
   }
   public int getVirtY(Background bg){
       virtY = 320 - bg.getY();
       return virtY;
   }
}
