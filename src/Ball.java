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

    private static final int bounceCount[] = {0};
    private static final int wallCount[] = {0};

    //pre load
    private static ImageIcon ballImage = new ImageIcon(Images.BALL);
    public static void draw(Graphics g, JPanel panel){
        if (Double.isNaN(x) || Double.isNaN(y)) {
            System.err.println("Invalid ball position: (" + x + ", " + y + ")");
            return;
        }

        g.drawImage(ballImage.getImage(), x , y, 25, 25, panel);
    }

    public static int getBounceCount(){
        return bounceCount[0];
    }

    public static int getWallCount(){
        return wallCount[0];
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

        if(bounceCount[0] == GameRules.BOUNCE_NUMBER){
            ScoreCard.updateScore(1);
        }

        double power = (double)PowerBar.getPower() / GameRules.POWER_DAMPENER; //divide by 7 to make it less powerful
        int angle = Arrow.getAngle();

        status = true;

        final double[] time = {0.0}; // start time
        double timeStep[] = {GameRules.MOVEMENT_SPEED}; // time increment for each frame

        double angleRadians = Math.toRadians(angle);

        double initialVelocityX[] = {power * Math.cos(angleRadians)};
        double initialVelocityY[] = {power * Math.sin(angleRadians)};

        final double[] velocityX = {initialVelocityX[0]};
        final double[] velocityY = {initialVelocityY[0]};


        ballFlight = new Timer(10, event -> {
            velocityY[0] -= GameRules.GRAVITY * timeStep[0];

            double posX = startX + velocityX[0] * time[0];
            double posY = startY - (initialVelocityY[0] * time[0] - 0.5 * GameRules.GRAVITY * time[0] * time[0]);

            x = (int)posX;
            y = (int)posY;

            //System.out.println("(" + x + ", " + y + ")");

            time[0] += timeStep[0];

            if(x > 1000-40){
                x = 1000-40;
                velocityX[0] *= -.25;
                startX = x; // Reset starting position
                startY = y; // Reset starting position
                //bounceCount[0] = GameRules.BOUNCE_NUMBER; // stop bouncing
                Arrow.setAngleOverride(Arrow.getAngleRaw() - 90);
                wallCount[0]++;
                time[0] = 0; // Reset time to avoid large increments
            }
            else if(x < 0){
                x = 0;
                velocityX[0] *= -.25;
                startX = x; // Reset starting position
                startY = y; // Reset starting position
                //bounceCount[0] = GameRules.BOUNCE_NUMBER; // stop bouncing
                Arrow.setAngleOverride(Arrow.getAngleRaw() + 90);
                wallCount[0]++;
                time[0] = 0; // Reset time to avoid large increments
            }

            if(y > 510){
                y = 510;

                status = false;

                if(bounceCount[0] < GameRules.BOUNCE_NUMBER){
                    if(wallCount[0] > 0){ // reduce bounce if coming off wall
                        System.out.println("bounce-");
                        PowerBar.setPower((int)velocityY[0] * (-1) * (GameRules.BOUNCE_POWER / 2));
                    }
                    else{ // bounce ball normally
                        System.out.println("bounce+");
                        PowerBar.setPower((int)velocityY[0] * (-1) * GameRules.BOUNCE_POWER); // -2 to flip sign and increase bounce 2x
                    }
                    bounceCount[0]++;
                    hit(); // bounce ball
                }
                else{
                    status = false;
                    bounceCount[0] = 0;
                    wallCount[0] = 0;
                }

                ((Timer) event.getSource()).stop();
                startX = x;
                startY = y;
            }
            else if(y < 0){

            }

        });
        ballFlight.start();

    }

}