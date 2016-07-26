import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by tungb on 7/27/2016.
 */
public class Plane {
    int x;
    int y;
    Image image;
    Plane(){
        x = 220;
        y = 430;
        try {
            image = ImageIO.read(new File("resources/plane4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Plane(int x, int y, String imageName){
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(new File("resources/" + imageName +".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void move(int xDistance, int yDistance){
        x += xDistance;
        y += yDistance;
    }
}
