package F17project2.untitled.src;
import java.util.ArrayList;
/*******************************************************************
 @author Ethan Carter

 This cell object will be used to represent different players and
 their game pieces. One thing that really needs to be defined is
 liberties.

 A liberty is a corrosponding point adjecent to our cell that is
 either NORTH, SOUTH, WEST and EAST. A cell in the middle of a board
 will have 4 liberties, while those on the side will have 3, and the
 corner cells have 2 liberties. This idea is the cornerstone of my
 game mechanics, as the utilization of liberties allows for easy
 checking of Cells and whose around them. By adding cells to each
 other in groups, you can also avoid recursion when checking a whole
 group of cells.

 Groups are easier understood as just a cell with a list of
 liberties that is bigger than 4. If you can visualize two cells
 next to each other, you will see how combined they have more
 liberties shared between them. The total of those liberties would
 be Liberties(A) + Liberties(B) - the liberties they have in common
 and the position of each cell.

 Hopefully this explanation makes my code more legible, as even I
 have a hard time understanding what it is I just birthed. Help.
 ******************************************************************/
public class Cell {
    /***************************************************************
     @int row - the row of this cell
     @int column - the column of this cell
     @ArrayList<Integer[]> liberties - a list of adjacent points
     @int playerNumber - the id of the cell
     **************************************************************/
    //i dont think i ever end up row or column.
            //kind of useless really.
    private int row, column;
    private ArrayList<Integer[]> liberties;
    private ArrayList<Integer[]> group;
    private int playerNumber;

    /***************************************************************
     Cell constructor. Takes the location into account and holds on
     to that info. Also sets default player number to 0, meaning no
     current player there.

     @param r, the row of the cell
     @param c, the column of the cell
     **************************************************************/
    public Cell(int r, int c) {
        this.row = r;
        this.column = c;
        this.playerNumber = 0;
        Integer[] temp = {this.row, this.column};
        group = new ArrayList<>();
        this.group.add(temp);
        liberties = new ArrayList<>();
    }

    /***************************************************************
     Cell constructor. Takes the id of the player and sets it to
     that particular cell

     @param playerNumber, the id of the player
     **************************************************************/
    public Cell(int playerNumber) {
        this.playerNumber = playerNumber;
        this.liberties = new ArrayList<>();
        this.group = new ArrayList<>();
    }

    /***************************************************************
     Method to set a player id to a certain cell.

     @param playerNumber, the id of a player
     **************************************************************/
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /***************************************************************
     Method that returns the id of the player there.

     @return the id of the player at this cell
     **************************************************************/
    public int getPlayerNumber() {
        return playerNumber;
    }

    /***************************************************************
     This adds the list of liberties from one cell to another,
     discarding any liberties that are repeated.

     @param otherLiberties, the list of liberties from another cell
     **************************************************************/
    public void addLibFromCell(ArrayList<Integer[]>
                                       otherLiberties) {
        //check if any of the other liberties are the same as current
        ArrayList<Integer[]> temp = otherLiberties;

        //compare each liberty in other to current
        for (int i = 0; i < liberties.size(); i++) {
            for (int j = 0; j < otherLiberties.size(); j++) {
                //if they are the same, remove that same liberty from temp
                if (compareLib(liberties.get(i), otherLiberties.get(j))) {
                    temp.remove(j);
                }
            }
        }
        //now temp should contain no duplicate coordinates
        //all though in hindsight it doesn't really seem to matter
        for (int i = 0; i < temp.size(); i++) {
            liberties.add(temp.get(i));
        }
    }
    /***************************************************************
     Method that removes the liberty containing the row and column
     given by the parameters. This is used to remove liberties that
     are either repeated or are taken up but the same id.

     @param r, the row of the cell
     @param c, the column of the cell
     **************************************************************/
    public void removeLibFromCell(int r, int c){
        Integer[] temp = {r,c};
        for(int i = 0; i < liberties.size(); i++){
            if (liberties.get(i) == temp){
                liberties.remove(i);
            }
        }
    }

    /***************************************************************
     Method that just adds the row and column to the list of
     liberties

     @param r, the row of the cell
     @param c, the column of the cell
     **************************************************************/
    public void addLibFromBoard(int r, int c) {
        Integer[] temp = {r, c};
        this.liberties.add(temp);
    }

    /***************************************************************
     Method that returns the liberties variable.

     @return liverties, the list of adjacent points
     **************************************************************/
    public ArrayList<Integer[]> getLiberties() {
        return this.liberties;
    }

    /***************************************************************
     Method that returns the group variable.

     @return group, the list of friendly point(s) including this
                    point
     **************************************************************/
    public ArrayList<Integer[]> getGroup() {
        return this.group;
    }


    /***************************************************************
     This is used to ensure that a Cell in a group doesn't contain
     liberties that are its group members.
     **************************************************************/
    public void trimLibs(){
        //take a group variable
        for(int i = 0; i < group.size(); i++){
            //compare it to liberties
            for(int j = 0; j < liberties.size(); ){
                //if they are the same, remove liberty
                if(compareLib(group.get(i), liberties.get(j))){
                    liberties.remove(j);
                }
                else{
                    //if not, move on
                    j++;
                }
                //it will move on anyways, but I don't want to
                //skip something by accident
            }
        }
    }

    /***************************************************************
     Adds the group list from outside and add it to this group list.
     If you were to print this from out of a group, you should be
     able to see all the cell locations from the group this is in,
     but it's not perfect. Works just enough that it does what I
     want it to.

     @param otherGroup, the group list from another cell
     **************************************************************/
    public void groupUp(ArrayList<Integer[]> otherGroup){
        //there should be no way for the other group to contain the
        //coordinates of this Cell, so every time we use this
        //the group should grow without redundancy
        for(int i = 0; i < otherGroup.size(); i++){
            this.group.add(otherGroup.get(i));

        }


    }

    /***************************************************************
     Method that compares two liberties.

     @param a, a liberty
     @param b, the other liberty
     **************************************************************/
    private boolean compareLib(Integer[] a, Integer[] b) {

        if(a[0] == b[0] && a[1] == b[1]){
            return true;
        }

        else return false;
    }
}