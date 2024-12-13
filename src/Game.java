import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Game {
    //set up GUI for game
    public static void start(JFrame frame){
        frame.getContentPane().removeAll(); //remove starting screen
        frame.setSize(1000, 700); //change size
        frame.setLocationRelativeTo(null); //re center

        //move aiming arrow towards mouse
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            //mouse moved
            public void mouseMoved(MouseEvent event) {
                int mouseX = event.getX();
                int mouseY = event.getY();

                //System.out.println("(" + mouseX + ", " + mouseY + ")");

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

        //left click triggers power bar
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

            //left click release triggers hit
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

        //turn on dev tools with key stroke
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

        //paint component for animating game
        JPanel drawPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);

                // Draw background image
                g.drawImage(holeBackground.getImage(), 0, 0, getWidth(), getHeight(), this);

                //re draw everything to update where they have moved
                Character.draw(g, this);
                Arrow.draw(g, this);
                PowerBar.drawBackground(g);
                PowerBar.drawBar(g);
                PowerBar.updatePower();
                Ball.draw(g, this);
                DevTools.draw(g);
                Director.draw(g, this);

                g.setFont(new Font("Arial", Font.BOLD, 18));
                g.setColor(Color.BLACK);
                g.drawString("Score: " + ScoreCard.getScore(), 10, 20);
            }
        };

        drawPanel.setDoubleBuffered(true);

        drawPanel.setPreferredSize(frame.getPreferredSize());
        frame.add(drawPanel, BorderLayout.CENTER);

        //create thread and loop animation
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

        //instructions for how to play the game
        Tools.alert("Aim in the direction you want to hit the ball and hold left click to charge up your shot!");
    }
}
