package F17project2.untitled.src;
import javax.swing.*;
import java.awt.*;

/*******************************************************************
 * @author Ethan Carter
 *
 * Thanks to stack overflow for centering the frame. That's about
 * all the help I actually got.
 ******************************************************************/
public class Surround extends JFrame{
    private static JMenuItem quitItem, newGameItem;

    public static void main(String[] args) {

        quitItem = new JMenuItem("Quit");
        newGameItem = new JMenuItem("New Game");

        JFrame game = new JFrame("Surround Game");
        SurroundPanel panel = new SurroundPanel(quitItem, newGameItem);
        game.getContentPane().add(panel);
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);
        game.setSize(new Dimension(700, 720));
        game.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        game.setLocation(dim.width/2-game.getSize().width/2,
                dim.height/2-game.getSize().height/2);

    }
}
