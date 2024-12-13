import java.awt.*;
import java.awt.Graphics;

class PowerBar {
    //power level and is it on
    private static int power = 0;
    private static boolean status = false;

    private static int step = 3;

    //draw the white background for the power bar
    public static void drawBackground(Graphics g){
        if(status){
            g.setColor(Color.WHITE);
            g.fillRect(250, 20, 500, 50);
        }
    }

    public static boolean getStatus(){
        return status;
    }

    public static void setPower(int inPower){
        power = inPower;
    }

    //draw the red power bar going left and right for power
    public static void drawBar(Graphics g){
        if(status){
            if(power < 0){ // prevent bugs
                power = 0;
            }
            g.setColor(Color.RED);
            g.fillRect(250+5, 20+5, power, 50-10);
        }
    }

    public static int getPower(){
        return power;
    }

    public static void updatePower(){
        if(status){
            if(power < 1 || power > 490){ //reverse direction at ends
                step = step * (-1);
            }

            power = power + step;
        }
    }

    public static void changeStatus(boolean inStatus){
        status = inStatus;

        //reset power after power bar is shut off and hit ball
        if(!inStatus){
            Ball.hit();
            power = 0;
        }
    }
}