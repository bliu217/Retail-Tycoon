package persistence;

import model.Cashier;
import model.Highscores;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Cashier and highscores to file
    public void write(Cashier c, Highscores h) {
        JSONObject json = new JSONObject();
        JSONObject cashier = c.toJson();
        JSONArray highscores = h.scoresToJson();
        json.put("Cashier", cashier);
        json.put("Highscores", highscores);
        saveToFile(json.toString(TAB));
    }



    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }


}
