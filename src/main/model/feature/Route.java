package model.feature;

import java.util.*;

import model.exceptions.InvalidInputException;

// Represents a route on the map with an ordered list of points
public class Route extends Feature {
    List<MapPoint> points;

    public Route() {
        super("", 0, 0);
        points = new ArrayList<>();
    }

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public Route(String name, int x, int y) {
        super(name, x, y);
        points = new ArrayList<>();
    }

    // REQUIREMENTS: x > 0, y > 0, name not empty
    public Route(String name, int x, int y, List<MapPoint> points) {
        super(name, x, y);
        this.points = points;
    }

    // EFFECTS: displays info of route
    void showInfo() {

    }

    // EFFECTS: draws route on map
    void drawFeature() {

    }

    // EFFECTS: returns a route based on name, xpos, ypos, and additional prompted
    // user input
    public Route constructThis(String name, int xpos, int ypos, Scanner input) throws InvalidInputException {
        List<MapPoint> newPoints = new ArrayList<MapPoint>();
        String cmd = "";
        MapPoint newPoint = new MapPoint();

        System.out.println("Type x and y of first point, like this: \"x y\"");
        newPoints.add(newPoint.constructThis("", 0, 0, input));

        System.out.println("Type x and y of second point, like this: \"x y\"");
        newPoints.add(newPoint.constructThis("", 0, 0, input));

        System.out.println("Add another point? y/n");
        cmd = input.next().toLowerCase();
        if (!(cmd.equals("y") | cmd.equals("n"))) {
            throw new InvalidInputException();
        }

        while (!cmd.equals("n")) {
            System.out.println("Type x and y of next point, like this: \"x y\"");
            newPoints.add(newPoint.constructThis("", 0, 0, input));

            System.out.println("Add another point? y/n");
            cmd = input.next().toLowerCase();
            if (!(cmd.equals("y") | cmd.equals("n"))) {
                throw new InvalidInputException();
            }
        }

        return new Route(name, xpos, ypos, newPoints);
    }
}