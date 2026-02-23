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
        return new ArrayList<>(); // stub
    }

    // EFFECTS: displays info of building, including name, max height, coords

    public void showInfo() {

    }

    // EFFECTS: draws the building on the map
    void drawFeature() {

    }
}
