import javax.swing.*;
import java.awt.*;

/**
 * Created by SirBlooby on 9/28/2016.
 */
public class RunGame {
    public static void main(String[] args){

        JFrame frame = new JFrame("Guessing Game 2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GuessingGameFrame game = new GuessingGameFrame();


        game.setPreferredSize(new Dimension(400,400));
        game.setSize(400,400);
        frame.pack();
        game.setVisible(true);
    }
}
