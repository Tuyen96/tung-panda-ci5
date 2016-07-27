package Models;

/**
 * Created by tungb on 7/27/2016.
 */
public class Bullet {
    public int x;
    public int dx;
    public int y;
    public int dy;
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Bullet(int x, int dx, int y, int dy) {
        this.x = x;
        this.dx = dx;
        this.y = y;
        this.dy = dy;
    }

    public void move(){
        x += dx;
        y += dy;
    }
}
