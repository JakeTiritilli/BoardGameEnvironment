package boardgamekit.players;

import org.json.JSONObject;

/**
 * This class serves as a representation of the data
 * associated with the player of the board games. It
 * contains general methods for retrieving and updating
 * scores for various games. The {@code scoreBreakdown}
 * property should be a JSON object in the following format,
 * where each number represents the **number of games won**:
 * 
 *      {
 *          "checkers": 1,
 *          "tictactoe": 4,
 *          "memory": 5,
 *          "othello": 2
 *      }
 * 
 * @author Jacob Tiritilli
 */
public class Player {
    final private String userName;
    private JSONObject scoreBreakdown;

    private Player(String userName, JSONObject scoreBreakdown) {
        this.userName = userName;
        this.scoreBreakdown = scoreBreakdown;
    }

    /**
     * Factory method to create an object with existing scores already stored
     * in a JSON object.
     * @param userName the name of the user
     * @param scoreBreakdown a JSON object of the user's scores for each game
     * @return a new {@code Player} object
     */
    public static Player create(String userName, JSONObject scoreBreakdown) {
        return new Player(userName, scoreBreakdown);
    }

    /**
     * Creates a default player by adding an associating game scores
     * JSON object with all zeroes for each game.
     * @param userName the name of the player
     * @return a new {@code Player} object
     */
    public static Player createDefault(String userName) {
        JSONObject scores = new JSONObject();
        return new Player(userName, scores);
    }

    /**
     * Returns the username.
     * @return username of player
     */
    public String getUsername() {
        return userName;
    }

    /**
     * Returns a JSON object of which acts as a dictionary
     * which stores the player's scores for each game.
     * @return JSON object of the player's scores for each game
     * available.
     */
    JSONObject getScoreBreakdown() {
        return scoreBreakdown;
    }

    /**
     * Gets the player's score for a particular game.
     * @param game the board game to return the score for
     */
    public int getScoreFor(String game) {
        return scoreBreakdown.getInt(game);
    }

    /**
     * Sets the player's score for a particular game.
     * @param game the board game to update the score for
     * @param score the new score for that board game
     */
    public void setScoreFor(String game, int score) {
        scoreBreakdown.put(game, score);
    }
}
