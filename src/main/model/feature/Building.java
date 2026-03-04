package model.feature;

import java.util.List;
import java.util.ArrayList;

// Represents a building
public class Building extends MapObject {

    public Building() {
        super("", 0, 0, 0);
    }

    public Building(String name, int x, int y, int height) {
        super(name, x, y, height);
    }

    // EFFECTS: returns info of building, coords, max height
    // coords: 1, 2
    // max height: 123 OR "none"
    // sections: 4 sections
    public List<String> getInfo() {
        ArrayList<String> info = getPosList();
        info.add("height: " + (getHeight()));
        // info.add("Sections: " + getBody().size() + " sections");
        return info;
    }

    // EFFECTS: returns max height of any section in body
    public int getHeight() {
        return height;
    }

    public String getType() {
        return "building";
    }

    // EFFECTS: displays info of building, including name, max height, coords
    public void showInfo() {

    }

    // EFFECTS: draws the building on the map
    void drawFeature() {

    }
}
