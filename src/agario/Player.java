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
public class Player extends GameProp {

    private String name;

    public Player() {
        this.x = 400;
        this.y = 320;
        name = "";
    }
    public void update(Background bg){
        x = 400 - bg.getX();
        y = 320 - bg.getY();
    }
    public void setName(String str) {
        name = str;
    }

    public String getName() {
        return name;
    }

}
