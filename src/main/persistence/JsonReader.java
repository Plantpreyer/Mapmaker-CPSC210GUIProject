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
import model.exceptions.ObjectClassificationException;
import model.feature.Building;
import model.feature.MapObject;
import model.feature.MapPoint;
import model.feature.Route;
import model.feature.TreeFeature;

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
    public List<CustomMap> read() throws IOException, ObjectClassificationException {
        String jsonData = readFile(fileSource);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseMapList(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses list of maps from JSONArray and returns it
    private List<CustomMap> parseMapList(JSONArray jsonArray) throws ObjectClassificationException {
        List<CustomMap> mapList = new ArrayList<>();
        addMaps(mapList, jsonArray);

        return mapList;
    }

    // MODIFIES: mapList
    // EFFECTS: parses maps from JSONArray and adds them to list
    private void addMaps(List<CustomMap> mapList, JSONArray jsonMapList) throws ObjectClassificationException {
        for (Object b : jsonMapList) {
            JSONObject nextMap = (JSONObject) b;
            addMap(mapList, nextMap);
        }
    }

    // MODIFIES: mapList
    // EFFECTS: parses map from JSONObject and adds it to list
    private void addMap(List<CustomMap> mapList, JSONObject jsonObject) throws ObjectClassificationException {
        String name = jsonObject.getString("name");
        CustomMap tempMap = new CustomMap(name);
        addObjects(tempMap, jsonObject.getJSONArray("objects"));
        addRoutes(tempMap, jsonObject.getJSONArray("routes"));

        mapList.add(tempMap);
    }

    // MODIFIES: map
    // EFFECTS: parses MapObjects from JSONArray and adds it to map
    private void addObjects(CustomMap map, JSONArray jsonObjectList) throws ObjectClassificationException {
        for (Object b : jsonObjectList) {
            JSONObject mapObject = (JSONObject) b;
            addObject(map, mapObject);

        }
    }

    // EFFECTS: parses object from JSONObject and adds it to map
    private void addObject(CustomMap map, JSONObject jsonObject) throws ObjectClassificationException {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        int xpos = jsonObject.getInt("xpos");
        int ypos = jsonObject.getInt("ypos");
        int height = jsonObject.getInt("height");
        MapObject mapObject;
        mapObject = parseMapObjectType(name, type, xpos, ypos, height, jsonObject);
        map.addObject(mapObject);

    }

    // EFFECTS: returns a mapobject with type depending on String type and fields
    // depending on parameters
    private MapObject parseMapObjectType(String name, String type, int xpos, int ypos, int height,
            JSONObject jsonObject) throws ObjectClassificationException {
        MapObject mapObject;
        switch (type) {
            case "building":
                int width = jsonObject.getInt("xDim");
                int length = jsonObject.getInt("yDim");
                mapObject = new Building(name, xpos, ypos, width, length, height);
                break;
            case "tree":
                int radius = jsonObject.getInt("radius");
                mapObject = new TreeFeature(name, xpos, ypos, radius, height);
                break;
            default:
                throw new ObjectClassificationException();
        }
        return mapObject;
    }

    // MODIFIES: map
    // EFFECTS: parses routes from JSONArray and adds it to map
    private void addRoutes(CustomMap map, JSONArray jsonRouteList) {
        for (Object b : jsonRouteList) {
            JSONObject route = (JSONObject) b;
            addRoute(map, route);

        }
    }

    // EFFECTS: parses route from JSONObject and adds it to map
    private void addRoute(CustomMap map, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray points = jsonObject.getJSONArray("points");
        Route newRoute = new Route(name);

        addPoints(newRoute, points);

        map.addRoute(newRoute);
    }

    // EFFECTS: parses points from JSONArray and adds it to route
    private void addPoints(Route route, JSONArray pointsList) {
        List<MapPoint> mapPoints = new ArrayList<>();

        for (Object b : pointsList) {
            JSONObject point = (JSONObject) b;
            String name = point.getString("name");
            int xpos = point.getInt("xpos");
            int ypos = point.getInt("ypos");

            mapPoints.add(new MapPoint(name, xpos, ypos));
        }

        route.setPoints(mapPoints);
    }
}