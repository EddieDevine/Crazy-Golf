import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Game {
    public static void start(JFrame frame){
        frame.getContentPane().removeAll(); //remove starting screen
        frame.setSize(1000, 700); //change size
        frame.setLocationRelativeTo(null); //re center

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            //mouse moved
            public void mouseMoved(MouseEvent event) {
                int mouseX = event.getX();
                int mouseY = event.getY();

                int arrowX = Ball.getX() + 25;
                int arrowY = Ball.getY();

                int deltaX = mouseX - arrowX;
                int deltaY = mouseY - arrowY;

                double radians = Math.atan2(deltaY, deltaX);
                int degrees = (int) Math.toDegrees(radians);

                // Rotate the arrow to point at the mouse
                Arrow.setAngle(degrees);
            }
        });

        frame.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent event){
                if(Ball.getStatus()){ // dont take user input while ball is flying
                    return;
                }

                if(!PowerBar.getStatus()) {
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    PowerBar.changeStatus(true);
                }
            }

            public void mouseReleased(MouseEvent event){
                if(Ball.getStatus()){ // dont take user input while ball is flying
                    return;
                }

                if(PowerBar.getStatus()) {
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    PowerBar.changeStatus(false);
                }
            }
        });

        frame.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent event) {
                int keyCode = event.getKeyCode();
                if(keyCode == 192){
                    DevTools.updateStatus();
                }
            }
        });

        //pre loaded
        ImageIcon holeBackground = new ImageIcon(Images.HOLE_BACKGROUND);

        JPanel drawPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);

                // Draw background image
                g.drawImage(holeBackground.getImage(), 0, 0, getWidth(), getHeight(), this);


                Character.draw(g, this);
                Arrow.draw(g, this);
                PowerBar.drawBackground(g);
                PowerBar.drawBar(g);
                PowerBar.updatePower();
                Ball.draw(g, this);
                DevTools.draw(g);

                g.setFont(new Font("Arial", Font.BOLD, 18));
                g.setColor(Color.BLACK);
                g.drawString("Score: " + ScoreCard.getScore(), 10, 20);
            }
        };

        drawPanel.setDoubleBuffered(true);

        drawPanel.setPreferredSize(frame.getPreferredSize());
        frame.add(drawPanel, BorderLayout.CENTER);


        new Thread(() -> {
            while (true) {
                SwingUtilities.invokeLater(drawPanel::repaint); // help prevent freezing?
                drawPanel.repaint();
                try {
                    Thread.sleep(10); // 100 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
