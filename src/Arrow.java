import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Arrow {
    //angle and image of arrow
    private static int angle = 0;
    private static BufferedImage arrowImage;

    //read in image for arrow
    static {
        try {
            arrowImage = ImageIO.read(new File(Images.ARROW)); // Replace with your arrow image path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void draw(Graphics g, JPanel panel){
        if(!Ball.getStatus()){ //dont draw if ball is flying
            //set position and size
            int width = 100;
            int height = 50;
            int xPos = Ball.getX() + (25/2);
            int yPos = Ball.getY() + (25/2);

            //rotate arrow towards ball
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform originalTransform = g2d.getTransform();
            g2d.translate(xPos, yPos);
            g2d.rotate(Math.toRadians(angle));
            g2d.drawImage(arrowImage, 0, -height / 2, width, height, panel);
            g2d.setTransform(originalTransform);
        }
    }

    //set rotation of arrow
    public static void setAngle(int degree){
        if(Ball.getStatus()){
            return;
        }

        angle = degree % 360;
    }

    //set rotation of arrow even if ball is moving
    public static void setAngleOverride(int degree){ // override
        angle = degree % 360;
    }

    //get the angle of the arrow
    public static int getAngle(){
        return (-1)*angle;
    }

    //get angle of arrow without it being modified for the ball flight
    public static int getAngleRaw(){
        return angle;
    }
}