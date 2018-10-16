package F17project2.untitled.src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Project2Help extends JPanel{
    private JButton[][] buttons;
    private  JPanel panelUpper;
    private JPanel panelLower;

    private int row = 10, col = 10;

    private  JButton resize;

    private JTextField textRow;
    private JTextField textCol;
    private JLabel lblCount;

    private ButtonListener listener;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab 4 Examples");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Project2Help panel = new Project2Help();
        frame.getContentPane().add(panel);
        frame.setSize(600,400);
        frame.setVisible(true);
    }

    public Project2Help(){
        panelUpper = new JPanel();
        panelLower = new JPanel();

        panelUpper.setLayout(new GridLayout(row,col));
        panelLower.setLayout(new GridLayout(4,1));

        buttons = new JButton[row][col];
        listener = new ButtonListener();


        //creates board
        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++){
                buttons[r][c] = new JButton(r + "," + c);
                buttons[r][c].addActionListener(listener);
                panelUpper.add(buttons[r][c]);
            }
        }

        resize = new JButton("Reset");
        resize.addActionListener(listener);
        textRow = new JTextField("10");
        textCol = new JTextField("10");
        lblCount =  new JLabel("0");

        panelLower.add(resize);
        panelLower.add(textRow);
        panelLower.add(textCol);
        panelLower.add(lblCount);

        setLayout(new BorderLayout());

        add(panelUpper, BorderLayout.NORTH);
        add(panelLower, BorderLayout.SOUTH);
    }
    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(resize == e.getSource()){


                row = Integer.parseInt(textRow.getText());
                col = Integer.parseInt(textCol.getText());

                clearBoard();
                setBoard();

                panelUpper.revalidate();
                repaint();
            }
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if(e.getSource() == buttons[r][c]) {
                        buttons[r][c].setText("Oh you clicked me....");
                    }
                }
            }
        }
    }
    private void clearBoard(){
        panelUpper.removeAll();
        panelUpper.setLayout(new GridLayout(row, col));
        buttons = new JButton[row][col];
    }
    private void setBoard(){
        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++){
                buttons[r][c] = new JButton(r+","+c);
                buttons[r][c].addActionListener(listener);
                panelUpper.add(buttons[r][c]);
            }
        }
    }
}
