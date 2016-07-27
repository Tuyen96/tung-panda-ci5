import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by tungb on 7/24/2016.
 */
public class GameWindow extends Frame implements Runnable {
    Image background;
    //enemy
    Image enemy1;
    Image enemy2;
    Image enemy3;
    //player0
    Image player;
    int planeX = 240;
    int planeY = 430;
    //player1
    Image player1;
    int planeX1 = 320;
    int planeY1 = 430;
    //thread
    Thread thread;
    //graphic
    BufferedImage bufferedImage;
    Graphics bufferImageGraphics;

    GameWindow() {
        this.setVisible(true);
        this.setSize(600, 500);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("Window Open");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Window Closing");
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Window Closed");

            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("Window Iconified");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("Window Activate");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("Window deactivate");

            }
        });
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        planeX -= 10;
                        break;
                    case KeyEvent.VK_RIGHT:
                        planeX += 10;
                        break;
                    case KeyEvent.VK_UP:
                        planeY -= 10;
                        break;
                    case KeyEvent.VK_DOWN:
                        planeY += 10;
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("release");
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }
            @Override
            public void mouseMoved(MouseEvent e) {
                planeX1 = e.getX() - 35;
                planeY1 = e.getY() - 31;
            }
        });
        try {
            background = ImageIO.read(new File("resources/background.png"));
            player = ImageIO.read(new File("resources/plane3.png"));
            player1 = ImageIO.read(new File("resources/plane4.png"));
            enemy1 = ImageIO.read(new File("resources/enemy_plane_white_1.png"));
            enemy2 = ImageIO.read(new File("resources/enemy_plane_white_2.png"));
            enemy3 = ImageIO.read(new File("resources/enemy_plane_white_3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLocation(0, 0);
        this.bufferedImage = new BufferedImage(600, 500, BufferedImage.TYPE_INT_ARGB);
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void update(Graphics g) {
        this.bufferImageGraphics = bufferedImage.getGraphics();
        bufferImageGraphics.drawImage(background, 0, 0, null);
        bufferImageGraphics.drawImage(player, planeX, planeY, null);
        bufferImageGraphics.drawImage(player1, planeX1, planeY1, null);
        bufferImageGraphics.drawImage(enemy1, 60, 40, null);
        bufferImageGraphics.drawImage(enemy2, 260, 40, null);
        bufferImageGraphics.drawImage(enemy3, 460, 40, null);
        g.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public void run() {
        while (true) {
            try {
                thread.sleep(40);
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
