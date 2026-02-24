package model.feature;


// Represents a tree
// REQUIRES: Can only have one circular section
public class TreeFeature extends MapObject {
    FeatureSection tree;

    

    public TreeFeature(String name, int x, int y, int radius, int height) {
        super(name, x, y);
        tree = new FeatureSection(0, 0, radius, height);
        addSection(tree);
    }

    // EFFECTS: shows info of tree, including name, height, coords
    void showInfo() {

    }

    // MODIFIES: this
    // EFFECTS: replaces body with new one
    void setBody(int radius, int height) {
        tree = new FeatureSection(0, 0, radius, height);
        clearBody();
        addSection(tree);
    }

    // EFFECTS: draws the tree as a green circle on the map
    void drawFeature() {
        
    }
}
