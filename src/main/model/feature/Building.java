package model.feature;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// Represents a building
public class Building extends MapObject {

    public Building() {
        super("", 0, 0);
    }

    public Building(String name, int x, int y) {
        super(name, x, y);
    }

    // EFFECTS: returns info of building, coords, max height
    // coords: 1, 2
    // max height: 123 OR "none"
    // sections: 4 sections
    public List<String> getInfo() {
        ArrayList<String> info = getPosList();
        info.add("max height: " + (getBody().isEmpty() ? "none" : getMaxHeight()));
        info.add("Sections: " + getBody().size() + " sections");
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

    public String getType() {
        return "building";
    }

    // EFFECTS: displays info of building, including name, max height, coords
    public void showInfo() {

    }

    // EFFECTS: draws the building on the map
    void drawFeature() {

    }

    public Building constructThis(String name, int xpos, int ypos, Scanner input) {
        return new Building(name, xpos, ypos);
    }
}
