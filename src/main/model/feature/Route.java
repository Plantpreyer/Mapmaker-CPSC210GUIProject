package model.feature;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a route on the map with an ordered list of points
public class Route extends Feature {
    List<MapPoint> points;

    public Route() {
        super("", 0, 0);
        points = new ArrayList<>();
    }

    // REQUIREMENTS: name not empty
    public Route(String name) {
        super(name, 0, 0);
        this.points = new ArrayList<>();
    }

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public Route(String name, List<MapPoint> points) {
        super(name, 0, 0);
        this.points = List.copyOf(points);
    }

    public List<MapPoint> getPoints() {
        return points;
    }

    public void setPoints(List<MapPoint> points) {
        this.points = points;
    }

    // EFFECTS: displays info of route
    // void showInfo() {

    // }

    // EFFECTS: draws route on map
    // void drawFeature() {

    // }

    // EFFECTS: returns list of info on each point. name, coords
    public List<String> getInfo() {
        ArrayList<String> info = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            MapPoint tempPt = points.get(i);
            info.add("Point " + (i + 1) + ": \'" + tempPt.getName() + "\'");

            List<String> tempInfo = tempPt.getInfo();
            for (String b : tempInfo) {
                info.add("\t" + b);
            }
        }
        return info;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("points", pointsToJson());

        return json;
    }

    // EFFECTS: returns points as json array
    private JSONArray pointsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MapPoint b : points) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }
}