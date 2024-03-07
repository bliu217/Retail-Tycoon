package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

// Represents a list of cashiers with descending score
public class Highscores {
    
    List<Cashier> scores;

    // EFFECTS: constructs an empty list of highscores
    public Highscores() {
        this.scores = new ArrayList<>();
    }

    // EFFECTS: returns the current list of cashiers
    public List<Cashier> getScores() {
        return this.scores;
    }

    // MODIFIES: this
    // EFFECTS: replaces current list of cashiers with new cashiers
    public void setScores(List<Cashier> newScores) {
        this.scores = newScores;
    }


    // EFFECTS: returns the list of cashiers as a JSON array
    public JSONArray scoresToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Cashier c : scores) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
