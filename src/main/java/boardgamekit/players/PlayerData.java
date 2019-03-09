package boardgamekit.players;

import org.json.JSONObject;
import boardgamekit.utility.Game;

/**
 * PlayerData
 */
public class PlayerData {
    final private String userName;
    private JSONObject scoreBreakdown;

    private PlayerData(String userName, JSONObject scoreBreakdown) {
        this.userName = userName;
        this.scoreBreakdown = scoreBreakdown;
    }

    public static PlayerData create(String userName, JSONObject scoreBreakdown) {
        return new PlayerData(userName, scoreBreakdown);
    }

    public static PlayerData createDefault(String userName) {
        return new PlayerData(userName, new JSONObject());
    }

    public String getUsername() {
        return userName;
    }

    JSONObject getScoreBreakdown() {
        return scoreBreakdown;
    }

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
