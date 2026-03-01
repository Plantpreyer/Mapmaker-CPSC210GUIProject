package model.feature;

import java.util.*;

import model.exceptions.InvalidInputException;

import java.awt.Color;

// Represents a route on the map with an ordered list of points
public class Route extends Feature {
    List<Feature> points;

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public Route(String name, int x, int y) {
        super(name, x, y);

    }

    // EFFECTS: displays info, including name, width/height of zone
    void showInfo() {
        
    }

    // EFFECTS: draws route on map
    void drawFeature() {

    }

    @Override
    public Feature constructThis(String name, int xpos, int ypos, Scanner input) throws InvalidInputException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'constructThis'");
    }
}