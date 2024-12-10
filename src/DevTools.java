import java.awt.*;

public class DevTools {
    private static boolean status = false;

    public static void updateStatus(){
        status = !status;
    }

    public static void draw(Graphics g){
        if(!status){
            return;
        }

        String[] info = {
                "X: " + Ball.getX(),
                "Y: " + Ball.getY(),
                "θ: " + Arrow.getAngle(),
                "POWER: " + PowerBar.getPower() + " / " + GameRules.POWER_DAMPENER + " = " + (PowerBar.getPower() / GameRules.POWER_DAMPENER),
                "BOUNCE: " + Ball.getBounceCount() + " / " + GameRules.BOUNCE_NUMBER,
                "WALL: " + Ball.getWallCount() + " / " + GameRules.BOUNCE_NUMBER,
                "POWER BAR: " + (PowerBar.getStatus() ? "On" : "Off"),
        };

        //System.out.println(info.length);

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        for(int i = 0; i < info.length; i++){
            g.drawString(info[i], 10, 40 + (20 * i));
        }

    }
}
