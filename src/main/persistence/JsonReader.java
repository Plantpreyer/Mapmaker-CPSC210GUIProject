package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.CustomMap;

// MODELLED FROM SAMPLE APPLICATION IN PHASE 2

// Reads MapMaker from JSON data stored in file
public class JsonReader {
    private String fileSource;

    // EFFECTS: constructs reader with file source
    public JsonReader(String source) {
        fileSource = source;
    }

    // EFFECTS: reads List<CustomMap> from file, returns it
    // throws IOException if error
    public List<CustomMap> read() {
        List<CustomMap> mapList = new ArrayList<>();
        // stub;

        return mapList;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) {
        // stub;
        return "";
    }

    // EFFECTS: parses list of maps from JSONArray and returns it
    private List<CustomMap> parseMapList(JSONArray jsonArray) {
        List<CustomMap> mapList = new ArrayList<>();
        // stub;

        return mapList;
    }

    // MODIFIES: mapList
    // EFFECTS: parses maps from JSONArray and adds them to list
    private void addMaps(List<CustomMap> mapList, JSONArray jsonArray) {

    }

    // MODIFIES: mapList
    // EFFECTS: parses map from JSONObject and adds it to list
    private void addMap(List<CustomMap> mapList, JSONObject jsonObject) {

    }
}
