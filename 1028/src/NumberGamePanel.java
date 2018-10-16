import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NumberGamePanel extends JPanel {

    private JButton[][] board;
    private ArrayList<Cell> list;
    private Cell[][] iBoard;
    private JButton quitButton, left, right, down, up;

    private NumberGame game;

    public NumberGamePanel() {
        game = new NumberGame();
        game.resizeBoard(4,4,1024);
        game.reset();

        JPanel display = new JPanel();
        GridLayout gameBoard = new GridLayout(4,4);
        display.setLayout(gameBoard);

        board = new JButton[4][4];
        for(int r = 0; r < 4; r++){
            for(int c = 0; c < 4; c++){
                display.add(board[r][c] = new JButton(""));
            }
        }
        add(display);

        displayBoard();
        createButtons();
    }

    private void displayBoard(){
        for(int r = 0; r < 4; r++){
            for(int c = 0; c < 4; c++){
                board[r][c].setText("");
            }
        }

        iBoard = game.getBoard();
        list = game.getNonEmptyTiles();
        for(int i = 0; i < list.size(); i ++){
            int r = list.get(i).row;
            int c = list.get(i).column;
            board[r][c].setText(list.get(i).value + "");
        }

    }

    private class ButtonListener implements ActionListener{
        public void actionPerformed (ActionEvent event){
            if (event.getSource().equals(quitButton)){
                System.exit(0);
            }
            else if (event.getSource().equals(up)){
                game.slide(SlideDirection.UP);
                displayBoard();
            }
            else if (event.getSource() == right){
                game.slide(SlideDirection.RIGHT);
                System.out.println("right");
                displayBoard();
            }
            else if(event.getSource() == down) {
                game.slide(SlideDirection.DOWN);
                displayBoard();
            }
            else if(event.getSource() == left) {
                game.slide(SlideDirection.LEFT);
                displayBoard();
            }
//            else if(event.getSource() == undo) {
//                try {
//                    game.undo();
//                    displayBoard();
//                } catch (IllegalStateException exp) {
//                    System.err.println("Can't undo that far");
//                }
//            }
            if (game.getStatus() != GameStatus.IN_PROGRESS){

            }


        /* Almost done.... */
            switch (game.getStatus()) {
                case IN_PROGRESS:
                    System.out.println("");
                    break;
                case USER_WON:
                    System.out.println("Congratulations!");
                    break;
                case USER_LOST:
                    System.out.println("Sorry!");
                    break;
            }
        }
    }

    private void createButtons(){
        ButtonListener listener = new ButtonListener();

        JPanel buttonPanel = new JPanel();
        BorderLayout buttons = new BorderLayout();
        buttonPanel.setLayout(buttons);

        quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(70,28));
        left = new JButton("Left");
        right = new JButton("Right");
        up = new JButton("Up");
        down = new JButton("Down");

        quitButton.addActionListener(listener);
        left.addActionListener(listener);
        right.addActionListener(listener);
        up.addActionListener(listener);
        down.addActionListener(listener);

        buttonPanel.add(up, BorderLayout.PAGE_START);
        buttonPanel.add(left, BorderLayout.LINE_START);
        buttonPanel.add(quitButton, BorderLayout.CENTER);
        buttonPanel.add(right, BorderLayout.LINE_END);
        buttonPanel.add(down, BorderLayout.PAGE_END);
        add(buttonPanel);
    }
}