import javax.swing.*;
import java.awt.Graphics;
import javax.swing.JPanel;

//draw golfer behind ball when ball is not flying
public class Character {
    private static ImageIcon TrumpImage = new ImageIcon(Images.TRUMP);
    public static void draw(Graphics g, JPanel panel){
        if(!Ball.getStatus()){
            g.drawImage(TrumpImage.getImage(), Ball.getX() - 100, Ball.getY() - 150, 175, 200, panel);
        }
    }
}