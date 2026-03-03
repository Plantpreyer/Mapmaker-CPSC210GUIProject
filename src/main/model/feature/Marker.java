package model.feature;

import java.util.*;

import model.exceptions.InvalidInputException;

import java.awt.Color;

// Represents a marker on the map, must have a colour and can have a zone
public class Marker extends MapPoint {
    List<FeatureSection> area;

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public Marker(String name, int x, int y, Color c) {
        super(name, x, y);
        color = c;
    }

    // EFFECTS: displays info, including name, width/height of zone
    void showInfo() {
        
    }

    // EFFECTS: draws marker with zone on map
    void drawFeature() {

    }

    @Override
    public Marker constructThis(String name, int xpos, int ypos, Scanner input) throws InvalidInputException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'constructThis'");
    }

    @Override
    public List<String> getInfo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInfo'");
    }

    
}