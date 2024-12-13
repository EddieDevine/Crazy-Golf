import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartingScreen {
    public static void create(){
        //create GUI
        JFrame frame = new JFrame("Crazy Golf"); //create frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set exit opperation
        frame.setSize(700, 700); //size
        frame.setLocationRelativeTo(null); //center of screen

        //set background for starting screen
        ImageIcon imageIcon = new ImageIcon(Images.STARTING_SCREEN);
        JLabel label = new JLabel(imageIcon);
        frame.getContentPane().add(label);
        frame.setVisible(true);

        //react when user is over play button
        MouseMotionAdapter myAdapter = new MouseMotionAdapter() {
            //mouse moved
            public void mouseMoved(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();

                if(x > 280 && x < 420 && y > 250 && y < 380){
                    label.setIcon(new ImageIcon(Images.STARTING_SCREEN_ACTIVE));
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                else{
                    label.setIcon(new ImageIcon(Images.STARTING_SCREEN));
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        };
        frame.addMouseMotionListener(myAdapter);

        //listen for click
        frame.addMouseListener(new MouseAdapter(){
            //mouse clicked
            public void mouseClicked(MouseEvent event){
                int x = event.getX();
                int y = event.getY();

                //play button clicked
                if(x > 280 && x < 420 && y > 250 && y < 380){
                    frame.removeMouseListener(this);
                    frame.removeMouseMotionListener(myAdapter);
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    Game.start(frame);
                }
            }
        });
    }
}
