/**
 * Created by SirBlooby on 9/28/2016.
 */
public interface GameMechanics {

    /**
     * Sets the range of the game
     * @param range
     *
     * @throws IllegalArgumentException when range is not an integer
     */
    void setRange(int range);

    /**
     * Resets the game to beginning stage
     */
    void reset();

    /**
     * Takes in a user's guess
     * @param guess
     *
     */
    void setGuess(int guess);


    /**
     *
     * @return user guess
     */
    int getGuess();


    /**
     * Takes the range of the current game and then creates a random
     * value to be guessed
     * @param range
     */
    void setRandomValue(int range);

    /**
     * @return true or false if the guess is the same as the game's
     * guess value
     */
    boolean processGuess();

    /**
     *
     * @return true if the guess is higher, and false if it is lower
     */
    boolean highLow();

    /**
     *
     * @return a game status from GameStatus class
     */
    GameStatus getStatus();

    /**
     * Resets the counter to the amount of guess
     */
    void resetCounter();

    /**
     * subtract 1 from the counter
     */
    void countDown();

    /**
     *
     * @return current count of counter
     */
    int getCount();

    /**
     * set the local guess variable to given input
     * @param guesses
     */
    void setGuesses(int guesses);

    /**
     *
     * @return the amount of guesses a user gets
     */
    int getGuesses();

    /**
     * opens a pop up window and lets user know the game is won
     * also resets the game
     * @return game status game is won
     */
    GameStatus isWinner();

    /**
     * opens a pop up window and lets the user know the game is lost
     * also resets the game
     * @return
     */
    GameStatus isLoser();


    /**
     *
     * @return current game status
     */
    GameStatus getGameStatus();

}
