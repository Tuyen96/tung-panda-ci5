package Models;

import java.awt.*;

/**
 * Created by tungb on 7/27/2016.
 */
public class Plane {
    public int x;
    public int y;
    public int dx;
    public int dy;

    public Plane(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(){
        x += dx;
        y += dy;
    }
}
