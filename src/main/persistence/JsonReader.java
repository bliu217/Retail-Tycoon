package persistence;

import model.Cashier;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.Highscores;
import org.json.*;

// Represents a reader that reads cashier statuses from JSON data stored in file
public class JsonReader {
    private final String source;


    // EFFECTS: constructs a reader that reads file from a source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Cashier from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Cashier read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject json = jsonObject.getJSONObject("Cashier");
        return parseCashier(json);
    }

    // EFFECTS: reads highscores from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Highscores getHighscores() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parseScores(json);
    }


    // EFFECTS: reads source file as string and returns it
    // CODE FROM JSON EXAMPLE
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses highscores from JSON object and returns it
    private Highscores parseScores(JSONObject json) {
        JSONArray jsonArray = json.getJSONArray("Highscores");
        Highscores h = new Highscores();
        List<Cashier> temp = new ArrayList<>();

        for (Object object : jsonArray) {
            JSONObject nextCashier = (JSONObject) object;
            temp.add(parseCashier(nextCashier));
        }
        h.setScores(temp);
        return h;
    }

    // EFFECTS: parses Cashier from JSON object and returns it
    private Cashier parseCashier(JSONObject jsonObject) {

        int score = jsonObject.getInt("score");
        int balance = jsonObject.getInt("balance");
        String name = jsonObject.getString("saveName");

        Cashier c = new Cashier(score, balance);
        c.setSaveName(name);
        addInventory(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses inventory from JSON object and adds it to cashier inventory
    private void addInventory(Cashier c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");

        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            String name = nextItem.getString("name");
            c.addInventory(c.stringToItem(name));
        }
    }


}
