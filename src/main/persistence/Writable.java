package persistence;

import org.json.JSONObject;

// represents a writable object to save data to
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
