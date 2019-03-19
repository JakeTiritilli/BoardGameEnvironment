package boardgamekit.players;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.json.JSONObject;
import org.json.JSONTokener; 

/**
 * Contains functionality for reading and writing to a JSON file
 * contains key value pairs representing a player name and the associated
 * scores for each board game.
 * 
 * @author Jacob Tiritilli
 */
public class PlayerLoader {
    private File jsonFile;

    /**
     * Constructs an object.
     * @param fileName the path to the JSON file to be read/written to
     * @throws IOException if the file was not found
     */
    public PlayerLoader(String fileName) throws IOException {
        Path currentRelativePath = Paths.get("");
        String currentPath = currentRelativePath.toAbsolutePath().toString();
        File check = new File (currentPath + "/UserData.json");
        if (check.exists()) {
            jsonFile = check.getAbsoluteFile();
        }
        else {
            check.createNewFile();
            FileWriter writer = new FileWriter(check);
            writer.write("{}");
            writer.close();
            jsonFile = check.getAbsoluteFile();
        }
    }
    
    /**
     * Loads all of the players' data from the JSON file and constructs
     * a {@code Player} object containing the scores for the player
     * in each game.
     * @param userName the name of the user
     * @throws IOException
     */
    public Player loadData(String userName) throws IOException {
        JSONTokener fileContent = new JSONTokener(new FileReader(jsonFile));
        JSONObject json = new JSONObject(fileContent);
        
        if (!json.has(userName)) {
            return Player.createDefault(userName);
        }
        
        JSONObject player = json.getJSONObject(userName);
        return Player.create(userName, player);
    }

    public JSONObject dumpData() throws IOException {
        JSONTokener fileContent = new JSONTokener(new FileReader(jsonFile));
        JSONObject json = new JSONObject(fileContent);
        return json;
    }

    /**
     * Writes a {@code Player} object as JSON into the JSON file of
     * all user scores.
     * @param Player an obejct holding the name of the player along with
     * all of their score for each game
     * @throws IOException
     */
    public void writeData(Player Player) throws IOException {
        JSONTokener fileContent = new JSONTokener(new FileReader(jsonFile));
        JSONObject json = new JSONObject(fileContent);
        json.put(Player.getUsername(), Player.getScoreBreakdown());
        
        FileWriter fileWriter = new FileWriter(jsonFile);
        fileWriter.write(json.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Gets a list of all of the players registered in the system.
     * @return an {@code ArrayList} of all of the players that have game data
     * associated with them in the JSON file.
     * @throws IOException
     */
    public ArrayList<String> getPlayers() throws IOException {
        ArrayList<String> players = new ArrayList<>();
        JSONTokener fileContent = new JSONTokener(new FileReader(jsonFile));
        JSONObject json = new JSONObject(fileContent);
        
        for (String key : json.keySet()) {
            players.add(key);
        }
        
        return players;
    }
}
