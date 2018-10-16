package F17project2.untitled.src;
//import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/*******************************************************************
 * @author Ethan Carter
 *
 * Just the panel that instantiates the game. Fun stuff.
 ******************************************************************/
public class SurroundPanel extends JPanel{
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem quitItem;
    private JMenuItem newGameItem;
    private JPanel centerPanel, southPanel, newGamePanel;
    private JButton startNewGame;
    private JLabel choosePlayers, chooseSize;
    private ButtonListener listener;
    private MenuListener menuListener;
    private NewGameListener newGameListener;
    private JFrame newGameFrame;

    private JComboBox selectPlayers, selectSize;

    private Cell[][] gameBoard;
    private JButton[][] board;
    private SurroundGame game;
    private int size = 10;

    //use this to store how many players there are
    private int[] players;
    //use this to store which player we are on
    private int player;


    public SurroundPanel(JMenuItem quitItem, JMenuItem newGameItem){
        //this is just for testing
        players = new int[1];
        player = 0;

        //constructor things
        this.quitItem = quitItem;
        this.newGameItem = newGameItem;

        //panels
        centerPanel = new JPanel();
        southPanel = new JPanel();
        newGamePanel = new JPanel();

        setLayout(new BorderLayout());
        centerPanel.setLayout(new GridLayout(size, size));
        newGamePanel.setLayout(new GridLayout(3,2));

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        //JButton
        startNewGame = new JButton("Start New Game");

        //JLabel
        choosePlayers = new JLabel("Choose Player Amount: ");
        chooseSize = new JLabel("Choose Board Size: ");

        //listener
        listener = new ButtonListener();
        menuListener = new MenuListener();
        newGameListener = new NewGameListener();

        //combo boxes
        selectPlayers = new JComboBox();
        for(int i = 2; i < 100; i++){
            selectPlayers.addItem(i);
        }
        selectSize = new JComboBox();
        for(int i = 4; i < 51; i++){
            selectSize.addItem(i);
        }

        //JMenu
        menuBar = new JMenuBar();
        file = new JMenu("File");
        menuBar.add(file);
        quitItem.addActionListener(menuListener);
        file.add(quitItem);
        newGameItem.addActionListener(menuListener);
        file.add(newGameItem);
        add(menuBar, BorderLayout.NORTH);

        //New Game Frame
        newGameFrame = new JFrame("Create New Game");
        newGameFrame.getContentPane().add(newGamePanel);
        newGameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        newGameFrame.setSize(new Dimension(300, 200));
        newGameFrame.setVisible(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        newGameFrame.setLocation(dim.width/2-newGameFrame
                        .getSize().width/2,
                dim.height/2-newGameFrame.getSize().height/2);

        newGameFrame.add(newGamePanel);
        newGamePanel.add(choosePlayers);
        selectPlayers.addActionListener(newGameListener);
        newGamePanel.add(selectPlayers);
        newGamePanel.add(chooseSize);
        selectSize.addActionListener(newGameListener);
        newGamePanel.add(selectSize);
        startNewGame.addActionListener(newGameListener);
        newGamePanel.add(startNewGame);

        //board
        board = new JButton[size][size];
        game = new SurroundGame(size);
        gameBoard = game.getBoard();

        //create the board
        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){
                board[r][c] = new JButton(" ");
                board[r][c].addActionListener(listener);
                centerPanel.add(board[r][c]);
            }
        }
    }
    //here is where we call on game a lot to do stuff for us. yay.
    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //check for other inputs like JMenuItems

            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    //is this the right one and can i change it?
                    if(e.getSource() == board[r][c]
                            && game.select(r,c)) {
                        board[r][c].setText("" + (player + 1));
                        game.placeCell(r,c, (player + 1));
                    }
                }
            }
            //check if game is won
            if(game.isWinner().size() == 0){ //game still in session
                nextPlayer();
                System.out.println("Player " + (player + 1) + "'s turn");
            }
            else if(game.isWinner() == null){ //board full, no winner
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"Nobody won. " +
                        "Pres OK to start new game");
                //make the board unclickable
                for (int r = 0; r < size; r++) {
                    for (int c = 0; c < size; c++) {
                        board[r][c].invalidate();
                    }
                }
                //new game
                newGame();
            }
            else{ //winner

                /**EDIT (10-14-2018): This is technically a breach of Player View Model since this next segment of code
                should be in the game engine. In fact the whole progression of keeping track of the current player is a
                 breach of Player View Model. However, making room for it is really quite messy at this stage, so it's
                easier to make that change in the panel.

                 Don't do this at home, we're professionals. */

                if(checkWinners(player, game.isWinner())) {
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f,
                            (player + 1) +" Has won the game!" +
                                    " Press OK to make a new game!");


                    //make the board un-clickable
                    for (int r = 0; r < size; r++) {
                        for (int c = 0; c < size; c++) {
                            board[r][c].invalidate();
                        }
                    }
                    //new game
                    newGame();
                }
            }
            centerPanel.revalidate();
            repaint();
        }
    }

    //menu listener
    private class MenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == quitItem){
                //exit
                System.exit(0);
            }
            else if(e.getSource() == newGameItem){
                newGame();

                centerPanel.revalidate();
                repaint();
            }
        }
    }

    private class NewGameListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startNewGame){
                clearBoard();
                int size = selectSize.getSelectedIndex() + 4;
                int players = selectPlayers.getSelectedIndex() + 1;
                setBoard(size, players);
                newGameFrame.setVisible(false);

                centerPanel.revalidate();
                repaint();
            }
        }
    }

    //helpers below
    private void nextPlayer(){
        if(player == players.length){
            player = 0;
        }
        else{
            player++;
        }
    }
    private void clearBoard(){
        centerPanel.removeAll();
        centerPanel.setLayout(new GridLayout(size, size));
        board = new JButton[size][size];
    }
    private void newGame(){
        newGameFrame.setVisible(true);
    }

    //should I make two of these? One for keeping the same variables
    //and one for setting the variables to something new?
    private void setBoard(int size, int players){
        this.size = size;
        this.players = new int[players];
        this.player = 0;

        //new game
        game = new SurroundGame(size);
        gameBoard = game.getBoard();
        board = new JButton[size][size];

        //create the board
        centerPanel.setLayout(new GridLayout(size, size));
        for(int r = 0; r < this.size; r++){
            for(int c = 0; c < this.size; c++){
                board[r][c] = new JButton(" ");
                board[r][c].addActionListener(listener);
                centerPanel.add(board[r][c]);
            }
        }
    }

    private boolean checkWinners(int player, ArrayList<Integer> list){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) == player);
            return true;
        }
        return false;
    }

    //just where I did some basic testing.
    public static void main(String[] args) {
        int size = 3;
        SurroundGame game = new SurroundGame(size);
//        Cell[][] board = game.getBoard();

        game.placeCell(2,2,1);
        game.printLib(2, 2);

        game.placeCell(1,2,2);
        game.printLib(0,1);
//        game.printLib(0,0);

        game.placeCell(2,1,2);
        game.printLib(1,0);


        game.printLib(0,1);

        game.printBoard();
        System.out.println(game.isWinner());
    }

}
