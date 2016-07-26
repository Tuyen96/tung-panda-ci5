import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by tungb on 7/26/2016.
 */
public class GameWindow extends Frame implements Runnable {
    Plane player;
    ArrayList<Plane> listEnemy;

    Image background;
    Thread thread;
    boolean playing;

    BufferedImage bufferedImage;
    Graphics graphics;

    GameWindow() {
        this.setTitle("Plane");
        this.setVisible(true);
        this.setSize(500, 800);
        this.setLocation((getToolkit().getDefaultToolkit().getScreenSize().width - 500) / 2,
                (getToolkit().getDefaultToolkit().getScreenSize().height - 800) / 2);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {
                playing = true;
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                playing = false;
            }
        });
        try {
            background = ImageIO.read(new File("resources/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playing = true;
        //set player and enemy
        player = new Plane();
        this.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                player.setX(e.getX() - 35);
                player.setY(e.getY() - 31);
            }
        });
        listEnemy = new ArrayList<>();
        updateListEnemy();

        //set image
        bufferedImage = new BufferedImage(500, 800, BufferedImage.TYPE_INT_ARGB);
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void update(Graphics g) {
        this.graphics = bufferedImage.getGraphics();
        graphics.drawImage(background, 0, 0, null);
        graphics.drawImage(player.getImage(), player.getX(), player.getY(), null);
        for (Plane plane : listEnemy)
            graphics.drawImage(plane.getImage(), plane.getX(), plane.getY(), null);
        g.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public void run() {
        int t = 0;
        while (true) {
            try {
                thread.sleep(27);
                if (playing) {
                    t++;
                    for (Plane plane : listEnemy)
                        plane.move(0, 5);
                    if(t == 25) {
                        updateListEnemy();
                        t = 0;
                    }
                    repaint();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateListEnemy(){
        System.out.println(listEnemy.size());
        Random rand = new Random();
        listEnemy.add(new Plane(rand.nextInt(150)+10, 40, "enemy_plane_white_3"));
        listEnemy.add(new Plane(rand.nextInt(150)+150, 40, "enemy_plane_yellow_3"));
        listEnemy.add(new Plane(rand.nextInt(300)+150, 40, "enemy_plane_white_3"));
    }
}
