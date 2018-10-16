import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by SirBlooby on 9/30/2016.
 */
public class GuessingGamePanel extends JPanel {

    //instantiate game variables
    private Game easyGame, mediumGame, hardGame, currentGame;
    private JTextField guess;
    private JLabel input, output, result;

    private int toGuess, guesses, theirGuess;

    public GuessingGamePanel() {

        easyGame = new Game(10, 3);
        mediumGame = new Game(100, 5);
        hardGame = new Game(100, 3);

        currentGame = easyGame;

        guess = new JTextField(5);
        input = new JLabel("Enter your guess here: ");
        output = new JLabel("");
        result = new JLabel("");

        add(input);
        add(guess);
        add(output);
        add(result);
    }

    private class GameLogic implements ActionListener{
        public void actionPerformed(ActionEvent event){
            //this is where the game logic goes
        }
    }

}
