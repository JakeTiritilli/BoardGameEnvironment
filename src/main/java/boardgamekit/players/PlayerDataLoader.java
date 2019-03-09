package boardgamekit.players;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
public class PlayerDataLoader {
    private File jsonFile;

    /**
     * Constructs an object.
     * @param fileName the path to the JSON file to be read/written to
     * @throws IOException if the file was not found
     */
    public PlayerDataLoader(String fileName) throws IOException {
        jsonFile = new File(fileName).getAbsoluteFile();
    }
    
    /**
     * Loads all of the players' data from the JSON file and constructs
     * a {@code PlayerData} object containing the scores for the player
     * in each game.
     * @param userName the name of the user
     * @throws IOException
     */
    public PlayerData loadData(String userName) throws IOException {
        JSONTokener fileContent = new JSONTokener(new FileReader(jsonFile));
        JSONObject json = new JSONObject(fileContent);
        
        if (!json.has(userName)) {
            return PlayerData.createDefault(userName);
        }
        
        JSONObject playerData = json.getJSONObject(userName);
        return PlayerData.create(userName, playerData);
    }

    /**
     * Writes a {@code PlayerData} object as JSON into the JSON file of
     * all user scores.
     * @param playerData an obejct holding the name of the player along with
     * all of their score for each game
     * @throws IOException
     */
    public void writeData(PlayerData playerData) throws IOException {
        JSONTokener fileContent = new JSONTokener(new FileReader(jsonFile));
        JSONObject json = new JSONObject(fileContent);
        json.put(playerData.getUsername(), playerData.getScoreBreakdown());
        
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
