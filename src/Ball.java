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
import java.util.concurrent.atomic.AtomicReference;

public class Ball {
    private static int x = 60;
    private static int y = 510;

    private static int startX = x;
    private static int startY = y;

    private static boolean status = false;

    private static Timer ballFlight;

    //pre load
    private static ImageIcon ballImage = new ImageIcon(Images.BALL);
    public static void draw(Graphics g, JPanel panel){
        if (Double.isNaN(x) || Double.isNaN(y)) {
            System.err.println("Invalid ball position: (" + x + ", " + y + ")");
            return;
        }

        g.drawImage(ballImage.getImage(), x , y, 25, 25, panel);
    }

    public static int getX(){
        return x;
    }

    public static int getY(){
        return y;
    }

    public static boolean getStatus(){
        return status;
    }

    public static void hit(){
        if(status){ // dont hit ball if ball in air
            return;
        }

        System.out.println(PowerBar.getPower());

        double power = (double)PowerBar.getPower() / 7; //divide by 7 to make it less powerful
        int angle = Arrow.getAngle();

        status = true;

        double gravity = 9.8; // acceleration due to gravity (m/s^2)
        final double[] time = {0.0}; // start time
        double timeStep = 0.02; // time increment for each frame

        double angleRadians = Math.toRadians(angle);

        double initialVelocityX = power * Math.cos(angleRadians);
        double initialVelocityY = power * Math.sin(angleRadians);

        ballFlight = new Timer(10, event -> {
            double posX = startX + initialVelocityX * time[0];
            double posY = initialVelocityY * time[0] - 0.5 * gravity * time[0] * time[0];

            x = (int)posX;
            y = (int)(startY - posY);

            time[0] += timeStep;

            //System.out.println("(" + x + ", " + y + ")");

            if(y > 510){
                y = 510;
                ((Timer) event.getSource()).stop();
                status = false;
                startX = x;
                startY = y;
            }
        });
        ballFlight.start();
    }
}