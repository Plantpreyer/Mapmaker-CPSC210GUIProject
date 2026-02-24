package model.feature;

// Represents a section of an object or zone body, with location of centre relative to feature
// REQUIRES: shape is "rect" or "circ"
public class FeatureSection {
    private int height;
    protected int xDim;
    protected int yDim;
    protected int radius;
    protected String shape;
    protected int x; // relative to the coords of the feature
    protected int y;

    // REQUIRES: dimensions, height are positive (not 0)
    // EFFECTS: Constructs a rectangle
    public FeatureSection(int x, int y, int xDim, int yDim, 
                            int height) {
        this.x = x;
        this.y = y;
        this.xDim = xDim;
        this.yDim = yDim;
        this.height = height;
        this.shape = "rect";
    }

    // REQUIRES: radius, height > 0
    // EFFECTS: Constructs a circle
    public FeatureSection(int x, int y, int radius, int height) {
        this.x = x;
        this.y = y;
        this.xDim = radius * 2;
        this.yDim = radius * 2;
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