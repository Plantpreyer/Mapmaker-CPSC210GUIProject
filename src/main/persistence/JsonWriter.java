package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.CustomMap;

// MODELLED OFF SAMPLE APPLICATION FOR PHASE 2

// Represents a class that writes JSON representation of Maps to file
public class JsonWriter {

    private static final int TAB_LENGTH = 4;
    private PrintWriter writer;
    private String destinationStr;

    // EFFECTS: constructs a writer with destination
    public JsonWriter(String destination) {
        destinationStr = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if dest. file cant be
    // opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destinationStr));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON MapMaker to file
    public void write(List<CustomMap> mapList) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mapList.size(); i++) {
            JSONObject json = mapList.get(i).toJson();
            jsonArray.put(json);
        }
        saveToFile(jsonArray.toString(TAB_LENGTH));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
