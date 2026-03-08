package model.feature;

import java.util.List;
import java.util.ArrayList;

// Represents a building
public class Building extends MapObject {

    public Building() {
        super("", 0, 0, 0);
    }

    public Building(String name) {
        super(name, 0, 0, 0);
    }

    public Building(String name, int x, int y, int height) {
        super(name, x, y, height);
    }

    public Building(String name, int x, int y, int xDim, int yDim, int height) {
        super(name, x, y, height);
        this.xDim = xDim;
        this.yDim = yDim;
    }

    // EFFECTS: returns info of building, coords, max height
    // coords: 1, 2
    // max height: 123 OR "none"
    // sections: 4 sections
    public List<String> getInfo() {
        ArrayList<String> info = getPosList();
        info.add("height: " + (getHeight()));
        info.add("width: " + getXdim() + " length: " + getYdim());
        return info;
    }

    // returns  a string representing the type of object (map / tree)
    public String getType() {
        return "building";
    }

    // EFFECTS: displays info of building, including name, max height, coords
    // public void showInfo() {

    // }

    // EFFECTS: draws the building on the map
    // void drawFeature() {

    // }
}
