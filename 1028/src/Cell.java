import javax.swing.*;

/**
 * Created by Hans Dulimarta (Summer 2014)
 */
public class Cell implements Comparable<Cell> {
    public int row;
    public int column;
    public int value;

    public Cell() {
        this.row = 0;
        this.column = 0;
        this.value = 0;
    }

    public Cell (int r, int c, int v)
    {
        this.row = r;
        this.column = c;
        this.value = v;
    }

    /* must override equals to ensure List::contains() works
     * correctly
     */
    @Override
    //polymorphic
    public int compareTo (Cell other) {
        if (this.row < other.row) return -1;
        if (this.row > other.row) return +1;

        /* break the tie using column */
        if (this.column < other.column) return -1;
        if (this.column > other.column) return +1;

        return this.value - other.value;
    }

    /**
     * this is totally copied from the dude's code. There's really no
     * simpler way of doing it, and it is nice. See NumberGame
     * comments for more details on my source
     *
     * @return true if the value is 0, false if it is not
     */
    public boolean isEmpty(){
        return value == 0;
    }

    /**
     *
     * @return true if the value is not 0, false if it is
     */
    public boolean hasValue(){
        return value != 0;
    }
}