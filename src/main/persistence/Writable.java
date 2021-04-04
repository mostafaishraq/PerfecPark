package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    // toJson() taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    JSONObject toJson();
}
