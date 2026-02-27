package model.feature;

import java.util.List;
import java.util.ArrayList;

// Represents a building
public class Building extends MapObject {

    public Building(String name, int x, int y) {
        super(name, x, y);
    }

    // EFFECTS: returns info of building, including name, max height, coords
    // name: xxx
    // max height: 123 OR "none"
    // coords: 1, 2
    public List<String> getInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add("name: " + name);
        info.add("max height: " + (getBody().isEmpty() ? "none" : getMaxHeight()));
        info.add("coords: " + xpos + ", " + ypos);
        return info;
    }

    // EFFECTS: returns max height of any section in body
    public int getMaxHeight() {
        int max = getSection(0).getHeight();
        for (FeatureSection s : getBody()) {
            if (s.getHeight() > max) {
                max = s.getHeight();
            }
        }

        return max;
    }

    // EFFECTS: displays info of building, including name, max height, coords

    public void showInfo() {

    }

    // EFFECTS: draws the building on the map
    void drawFeature() {

    }
}
