package model.feature;

import java.util.*;

// Represents a route on the map with an ordered list of points
public class Route extends Feature {
    List<MapPoint> points;

    public Route() {
        super("", 0, 0);
        points = new ArrayList<>();
    }

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public Route(String name, int x, int y) {
        super(name, x, y);
        points = new ArrayList<>();
    }

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public Route(String name, int x, int y, List<MapPoint> points) {
        super(name, x, y);
        this.points = points;
    }

    // EFFECTS: displays info of route
    void showInfo() {

    }

    // EFFECTS: draws route on map
    void drawFeature() {

    }

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
}