package F17project2.untitled.src;
import java.util.ArrayList;
/*******************************************************************
 * @author Ethan Carter
 *
 * This is where Surround game mechanics are stored. There is heavy
 * relience on the Cell object. Also, the way I ened up writing this
 * is pretty sketchy, so if it doesn't work then I have no idea how
 * I will fix it. Let it be known that this is way more complicated
 * than recursion, but I really want that extra credit.
 ******************************************************************/
public class SurroundGame {
    /***************************************************************
     @Cell[][] board, the array of cells for the board
     @int size, the dimension of the board. The board will always
                 be size x size, to remain being square.
     **************************************************************/
    private Cell[][] board;
    private int size;
    private ArrayList<Integer> winners;

    /***************************************************************
     SurroundGame constructor. By default the game is played on a
     10x10 board.
     **************************************************************/
    public SurroundGame() {
        size = 10;
        board = new Cell[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = new Cell(r,c);
                createCell(r,c);
            }
        }
    }

    /***************************************************************
     SurroundGame constructor. Takes the size parameter and makes a
     size x size board.

     @param size, the width and length of your board
     **************************************************************/
    public SurroundGame(int size){
        this.size = size;
        board = new Cell[size][size];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                //this is a bundle of problems
                //the cell array doesn't actually get
                //instantiated
                board[r][c] = new Cell(r,c);
                createCell(r,c);
            }
        }
    }

    /***************************************************************
     Method that helps check if something is at a certain spot,
     using the parameters.

     @param row, the row of the cell
     @param col, the column of the cell
     **************************************************************/
    public boolean select(int row, int col){
        //is there a player there?
        if(board[row][col].getPlayerNumber() > 0){
            //there is, please don't change this
            return false;
        }
        //guess not, go ahead
        else return true;
    }

    /***************************************************************
     Method that resets the board to a new board. However you will
     likely end up resetting the game through the SurroundPanel.
     **************************************************************/
    public void reset(){
        board = new Cell[size][size];
    }

    /***************************************************************
     Method that checks to see if someone has won the game.
     TODO this method is not navigating all the cells
     @return i, i being the winner
     @return -1, if no winner
     @return 0 if there are no winners and board is full
     **************************************************************/
    public ArrayList<Integer> isWinner(){
        winners = new ArrayList<>();
        int counter = 0;
        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){
                int tempID;
                ArrayList<Integer[]> tempLib;
                    //if cell is not empty, meaning its definitely has an ID
                if(!select(r,c)){
                    //we found a full cell, count up
                    counter++;

                    //takes the cells liberties
                    tempLib = board[r][c].getLiberties();

                    //what is the id?
                    tempID = getLibID(tempLib.get(0));

                    //check the liberties
                    if(checkLibs(tempID, tempLib) &&
                            !(tempID == 0) &&
                            !(tempID == board[r][c].getPlayerNumber())){
                        winners.add(tempID - 1); //SurroundPanel player id starts at 0
                    }
                }
            }
        }
        //if we reached the end of the looping, then I guess you don't
        // have a winner.
        if(counter == (size * size)){
            return null;
        }

        return winners;
    }

    /***************************************************************
     Basic getter. Returns board.
     **************************************************************/
    public Cell[][] getBoard(){
        return board;
    }


    /***************************************************************
     Method that creates a cell the right way. This will ensure the
     cell has it's liberties defined and that it knows it's row and
     column given the parameters.

     This was designed to be used after the creation of a new board,
     and should not be used to place player cells

     @param r, the row of the cell
     @param c, the column of the cell
     **************************************************************/
    public void createCell(int r, int c){
        this.board[r][c] = new Cell(r, c);

        try{
            validateLib((r - 1), c);
            board[r][c].addLibFromBoard(r - 1, c);
        }
        catch(IndexOutOfBoundsException index){
//            System.out.println("This is either a corner or side," +
//                    " it's whatever.");
        }
        catch(Exception e){
            System.out.println("I don't know what the hell went wrong");
        }

        try{
            validateLib((r + 1), c);
            board[r][c].addLibFromBoard(r + 1, c);
        }
        catch(IndexOutOfBoundsException index){
//            System.out.println("This is either a corner or side," +
//                    " it's whatever.");
        }
        catch(Exception e){
            System.out.println("I don't know what the hell went wrong");
        }

        try{
            validateLib(r, (c - 1));
            board[r][c].addLibFromBoard(r, c - 1);
        }
        catch(IndexOutOfBoundsException index){
//            System.out.println("This is either a corner or side," +
//                    " it's whatever.");
        }
        catch(Exception e){
            System.out.println("I don't know what the hell went wrong");
        }

        try{
            validateLib(r, (c + 1));
            board[r][c].addLibFromBoard(r, c + 1);
        }
        catch(IndexOutOfBoundsException index){
//            System.out.println("This is either a corner or side," +
//                    " it's whatever.");
        }
        catch(Exception e){
            System.out.println("I don't know what the hell went wrong");
        }
    }

    //this does not add liberties which separates it from createCell.
    /***************************************************************
     Method that places a cell at a desired location, and changes
     the identity as well. This also covers the merging of cells
     into cell groups which allows me to avoid recursion.

     This was designed to be used when placing a cell with a player
     identity, and should only be used that way.

     @param r, the row of the cell
     @param c, the column of the cell
     @param id, the identity of the player
     **************************************************************/
    public void placeCell(int r, int c, int id){
        board[r][c].setPlayerNumber(id);

        //check to see any surrounding cells are compatible for merger
        try{
            //of the one above has the same number, merge.
            if(board[r - 1][c].getPlayerNumber() == board[r][c].getPlayerNumber()){
                board[r - 1][c].removeLibFromCell(r,c);

                //merge
                board[r][c].addLibFromCell(board[r - 1][c].getLiberties());
                board[r][c].groupUp(board[r-1][c].getGroup());

                board[r - 1][c] = board[r][c];
                board[r][c].trimLibs();
            }
        }
        catch(IndexOutOfBoundsException index){
            System.out.println("You checked merger with a non-existent cell");
        }
        catch(Exception e){
            System.out.println("I don't know what the hell went wrong");
        }

        try{
            //if the one below has the same number, merge.
            if(board[r + 1][c].getPlayerNumber() == board[r][c].getPlayerNumber()){
                //delete the liberty that has the position of the new cell
                board[r + 1][c].removeLibFromCell(r,c);

                //merge
                board[r][c].addLibFromCell(board[r+1][c].getLiberties());
                board[r][c].groupUp(board[r+1][c].getGroup());

                //this actually works. I'm genuinely surprised
                    //not really used to things going the way I want
                board[r+1][c] = board[r][c];
                board[r][c].trimLibs();
            }
        }
        catch(IndexOutOfBoundsException index){
            System.out.println("You checked merger with a non-existent cell");
        }
        catch(Exception e){
            System.out.println("I don't know what the hell went wrong");
        }

        try{
            //if the one to the left has the same number, merge.
            if(board[r][c - 1].getPlayerNumber() == board[r][c].getPlayerNumber()){
                //remove this location from the other cell list
                board[r][c - 1].removeLibFromCell(r,c);

                //merge
                board[r][c].addLibFromCell(board[r][c - 1].getLiberties());
                board[r][c].groupUp(board[r][c-1].getGroup());

                board[r][c -1] = board[r][c];
                board[r][c].trimLibs();
            }
        }
        catch(IndexOutOfBoundsException index){
            System.out.println("You checked merger with a non-existent cell");
        }
        catch(Exception e){
            System.out.println("I don't know what the hell went wrong");
        }

        try{
            //if the one to the right has the same number, merge.
            if(board[r][c + 1].getPlayerNumber() == board[r][c].getPlayerNumber()){
                board[r][c + 1].removeLibFromCell(r,c);

                //merge
                board[r][c].addLibFromCell(board[r][c + 1].getLiberties());
                board[r][c].groupUp(board[r][c + 1].getGroup());

                board[r][c + 1] = board[r][c];
                board[r][c].trimLibs();
            }
        }
        catch(IndexOutOfBoundsException index){
            System.out.println("You checked merger with a non-existent cell");
        }
        catch(Exception e){
            System.out.println("I don't know what the hell went wrong");
        }
     }

    /***************************************************************
     Method that searches the list of liberties from a given cell
     and makes the id of them is all the same. If any are open, or
     are not the same as the identity handed to us, then return
     false. Otherwise, we have a positive result for the id handed
     to us.

     @param identity, the id of a player
     @param liberties, the list of liberties we are checking
     @return flag, true being we have a winner, false being we don't
     **************************************************************/
    public boolean checkLibs(int identity,
                             ArrayList<Integer[]> liberties) {
        int i = 0;
        int r;
        int c;
        boolean flag = true;
        while(i < liberties.size()){
            //if this isn't smelly I don't know what is
            r = liberties.get(i)[0];
            c = liberties.get(i)[1];
            //if the identity doesn't match the one found at a
            // liberty, then set the flag to false
            if(!(board[r][c].getPlayerNumber() == identity)){
                flag = false;
            }
            i++;
        }
        return flag;
    }

    /***************************************************************
     Cell constructor. Takes the location into account and holds on
     to that info. Also sets default player number to 0, meaning no
     current player there.

     @param liberty, the point of whose ID we want
     @return id, the id of the player at the cell[r][c]
     **************************************************************/
    private int getLibID(Integer[] liberty){
        int r = liberty[0];
        int c = liberty[1];

        return board[r][c].getPlayerNumber();
    }

    /***************************************************************
     Sort of a debugging tool that allows me to see what the
     liberties are.

     @param row, the row you want to print from
     @param col, the column you want to print from
     **************************************************************/
    public void printLib(int row, int col){
        ArrayList<Integer[]> list = board[row][col].getLiberties();
        int r;
        int c;

        System.out.println("Printing the liberties of: " + row
                + "," + col);
//        System.out.println("" + board[row][col].getPlayerNumber());
        for(int i = 0; i < list.size(); i++){
            r = list.get(i)[0];
            c = list.get(i)[1];
            System.out.println(r + "," + c);
        }

//        System.out.println("done");
    }

    /***************************************************************
     Makes sure a given liberty @location (r,c) is within the
     boundaries of the game board. This is intended to be used in
     PlaceCell only as a trigger to prevent adding liberties that
     don't exist.

     @param r, the row you want to print from
     @param c, the column you want to print from
     @throws IndexOutOfBoundsException, lets you know this location
                                        is not within boundaries
     **************************************************************/
    private void validateLib(int r, int c){
        if( 0 <= r && r < size && c < size && 0 <= c){
            //this should just do nothing
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }

    /***************************************************************
     Sort of a debugging tool that allows me to see the board on
     display. Just one of those useful things you think you might
     want.
     **************************************************************/
    public void printBoard(){
        System.out.println("Printing Board...");
        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){
                System.out.print(board[r][c].getPlayerNumber() + " ");
            }
            System.out.println("");
        }
    }
}
