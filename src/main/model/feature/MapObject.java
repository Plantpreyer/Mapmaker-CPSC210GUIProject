package model.feature;

import java.util.*;

// Represents a category of features that have a body of sections with dimensions and height
public abstract class MapObject extends Feature {
    List<FeatureSection> body;

    public MapObject(String name, int x, int y) {
        super(name, x, y);
        body = new ArrayList<>();
    }

    // EFFECTS: draws all sections in body on map
    abstract void drawFeature();

    // EFFECTS: adds a section to list of sections with specified attributes
    private void addSection(int x, int y, int xDim, int yDim, int height, String shapeType) {

    }

    // REQUIRES: index is valid for list of sections
    // MODIFIES: this
    // EFFECTS: deletes section at index from body
    private void deleteSection(int index) {

    }

    // MODIFIES: this
    // EFFECTS: deletes all sections from body
    private void clearBody() {

    }


}
