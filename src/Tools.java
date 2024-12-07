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

public class Tools {
    public static void alert(String message){
        JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.INFORMATION_MESSAGE);
    }
}