package model.feature;

import java.util.ArrayList;
import java.util.List;

// Represents a tree
// REQUIRES: Can only have one circular section
public class TreeFeature extends MapObject {
    // FeatureSection tree;
    
    public TreeFeature() {
        super("", 0, 0, 0);
        // tree = new FeatureSection(0, 0, 0, 0);
        // addSection(tree);
    }

    public TreeFeature(String name, int x, int y, int radius, int height) {
        super(name, x, y, height);
        // tree = new FeatureSection(0, 0, radius, height);
        // addSection(tree);
    }

    public String getType() {
        return "tree";
    }

    public List<String> getInfo() {
        ArrayList<String> info = getPosList();
        info.add("Height: " + getHeight());
        info.add("Radius: " + getRadius());
        return info;
    }

    // EFFECTS: shows info of tree, including name, height, coords
    void showInfo() {
        
    }

    public int getHeight() {
        return getHeight();
    }

    // MODIFIES: this
    // EFFECTS: replaces body with new one
    public void setBody(int radius, int height) {
        // tree = new FeatureSection(0, 0, radius, height);
        // clearBody();
        // addSection(tree);
        this.radius = radius;
        this.height = height;
    }

    // EFFECTS: draws the tree as a green circle on the map
    void drawFeature() {
        
    }
}
