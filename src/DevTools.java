import java.awt.*;

// TOOLS FOR DEVOPMENT

public class DevTools {
    //is it showing
    private static boolean status = false;

    public static void updateStatus(){
        status = !status;
    }

    public static void draw(Graphics g){
        if(!status){
            return;
        }

        //info to display
        String[] info = {
                "X: " + Ball.getX(),
                "Y: " + Ball.getY(),
                "θ: " + Arrow.getAngle(),
                "POWER: " + PowerBar.getPower() + " / " + GameRules.POWER_DAMPENER + " = " + (PowerBar.getPower() / GameRules.POWER_DAMPENER),
                "BOUNCE: " + Ball.getBounceCount() + " / " + GameRules.BOUNCE_NUMBER,
                "WALL: " + Ball.getWallCount(),
                "POWER BAR: " + (PowerBar.getStatus() ? "On" : "Off"),
        };

        //display info on GUI
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        for(int i = 0; i < info.length; i++){
            g.drawString(info[i], 10, 40 + (20 * i));
        }

    }
}
