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

class PowerBar {
    private static int power = 0;
    private static boolean status = false;

    private static int step = 3;

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