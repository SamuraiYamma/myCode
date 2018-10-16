import java.util.Random;

/**
 * Created by SirBlooby on 9/28/2016.
 */
public class GuessingGame implements GameMechanics{
    private int count;
    private int toGuess;
    private int guess;
    private int guesses;
    private int range;
    private GameStatus status;


    @Override
    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public void reset() {
        resetCounter();
        setRandomValue(this.range);
    }

    @Override
    public void setGuess(int guess) {
        this.guess = guess;
    }

    @Override
    public int getGuess(){
        return this.guess;
    }

    @Override
    public void setRandomValue(int range) {
        Random randomGenerator = new Random();
        this.toGuess = randomGenerator.nextInt(range);
    }

    @Override
    public boolean processGuess() {
        if(this.guess == this.toGuess){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean highLow() {
        if(this.guess > toGuess){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public GameStatus getStatus() {
        return status;
    }

    @Override
    public void resetCounter() {
        this.count = getGuesses();
    }

    @Override
    public void countDown() {
        this.count--;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }

    @Override
    public int getGuesses() {
        return this.guesses;
    }

    @Override
    public GameStatus isWinner() {
        //checks to make sure the game is indeed won
        if(this.guess == this.toGuess){
            return GameStatus.USER_WON;
        }
        return GameStatus.IN_PROGRESS;
    }

    @Override
    public GameStatus isLoser() {
        //checks if count is 0
        if(this.count == 0){
            return GameStatus.USER_LOST;
        }
        return GameStatus.IN_PROGRESS;
    }

    @Override
    public GameStatus getGameStatus() {
        return status;
    }
}
