package model.feature;

// Represents a section of an object or zone body, with location of centre relative to feature
// REQUIRES: shape is "rect" or "circ"
public class FeatureSection {
    private int height;
    protected int xdim;
    protected int ydim;
    protected int radius;
    protected String shape;
    protected int xpos; // relative to the coords of the feature
    protected int ypos;

    // REQUIRES: dimensions, height are positive (not 0)
    // EFFECTS: Constructs a rectangle
    public FeatureSection(int x, int y, int xdim, int ydim,
            int height) {
        this.xpos = x;
        this.ypos = y;
        this.xdim = xdim;
        this.ydim = ydim;
        this.height = height;
        this.shape = "rect";
    }

    // REQUIRES: radius, height > 0
    // EFFECTS: Constructs a circle
    public FeatureSection(int x, int y, int radius, int height) {
        this.xpos = x;
        this.ypos = y;
        this.xdim = radius * 2;
        this.ydim = radius * 2;
        this.height = height;
        this.shape = "circ";
    }

    // EFFECTS: draws a shape to represent the section of the feature on the map
    void drawFeature() {

    }

    public int getHeight() {
        return height;
    }
}