package boardgamekit.players;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import boardgamekit.utility.Game;

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
public class PlayerData {
    final private String userName;
    private JSONObject scoreBreakdown;

    private PlayerData(String userName, JSONObject scoreBreakdown) {
        this.userName = userName;
        this.scoreBreakdown = scoreBreakdown;
    }

    /**
     * Factory method to create an object with existing scores already stored
     * in a JSON object.
     * @param userName the name of the user
     * @param scoreBreakdown a JSON object of the user's scores for each game
     * @return a new {@code PlayerData} object
     */
    public static PlayerData create(String userName, JSONObject scoreBreakdown) {
        return new PlayerData(userName, scoreBreakdown);
    }

    /**
     * Creates a default player by adding an associating game scores
     * JSON object with all zeroes for each game.
     * @param userName the name of the player
     * @return a new {@code PlayerData} object
     */
    public static PlayerData createDefault(String userName) {
        JSONObject scores = new JSONObject();
        List<Game> somethingList = Arrays.asList(Game.values());
        for (Game game : somethingList) {
            scores.put(game.toString().toLowerCase(), 0);
        }
        return new PlayerData(userName, scores);
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
     * Returns the player's score for a particular game.
     * @param game the game to retrieve the score for.
     * @return and int representing the number of games the
     * player has won for that particular board game.
     */
    public int getScoreFor(Game game) {
        switch (game) {
            case CHECKERS:
                return scoreBreakdown.getInt("checkers");
            case OTHELLO:
                return scoreBreakdown.getInt("othello");
            case MEMORY:
                return scoreBreakdown.getInt("memory");
            case TICTACTOE:
                return scoreBreakdown.getInt("tictactoe");
            default:
                return 0;
        }
    }

    /**
     * Sets the player's score for a particular game.
     * @param game the board game to update the score for
     * @param score the new score for that board game
     */
    public void setScoreFor(Game game, int score) {
        switch (game) {
            case CHECKERS:
                scoreBreakdown.put("checkers", score);
                break;
            case OTHELLO:
                scoreBreakdown.put("othello", score);
                break;
            case MEMORY:
                scoreBreakdown.put("memory", score);
                break;
            case TICTACTOE:
                scoreBreakdown.put("tictactoe", score);
                break;
        }
    }
}
