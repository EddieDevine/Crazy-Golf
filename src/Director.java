import javax.swing.*;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Director {
    //load image
    private static final ImageIcon arrowImage = new ImageIcon(Images.UP_ARROW);

    //draw arrow pointing to ball if ball out of bounds
    public static void draw(Graphics g, JPanel panel){
        if(Ball.getY() < 0){
            g.drawImage(arrowImage.getImage(), Ball.getX(), 0, 50, 50, panel);
        }
    }
}
