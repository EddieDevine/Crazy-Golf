import javax.swing.*;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Ball {
    //set position
    private static int x = 60;
    private static int y = 510;

    private static int startX = x;
    private static int startY = y;

    //is ball flying
    private static boolean status = false;

    //interval for ball flight
    private static Timer ballFlight;

    //numbers controlling ball behavior
    private static final int bounceCount[] = {0};
    private static final int wallCount[] = {0};

    //pre load ball image
    private static ImageIcon ballImage = new ImageIcon(Images.BALL);
    public static void draw(Graphics g, JPanel panel){
        //draw ball
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

    //hit the ball into air
    public static void hit(){
        //no double hit
        if(status){ // dont hit ball if ball in air
            return;
        }

        //only bounce set number of times
        if(bounceCount[0] == GameRules.BOUNCE_NUMBER){
            ScoreCard.updateScore(1);
        }

        //ball flight variables
        double power = (double)PowerBar.getPower() / GameRules.POWER_DAMPENER; //divide by 7 to make it less powerful
        int angle = Arrow.getAngle();

        status = true;

        //varibles for position calculation
        final double[] time = {0.0}; // start time
        double timeStep[] = {GameRules.MOVEMENT_SPEED}; // time increment for each frame

        double angleRadians = Math.toRadians(angle);

        double initialVelocityX[] = {power * Math.cos(angleRadians)};
        double initialVelocityY[] = {power * Math.sin(angleRadians)};

        final double[] velocityX = {initialVelocityX[0]};
        final double[] velocityY = {initialVelocityY[0]};

        //set interval to move ball
        ballFlight = new Timer(10, event -> {
            //calcuate formula for ball position
            velocityY[0] -= GameRules.GRAVITY * timeStep[0];

            double posX = startX + velocityX[0] * time[0];
            double posY = startY - (initialVelocityY[0] * time[0] - 0.5 * GameRules.GRAVITY * time[0] * time[0]);

            x = (int)posX;
            y = (int)posY;

            //System.out.println("(" + x + ", " + y + ")");

            time[0] += timeStep[0];


            //lift and right collision
            if(x > 1000-40){ // right side collision
                x = 1000-40;
                velocityX[0] *= -.25;
                startX = x; // Reset starting position
                startY = y; // Reset starting position
                //bounceCount[0] = GameRules.BOUNCE_NUMBER; // stop bouncing
                Arrow.setAngleOverride(Arrow.getAngleRaw() - 90);
                wallCount[0]++;
                time[0] = 0; // Reset time to avoid large increments
            }
            else if(x < 0){ //left side collision
                x = 0;
                velocityX[0] *= -.25;
                startX = x; // Reset starting position
                startY = y; // Reset starting position
                //bounceCount[0] = GameRules.BOUNCE_NUMBER; // stop bouncing
                Arrow.setAngleOverride(Arrow.getAngleRaw() + 90);
                wallCount[0]++;
                time[0] = 0; // Reset time to avoid large increments
            }
            else if(y > 520 && x < 460){ //left side of bunker collision
                x = 460;
                velocityX[0] *= -.25;
                startX = x; // Reset starting position
                startY = y; // Reset starting position
                //bounceCount[0] = GameRules.BOUNCE_NUMBER; // stop bouncing
                Arrow.setAngleOverride(Arrow.getAngleRaw() - 90);
                wallCount[0]++;
                time[0] = 0; // Reset time to avoid large increments
            }
            else if(y > 520 && x > 640){ //right side of bunker collision
                System.out.println("hit");
                x = 640;
                velocityX[0] *= -.25;
                startX = x; // Reset starting position
                startY = y; // Reset starting position
                //bounceCount[0] = GameRules.BOUNCE_NUMBER; // stop bouncing
                Arrow.setAngleOverride(Arrow.getAngleRaw() + 90);
                wallCount[0]++;
                time[0] = 0; // Reset time to avoid large increments
            }

            //bottom collisions
            if(y > 510 && (x < 850-25 && x > 830-25)){
                ScoreCard.updateScore(1);
                Tools.alert("Hole Complete good job!");
                x = 60;
                y = 510;
                startX = x;
                startY = y;
                bounceCount[0] = GameRules.BOUNCE_NUMBER;
                ScoreCard.resetScore();
            }
            else if(y > 510 && (x < 460 || x > 640)){ //grass bottom
                y = 510;


                status = false;

                if(bounceCount[0] < GameRules.BOUNCE_NUMBER){
                    if(wallCount[0] > 0){ // reduce bounce if coming off wall
                        PowerBar.setPower((int)velocityY[0] * (-1) * (GameRules.BOUNCE_POWER / 2));
                    }
                    else{ // bounce ball normally
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
            else if(y > 580 && (x > 470 && x < 640)){ //bunker bottem
                y = 580;
                status = false;
                ((Timer) event.getSource()).stop();
                startX = x;
                startY = y;
                ScoreCard.updateScore(1);
            }

        });
        ballFlight.start();

    }

}