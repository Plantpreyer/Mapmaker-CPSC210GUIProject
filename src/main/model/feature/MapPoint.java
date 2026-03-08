package model.feature;

import java.util.*;

// Represents a point on the map with a name
public class MapPoint extends Feature {
    // List<FeatureSection> area;

    public MapPoint() {
        super("", 0, 0);
    }

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public MapPoint(String name, int x, int y) {
        super(name, x, y);
    }

    // EFFECTS: displays info
    // void showInfo() {

    // }

    // EFFECTS: draws point
    // void drawFeature() {

    // }

    @Override
    public List<String> getInfo() {
        ArrayList<String> info = getPosList();

        return info;
    }

}