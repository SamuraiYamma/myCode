import javax.swing.*;
import java.awt.*;

public class TenTwentyFour {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        NumberGamePanel panel = new NumberGamePanel();
        frame.setPreferredSize(new Dimension(350, 450));
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
//        frame.setResizable(false);
    }
}
