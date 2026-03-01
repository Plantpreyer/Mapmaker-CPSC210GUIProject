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
        String cmd = "";
        int newX;
        int newY;
        String pointName = "";

        newX = input.nextInt();
        newY = input.nextInt();

        System.out.println("Does this point have a name? y/n");
        cmd = input.next().toLowerCase();
        if(cmd.equals("y")) {
            System.out.println("Name the point: ");
            pointName = input.next();
        } else if(!cmd.equals("n")) {
            throw new InvalidInputException();
        }

        return new MapPoint(pointName, newX, newY);
    }

}