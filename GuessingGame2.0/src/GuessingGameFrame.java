import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.EventQueue;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Created by SirBlooby on 9/28/2016.
 */
public class GuessingGameFrame extends JFrame{

    private JRadioButtonMenuItem difficultyItemEasy,
            difficultyItemMedium, difficultyItemHard;

    private ButtonGroup difficultyGroup;

    /**
     * create game panel
     */
    public GuessingGameFrame(){
        initializeUI();
    }
    //------------------------------------------------------------------

    /**
     * initialize all game properties
     */
    private void initializeUI(){
        //lets the program know there will be a menu bar, and uses
        //our menu method
        createMenu();
        //sets the title. in our case  its Guessing Game 2.0
        setTitle("Guessing Game 2.0");
        GuessingGamePanel panel = new GuessingGamePanel();
        add(panel);
        //no clue what this does
        setLocationRelativeTo(null);

        //standard closing procedure
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    //------------------------------------------------------------------

    /**
     * Here it is important to note what needs what (hierarchy of stuff)
     *
     * You create a menu bar first, then add a menu option, and then
     * add an option item.
     */
    private void createMenu(){
        //creates the menu bar
        JMenuBar menu = new JMenuBar();


        //creates an option on the menu bar and gives it a name
        JMenu game = new JMenu("Game");
        JMenu about = new JMenu("About");
        JMenu difficulty = new JMenu("Difficulty");


        //creates items for the game option
        JMenuItem gameItemExit = new JMenuItem("Exit");
        JMenuItem gameItemReset = new JMenuItem("Reset");


        //different difficulties of the game
        difficultyItemEasy = new JRadioButtonMenuItem("Easy",true);
        difficultyItemMedium = new JRadioButtonMenuItem("Medium",false);
        difficultyItemHard = new JRadioButtonMenuItem("Hard",false);

        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(difficultyItemEasy);
        difficultyGroup.add(difficultyItemMedium);
        difficultyGroup.add(difficultyItemHard);


        //this creates a small panel that tells you the purpose of
        //the item
        gameItemExit.setToolTipText("Exit Game");
        gameItemReset.setToolTipText("Reset Game");


        //listener methods
        ExitListener exit = new ExitListener();
        gameItemExit.addActionListener(exit);

        ResetListener reset = new ResetListener();
        gameItemReset.addActionListener(reset);

        DifficultyListener difficultyL = new DifficultyListener();
        difficultyItemEasy.addActionListener(difficultyL);
        difficultyItemMedium.addActionListener(difficultyL);
        difficultyItemHard.addActionListener(difficultyL);


        //now that all these things have been created, we can add
        //them in reverse order. The order you see here is the order
        //they show up
        //------------
        //game
        //------------
        game.add(gameItemReset);
        game.add(gameItemExit);
        //------------
        //about
        //------------

        //------------
        //difficulty
        //------------
        difficulty.add(difficultyItemEasy);
        difficulty.add(difficultyItemMedium);
        difficulty.add(difficultyItemHard);
        //------------
        //menu
        //------------
        menu.add(game);
        menu.add(difficulty);
        menu.add(about);


        //and this is the final step: setting the menu bar to what we
        //just made
        setJMenuBar(menu);
}


    /**
     * a class specifically for the exit application menu item
     */
    private class ExitListener implements ActionListener{
        public void actionPerformed(ActionEvent event1){
            System.exit(0);
        }
    }

    /**
     * a class specifically for the resetting of the board
     */
    private class ResetListener implements ActionListener{
        public void actionPerformed(ActionEvent event1){
            //resets the game
        }
    }

    /**
     * a class that sets the difficulty of the game
     */
    private class DifficultyListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            //check source, and change difficulty appropriately

            //if easy
            if(event.getSource() == difficultyItemEasy) {
                //set game mode to easy, un-select other difficulties
                //reset game
            }
            //if medium,
            //set game mode to medium, un-select other difficulties
            //reset game

            //if hard
            //set game mode to hard, un-select other difficulties
            //reset game
        }
    }
}
