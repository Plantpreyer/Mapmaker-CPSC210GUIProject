package model.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.exceptions.InvalidInputException;

// Represents a tree
// REQUIRES: Can only have one circular section
public class TreeFeature extends MapObject {
    FeatureSection tree;
    
    public TreeFeature() {
        super("", 0, 0);
        tree = new FeatureSection(0, 0, 0, 0);
        addSection(tree);
    }

    public TreeFeature(String name, int x, int y, int radius, int height) {
        super(name, x, y);
        tree = new FeatureSection(0, 0, radius, height);
        addSection(tree);
    }

    public String getType() {
        return "tree";
    }

    public List<String> getInfo() {
        ArrayList<String> info = getPosList();
        info.add("Height: " + getMaxHeight());
        info.add("Radius: " + tree.getRadius());
        return info;
    }

    // EFFECTS: shows info of tree, including name, height, coords
    void showInfo() {
        
    }

    public int getMaxHeight() {
        return tree.getHeight();
    }

    // MODIFIES: this
    // EFFECTS: replaces body with new one
    public void setBody(int radius, int height) {
        tree = new FeatureSection(0, 0, radius, height);
        clearBody();
        addSection(tree);
    }

    // EFFECTS: draws the tree as a green circle on the map
    void drawFeature() {
        
    }

    public TreeFeature constructThis(String name, int xpos, int ypos, Scanner input) throws InvalidInputException {
        int newRad;
        int newHeight;

        try {
            System.out.print("radius of tree: ");
            newRad = input.nextInt();
            System.out.print("height of tree: ");
            newHeight = input.nextInt();
        } catch (Exception e) {
            throw new InvalidInputException();
        }

        return new TreeFeature(name, xpos, ypos, newRad, newHeight);
    }
}
