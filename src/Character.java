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

public class Character {
    private static ImageIcon TrumpImage = new ImageIcon(Images.TRUMP);
    public static void draw(Graphics g, JPanel panel){
        if(!Ball.getStatus()){
            g.drawImage(TrumpImage.getImage(), Ball.getX() - 100, Ball.getY() - 150, 175, 200, panel);
        }
    }
}