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

public class Director {
    private static ImageIcon arrowImage = new ImageIcon(Images.UP_ARROW);

    public static void draw(Graphics g, JPanel panel){
        if(Ball.getY() < 0){
            g.drawImage(arrowImage.getImage(), Ball.getX(), 0, 50, 50, panel);
        }
    }
}
