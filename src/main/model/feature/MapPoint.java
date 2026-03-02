package model.feature;

import java.util.*;

import model.exceptions.InvalidInputException;

// Represents a point on the map with a name
public class MapPoint extends Feature {
    List<FeatureSection> area;

    public MapPoint() {
        super("", 0, 0);
    }

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public MapPoint(String name, int x, int y) {
        super(name, x, y);
    }

    // EFFECTS: displays info
    void showInfo() {

    }

    // EFFECTS: draws point
    void drawFeature() {

    }

    public MapPoint constructThis(String name, int xpos, int ypos, Scanner input) throws InvalidInputException {
        int newX;
        int newY;
        String pointName = "";

        System.out.print("\tx: ");
        newX = input.nextInt();
        System.out.print("\ty: ");
        newY = input.nextInt();

        System.out.println("Name the point (press enter if no name):");
        pointName = input.next();

        return new MapPoint(pointName, newX, newY);
    }

    @Override
    public List<String> getInfo() {
        ArrayList<String> info = new ArrayList<>();

        info.add("x: " + xpos + " y: " + ypos);

        return info;
    }

}