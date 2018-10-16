
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Ethan Carter on 9/29/2016.
 */
public class NumberGame implements NumberSlider {
    private int rows, columns, toWin;
    private int[][] board;
    private Cell[][] grid;
    private Stack saves;
    private GameStatus status = GameStatus.IN_PROGRESS;

    /***************************************************************
     * Reset the game logic to handle a board of a given dimension
     *
     * @param height the number of rows in the board
     * @param width the number of columns in the board
     * @param winningValue the value that must appear on the board to
     *                     win the game
     * @throws IllegalArgumentException when the winning value is not power of two
     *  or negative
     **************************************************************/
    @Override
    public void resizeBoard(int height, int width, int winningValue) {

        String temp;
        if(winningValue % 2 != 0 || winningValue < 0){
            throw new IllegalArgumentException("Winning Value is " +
                    "either negative or not a multiple of two.");
        }
        else{
            this.rows = height;
            this.columns = width;
            this.toWin = winningValue;


            board = new int[rows][columns];

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    this.board[r][c] = 0;
                    temp = "" + this.board[r][c];
                }
            }
        }
        convert();
    }

    /*******************************************************************
     * Remove all numbered tiles from the board and place
     * TWO non-zero values at random location
     ******************************************************************/
    @Override
    public void reset() {
        board = new int[rows][columns];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                this.board[r][c] = 0;
            }
        }


        status = GameStatus.IN_PROGRESS;

        placeRandomValue();
        placeRandomValue();
        convert();
    }

    /*******************************************************************
     * Set the game board to the desired values given in the 2D array.
     * This method should use nested loops to copy each element from the
     * provided array to your own internal array. Do not just assign the
     * entire array object to your internal array object. Otherwise,
     * your internal array may get corrupted by the array used in the
     * JUnit test file. This method is mainly used by the JUnit
     * tester. Throws IllegalArgumentException if ref is null
     *
     * @param ref
     *
     * @exception IllegalArgumentException
     ******************************************************************/
    @Override
    public void setValues(int[][] ref) {
        if (ref == null){
            throw new IllegalArgumentException("Can't set board to " +
                    "null state");
        }
        else {
            this.board = ref;
            for (int r = 0; r < ref.length; r++) {
                for (int c = 0; c < ref[r].length; c++) {
                    this.board[r][c] = ref[r][c];
                }
            }
        }
    }

    /**
     * Insert one random tile into an empty spot on the board.
     *
     * @return a Cell object with its row, column, and value attributes
     * initialized properly
     * @throws IllegalStateException when the board has no empty cell
     */
    @Override
    public Cell placeRandomValue() {
        ArrayList<Cell> list = getEmptyCells();

        if (!getEmptyCells().isEmpty()) {
            int index = (int) (Math.random() * list.size()) %
                    list.size();
            Cell emptyCell = list.get(index);
            int twoOrFour = twoOrFour();
            emptyCell.value = twoOrFour;

            //hears row and column and places value there
            board[emptyCell.row][emptyCell.column] = emptyCell.value;

            convert();
            return emptyCell;
        }
        else {
            ArrayList<Cell> list1 = getNonEmptyTiles();
            for(int i = 0; i < list1.size(); i++){
                System.out.println(list1.get(i).value);
            }
            convert();
            status = GameStatus.USER_LOST;
            throw new IllegalStateException("Board is full");

        }

    }

    /**
     * Slide all the tiles in the board in the requested direction
     *
     * @param dir move direction of the tiles
     * @return true when the board changes
     */
    @Override
    public boolean slide(SlideDirection dir) {
        switch (dir) {
            case UP:
                up();
                return true;
            case DOWN:
                down();
                return true;
            case RIGHT:
                right();
                return true;
            case LEFT:
                left();
                return true;
        }
        return false;
    }

    /**
     * returns a list of cells that are not empty
     * @return list
     */
    @Override
    public ArrayList<Cell> getNonEmptyTiles() {
        ArrayList<Cell> list = new ArrayList<>();
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                if(board[r][c] != 0){
                    list.add(new Cell(r, c, board[r][c]));
                }
            }
        }

        /*//temp
        printList(list);*/
        return list;
    }

    /**
     * Return the current state of the game
     *
     * @return one of the possible values of GameStatus enum
     */
    @Override
    public GameStatus getStatus() {
        //if you cant move, turn status to lost
        if(!canMove()){
            status = GameStatus.USER_LOST;
        }
        else if(anyHasWon()){
            status = GameStatus.USER_WON;
        }
        return status;
    }

    /**
     * Undo the most recent action, i.e. restore the board to its previous
     * state. Calling this method multiple times will ultimately restore
     * the gam to the very first initial state of the board holding two
     * random values. Further attempt to undo beyond this state will throw
     * an IllegalStateException.
     *
     * @throws IllegalStateException when undo is not possible
     */
    @Override
    public void undo() {
        //if its empty, throw exception
        if(saves.isEmpty()){
            throw new IllegalStateException();
        }
        //otherwise pop the most recent object.
        else{
            saves.pop();
        }
    }

    /**
     * this is a totally crap way to do this. It works though.
     *
     * @return either 2 or 4
     */
    private static int twoOrFour() {
        return (int) (((Math.random() * 10) / 5) + 1) * 2;
    }

    /**
     * returns array list of cells with a value of 0
     *
     * @return list
     */
    private ArrayList<Cell> getEmptyCells() {
        final ArrayList<Cell> list = new ArrayList<>();
        ArrayList<Cell> boardList = intToList();
        for(Cell cell : boardList){
            if(cell.isEmpty()){
                list.add(cell);
            }
        }
        return list;
    }

    /**
     * returns a list of Cells from the board that have values other
     * than 0
     *
     * @return list
     */
    private ArrayList<Cell> getCells() {
        final ArrayList<Cell> list = new ArrayList<>();
        ArrayList<Cell> boardList = intToList();
        for(Cell cell : boardList){
            if(cell.hasValue()){
                list.add(cell);
            }
        }

        return list;
    }

    /**
     * returns a list of Cells for each element in the board
     * @return list
     */
    private ArrayList<Cell> intToList(){
        ArrayList<Cell> list = new ArrayList<>();

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                list.add(new Cell(r,c,board[r][c]));
            }
        }
        return list;
    }

    /**
     * if the list of non empty tiles is equal to maximum possible
     * tiles on the board, then it is full so it returns false. Any
     * other time it returns true.
     *
     * @return true or false
     */
    private boolean canMove() {
        ArrayList<Cell> list = getNonEmptyTiles();
        if (list.size() == (rows * columns)) {
            return false;
        }
        return true;
    }

    /**
     * helper for determining if any of the tiles on board won the game
     * @return true or false
     */
    private boolean anyHasWon(){
        ArrayList<Cell> list = getNonEmptyTiles();
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).value == toWin){
                return true;
            }
        }
        return false;
    }

    /**
     * helper method for moving all the tiles up
     */
    private void up(){
        rotate(board);
        rotate(board);
        rotate(board);
        left();
        rotate(board);

    }

    /**
     * helper method for moving all the tiles down
     */
    private void down(){
        ArrayList<Cell> list = getEmptyCells();
        rotate(board);
        left();
        rotate(board);
        rotate(board);
        rotate(board);
        ArrayList<Cell> list2 = getEmptyCells();
    }

    /**
     * helper method for moving all the tiles right
     */
    private void right(){
        ArrayList<Cell> list = getEmptyCells();
        rotate(board);
        rotate(board);
        left();
        rotate(board);
        rotate(board);
        ArrayList<Cell> list2 = getEmptyCells();
    }

    /**
     * helper method for doing left
     */
    private void left(){
        ArrayList<Cell> list1 = getEmptyCells();
        for (int r = 0; r < rows; r++){
        //put value from board row into a list
            ArrayList list = new ArrayList();
            for(int c = 0; c < columns; c++){
                //if board value isn't 0, put it into list
                if(board[r][c] != 0){
                    list.add(board[r][c]);
                }
            }
            ArrayList list2 = merge(list);
            replaceRow(list2, r);
        }
        placeRandomValue();
    }

    /**
     * takes an array of ints and merges properly, then returns merged
     * @param toMerge
     * @return merged
     */
    private ArrayList merge(ArrayList toMerge){
        ArrayList merged = new ArrayList();
        int current, next;
            //if list is empty, return it
            if(toMerge.size() == 0){
                return toMerge;
            }
            //if there is only one item, return it
            if(toMerge.size() == 1){
                merged.add(toMerge.get(0));
            }
            //if there are two or more
            if(toMerge.size() > 1) {
                toMerge.add(0);
                for(int i = 0; i < toMerge.size() - 1; i++) {
                    current = (int) toMerge.get(i);
                    next = (int) toMerge.get(i + 1);
                    //if current is equal to next, add and double it
                    if(current == next){
                        merged.add(current * 2);
                        i++;
                    }
                    //if current is not not equal to next, add it
                    else{
                        merged.add(current);
                    }
                }
            }
        return merged;
    }

    /**
     * replaces row on board with new updated row
     * @param list
     * @param r
     */
    private void replaceRow(ArrayList list, int r){
        //for the length of the array
        for(int c = 0; c < columns; c++){

            if(c < list.size()){
                board[r][c] = (int) list.get(c);
            }
            else{
                board[r][c] = 0;
            }
        }
    }

    /**
     * taken off of stack overflow
     * minor modification to make sure that rows and columns dont go
     * out of bounds
     *
     * takes the board and rotates it clockwise 90 degress
     * @param mat
     *
     */
    private void rotate(int[][] mat) {
        this.columns = mat.length;
        final int M = mat.length;
        this.rows = mat[0].length;
        final int N = mat[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = mat[r][c];
            }
        }
        this.board = ret;
    }

    /**
     * converts the board to a Cell array type
     */
    private void convert(){
        grid = new Cell[rows][columns];
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                grid[r][c] = new Cell(r,c,board[r][c]);
            }
        }
    }

    /**
     *
     * @return current grid
     */
    public Cell[][] getBoard() {
        return grid;
    }

}
