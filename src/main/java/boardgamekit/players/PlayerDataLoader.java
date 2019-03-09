package boardgamekit.players;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.json.JSONObject;
import org.json.JSONTokener; 

/**
 * UserData
 */
public class PlayerDataLoader {
    private File jsonFile;

    public PlayerDataLoader(String fileName) throws IOException {
        jsonFile = new File(fileName).getAbsoluteFile();
    }
    
    public PlayerData loadData(String userName) throws IOException {
        JSONTokener fileContent = new JSONTokener(new FileReader(jsonFile));
        JSONObject json = new JSONObject(fileContent);
        if (!json.has(userName)) {
            return PlayerData.createDefault(userName);
        }
        JSONObject playerData = json.getJSONObject(userName);
        return PlayerData.create(userName, playerData);
    }

    public void writeData(PlayerData playerData) throws IOException {
        JSONTokener fileContent = new JSONTokener(new FileReader(jsonFile));
        JSONObject json = new JSONObject(fileContent);
        json.put(playerData.getUsername(), playerData.getScoreBreakdown());
        FileWriter fileWriter = new FileWriter(jsonFile);
        fileWriter.write(json.toString());
        fileWriter.flush();
        fileWriter.close();
    }

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
