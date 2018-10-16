/**
 * Created by SirBlooby on 9/28/2016.
 *
 * This utilized GuessingGame by putting the methods to use.
 *
 * This feels a bit redundant but it keeps it nice and clean
 */
public class Game extends GuessingGame{

    //instantiate game variables
    private GuessingGame game;
    private int count;
    private int guess;
    private int range;
    private int guesses;

    public Game(int range, int guesses) {
        this.range = range;
        this.guesses = guesses;
    }

    public int getRange(){
        return range;
    }


    //any helper methods or additional methods not covered in the
    // interface are added here
    String guessesLeft(){
        return "Guesses Left: " + getCount();
    }
}
