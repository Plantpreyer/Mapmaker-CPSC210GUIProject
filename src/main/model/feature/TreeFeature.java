package model.feature;


// Represents a tree
// REQUIRES: Can only have one circular section
public class TreeFeature extends MapObject {
    FeatureSection tree;

    public TreeFeature(String name, int x, int y, int radius, int height) {
        super(name, x, y);
        tree = new FeatureSection(0, 0, radius, height);
        body.add(tree);
    }

    // MODIFIES: this
    // EFFECTS: replaces body with new one
    void setBody(int radius, int height) {
        tree = new FeatureSection(0, 0, radius, height);
        clearBody();
        body.add(tree);
    }

    // EFFECTS: draws the tree as a green circle on the map
    void drawFeature() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawFeature'");
    }
}
