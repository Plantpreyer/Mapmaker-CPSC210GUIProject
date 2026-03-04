package model.feature;

import java.util.*;

// Represents a category of features with dimensions and height
public abstract class MapObject extends Feature {
    // private List<FeatureSection> body;
    protected int height;

    public MapObject(String name, int x, int y, int height) {
        super(name, x, y);
        this.height = height;
        // body = new ArrayList<>();
    }

    // EFFECTS: draws all sections in body on map
    abstract void drawFeature();

    // EFFECTS: returns a string representing the type of object
    public abstract String getType();

    public ArrayList<String> getPosList() {
        ArrayList<String> info = new ArrayList<>();
        info.add("Position: " + xpos + ", " + ypos);
        return info;
    }

    // EFFECTS: returns list of strings with various information meant to be
    // displayed
    public abstract List<String> getInfo();

    public abstract int getHeight();

    // public List<FeatureSection> getBody() {
    //     return body;
    // }

    // public FeatureSection getSection(int ind) {
    //     return body.get(ind);
    // }

    // EFFECTS: adds section
    // public void addSection(FeatureSection fs) {
    //     body.add(fs);
    // }

    // MODIFIES: this
    // EFFECTS: adds a section to list of sections with specified attributes
    // public void addSection(int x, int y, int xdim, int ydim, int height) {
    //     FeatureSection s = new FeatureSection(x, y, xdim, ydim, height);
    //     body.add(s);
    // }

    // MODIFIES: this
    // EFFECTS: adds a section to list of sections with specified attributes
    // public void addSection(int x, int y, int radius, int height) {
    //     FeatureSection s = new FeatureSection(x, y, radius, height);
    //     body.add(s);
    // }

    // REQUIRES: index is valid for list of sections
    // MODIFIES: this
    // EFFECTS: deletes section at index from body
    // public void deleteSection(int index) {
    //     body.remove(index);
    // }

    // MODIFIES: this
    // EFFECTS: deletes all sections from body
    // public void clearBody() {
    //     body.clear();
    // }
}
