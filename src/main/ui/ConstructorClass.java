package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.exceptions.InvalidInputException;
import model.feature.Building;
import model.feature.MapPoint;
import model.feature.Route;
import model.feature.TreeFeature;

// Class that runs methods to ask for user input and create features on a map
public class ConstructorClass {

    public ConstructorClass() {

    }

    // EFFECTS: returns a route based on name, xpos, ypos, and additional prompted
    // user input
    public Route constructRoute(String name, int xpos, int ypos, Scanner input) throws InvalidInputException {
        List<MapPoint> newPoints = new ArrayList<MapPoint>();
        String cmd = "";

        System.out.println("First point:");
        newPoints.add(constructPoint("", 0, 0, input));

        System.out.println("Second point:");
        newPoints.add(constructPoint("", 0, 0, input));

        System.out.println("Add another point? y/n");
        cmd = input.next().toLowerCase();
        if (!(cmd.equals("y") | cmd.equals("n"))) {
            throw new InvalidInputException();
        }

        while (!cmd.equals("n")) {
            System.out.println("Next point:");
            newPoints.add(constructPoint("", 0, 0, input));

            System.out.println("Add another point? y/n");
            cmd = input.next().toLowerCase();
            if (!(cmd.equals("y") | cmd.equals("n"))) {
                throw new InvalidInputException();
            }
        }

        return new Route(name, xpos, ypos, newPoints);
    }

    public MapPoint constructPoint(String name, int xpos, int ypos, Scanner input) throws InvalidInputException {
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

    public Building constructBuilding(String name, int xpos, int ypos, int height) {
        return new Building(name, xpos, ypos, height);
    }

    public TreeFeature constructTree(String name, int xpos, int ypos, Scanner input) throws InvalidInputException {
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
