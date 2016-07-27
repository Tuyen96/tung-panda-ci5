import Models.Bullet;
import Models.Plane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * Created by tungb on 7/24/2016.
 */
public class GameWindow extends Frame implements Runnable {
    Image background;
    //enemy
    Image enemyImage;
    Vector<Plane> enemyVector;

    //player
    Image plane1Image;
    Plane plane1;
    Image plane2Image;
    Plane plane2;

    //bullet
    Vector<Bullet> bulletVector;
    Image bulletImage;

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

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        this.setCursor(getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), null));
        plane1 = new Plane(240, 430);
        plane2 = new Plane(320, 430);
        bulletVector = new Vector<>();
        enemyVector = new Vector<>();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        plane1.moveTo(plane1.x - 10, plane1.y);
                        break;
                    case KeyEvent.VK_RIGHT:
                        plane1.moveTo(plane1.x + 10, plane1.y);
                        break;
                    case KeyEvent.VK_UP:
                        plane1.moveTo(plane1.x, plane1.y - 10);
                        break;
                    case KeyEvent.VK_DOWN:
                        plane1.moveTo(plane1.x, plane1.y + 10);
                        break;
                    case KeyEvent.VK_SPACE:
                        bulletVector.add(new Bullet(plane1.x + 35, 0, plane1.y, -10));
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                plane2.moveTo(e.getX() - 35, e.getY() - 31);
//                plane2.x = e.getX() - 35;
//                plane2.y = e.getY() - 31;
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                bulletVector.add(new Bullet(plane2.x + 35, 0, plane2.y, -10));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                bulletVector.add(new Bullet(plane2.x + 35, 0, plane2.y, -10));
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        try {
            background = ImageIO.read(new File("resources/background.png"));
            plane1Image = ImageIO.read(new File("resources/plane3.png"));
            plane2Image = ImageIO.read(new File("resources/plane4.png"));
            bulletImage = ImageIO.read(new File("resources/bullet.png"));
            enemyImage = ImageIO.read(new File("resources/enemy_plane_yellow_3.png"));
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
        bufferImageGraphics.drawImage(plane1Image, plane1.x, plane1.y, null);
        bufferImageGraphics.drawImage(plane2Image, plane2.x, plane2.y, null);
        for (int i = 0; i < bulletVector.size(); i++) {
            Bullet bullet = bulletVector.get(i);
            bufferImageGraphics.drawImage(bulletImage, bullet.x, bullet.y, null);
        }
        for (int i = 0; i < enemyVector.size(); i++) {
            bufferImageGraphics.drawImage(enemyImage, enemyVector.get(i).x, enemyVector.get(i).y, null);
        }
        g.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public void run() {
        Random rand = new Random();
        int t = 0;
        while (true) {
            try {
                thread.sleep(40);
                //add enemy
                t++;
                if(t % 8 == 0) {
                    enemyVector.add(new Plane(rand.nextInt(500) + 50, 0, 40, 6));
                    t = 0;
                }
                //move and check collision enemy
                Iterator<Plane> enemyIterator = enemyVector.iterator();
                while (enemyIterator.hasNext()){
                    Plane enemy = enemyIterator.next();
                    enemy.move();
                    if(enemy.y > 530) {
                        enemyIterator.remove();
                        break;
                    }
                    Rectangle enemyRectangle = new Rectangle(enemy.x, enemy.y, enemyImage.getWidth(null), enemyImage.getHeight(null));

                    Iterator<Bullet> bulletIterator = bulletVector.iterator();
                    while (bulletIterator.hasNext()) {
                        Bullet bullet = bulletIterator.next();
                        Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y, bulletImage.getWidth(null), bulletImage.getHeight(null));
                        if (enemyRectangle.intersects(bulletRectangle)) {
                            enemyIterator.remove();
                            bulletIterator.remove();
                            break;
                        }
                    }

                }

                Iterator<Bullet> bulletIterator = bulletVector.iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    bullet.move();
                    if (bullet.y < -20)
                        bulletIterator.remove();
                }
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
