package ui;

import java.util.*;

import model.CustomMap;
import model.exceptions.InvalidInputException;
import model.feature.MapObject;

// Map maker / manager application
// Has a list of map objects that you can select
public class MapMaker {
    private List<CustomMap> maps;
    private int selectIndex;
    private CustomMap selectedMap;
    private String cmdString;
    private Scanner input;
    private boolean quit;
    private int spamCount;
    private ConstructorClass cons;

    // EFFECTS: runs application
    public MapMaker() {
        spamCount = 0;
        selectIndex = 0;
        maps = new ArrayList<>();
        cmdString = "";
        quit = false;
        cons = new ConstructorClass();

        runMapMaker();
    }

    // MODIFIES: this
    // EFFECTS: handles input until quit
    void runMapMaker() {
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");

        while (!quit) {
            if (!maps.isEmpty()) {
                selectedMap = maps.get(selectIndex);
            }
            displayMenu();

            cmdString = takeInput();
            try {
                handleInput(cmdString);
            } catch (InvalidInputException e) {
                System.out.println("Error: couldn't interpret input.");
                quit = incrementSpam();
                continue;
            }
            spamCount = 0;
        }

        System.out.println("Quitting application...");
    }

    // EFFECTS: displays menu of options to user, with information on stored maps
    // and selected map
    private void displayMenu() {
        System.out.println("\nMAIN MENU");
        if (maps.isEmpty()) {
            System.out.println("No maps! Make a new one?");
        } else {
            printSelectInfo();
        }

        System.out.println("\nSelect from:");
        System.out.println("\tn -> New Map");
        if (canCycleSelect()) {
            System.out.println("\ta -> Previous Map Slot");
            System.out.println("\td -> Next Map Slot");
        }
        if (!maps.isEmpty()) {
            System.out.println("\tm -> Manage Map");
            System.out.println("\tf -> Map Info");
        }
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: calls various methods depending on input string
    private void handleInput(String cmdString) throws InvalidInputException {
        switch (cmdString) {
            case "q":
                quit = true;
                break;
            case "n":
                System.out.print("Enter name: ");
                String newMapName = input.next();
                createMap(newMapName);
            case "m":
                manageMap(selectedMap);
                break;
            case "f":
                printMapInfo();
                break;
            case "a":
                cycleBackward();
                break;
            case "d":
                cycleForward();
                break;
            default:
                throw new InvalidInputException();
        }
    }

    // REQUIRES: name not empty
    // MODIFIES: this
    // EFFECTS: adds a new map to list with specified name and selects it
    void createMap(String name) {
        CustomMap newMap = new CustomMap(name);
        maps.add(newMap);
        selectMap(newMap);
    }

    // EFFECTS: prints info on stored maps and selected map
    private void printSelectInfo() {
        System.out.println("# of maps: " + maps.size());
        System.out.print("Selected map: [" + (selectIndex + 1) + "] ");
        System.out.println("\'" + selectedMap.getName() + "\'");
    }

    // EFFECTS: returns true if maps.size() > 1
    private boolean canCycleSelect() {
        return maps.size() > 1;
    }

    // REQUIRES: maps.size() > 1
    // MODIFIES: this
    // EFFECTS: cycles selected map either forwards or backwards, wraps the list if
    // at end
    private void cycleForward() throws InvalidInputException {
        if (!canCycleSelect()) {
            throw new InvalidInputException();
        }
        selectIndex++;
        selectIndex = selectIndex % maps.size();
        selectedMap = maps.get(selectIndex);
    }

    private void cycleBackward() throws InvalidInputException {
        if (!canCycleSelect()) {
            throw new InvalidInputException();
        }
        selectIndex--;
        selectIndex += maps.size();
        selectIndex = selectIndex % maps.size();
        selectedMap = maps.get(selectIndex);
    }

    // MODIFIES: this
    // EFFECTS: handles operations on selected map object, if no map selected throws
    // expception
    void manageMap(CustomMap map) throws InvalidInputException {
        if (map == null) {
            throw new InvalidInputException();
        }

        boolean quit = false;

        while (!quit) {
            System.out.println(); // new line
            printSelectInfo();
            printMapInfo();
            System.out.println("Editing map.");

            printManageMenu();

            String cmdString;
            cmdString = takeInput();
            try {
                if (manageMapAction(cmdString)) {
                    quit = true;
                }
            } catch (InvalidInputException e) {
                System.out.println("Error: couldn't interpret input.");
                quit = incrementSpam();
                continue;
            }
            spamCount = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method for manageMap and runMapMaker; increases spam count by
    // 1 and checks if it is >3
    private boolean incrementSpam() {
        spamCount++;
        return checkSpam();
    }

    // MODIFIES: this
    // EFFECTS: performs certain functionalities based on passed string, returns
    // true if quit and false otherwise
    private boolean manageMapAction(String cmdString) throws InvalidInputException {
        switch (cmdString) {
            case "n":
                constructFeature();
                break;
            case "e":
                System.out.println("Sorry, we haven't made this feature yet.");
                break;
            case "d":
                if (!deleteMap()) {
                    break;
                }
            case "q":
                return true;
            default:
                throw new InvalidInputException();
        }
        return false;
    }

    // EFFECTS: prints menu for managing map
    private void printManageMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tn -> New Feature!");
        System.out.println("\te -> Edit Feature... (not implemented)");
        System.out.println("\td -> Delete Map.");
        System.out.println("\tq -> Back to menu!");
    }

    // REQUIRES: selectedMap != null
    // EFFECTS: print selected map info
    private void printMapInfo() throws InvalidInputException {
        if (selectedMap == null) {
            throw new InvalidInputException();
        }

        List<String> mapInfo = selectedMap.mapInfo();
        for (String b : mapInfo) {
            System.out.println(b);
        }
    }

    // REQUIRES: selectedMap != null
    // MODIFIES: this
    // EFFECTS: deletes selected map
    private boolean deleteMap() throws InvalidInputException {
        System.out.println("!!!!! Are you sure? y/n");
        cmdString = takeInput();
        if (cmdString.equals("y")) {
            System.out.println("WE ARE DELETING YOUR MAP...");
            maps.remove(selectIndex);
            selectIndex = 0;
            selectedMap = null;
            return true;
        } else if (cmdString.equals("n")) {
            System.out.println("Cancelled");
            return false;
        } else {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks whether user wants to add a marker or object to the map, then
    // does that
    private void constructFeature() throws InvalidInputException {
        System.out.println("\tMarker (we haven't implemented marker yet): m");
        System.out.println("\tRoute: r");
        System.out.println("\tObject: o");

        String choice = takeInput();
        switch (choice) {
            case "m":
                System.out.println("Sorry, we haven't programmed this part yet!");
                break;
            case "r":
                constructRoute();
                break;
            case "o":
                constructObject();
                break;
            default:
                throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for specifications from user, then creates a new object in
    // selected map
    private void constructObject() throws InvalidInputException {
        try {
            String type = takeTypeObject();

            System.out.print("x: ");
            int newObjX = input.nextInt();
            System.out.print("y: ");
            int newObjY = input.nextInt();
            System.out.print("name: ");
            String newObjName = input.next();

            MapObject newObj = convertObjType(type, newObjX, newObjY, newObjName);

            selectedMap.addObject(newObj);
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for specifications from user, then creates a new route in
    // selected map
    private void constructRoute() throws InvalidInputException {
        // try {
            System.out.print("name: ");
            String newName = input.next();

            selectedMap.addRoute(cons.constructRoute(newName, 0, 0, input));
        // } catch (Exception e) {
        //     throw new InvalidInputException();
        // }
    }

    // EFFECTS: asks user for a string representing type of object and returns it
    private String takeTypeObject() throws InvalidInputException {
        System.out.println("Type of object: (\"buil\" or \"tree\")");
        String type = takeInput();
        if (!checkValidType(type)) {
            throw new InvalidInputException();
        }
        return type;
    }

    // EFFECTS: returns a subclass of mapobject depending on string passed
    private MapObject convertObjType(String type, int newObjX, int newObjY, String newObjName)
            throws InvalidInputException {
        MapObject newObj;

        switch (type) {
            case CustomMap.objectCodeBuilding:
                newObj = cons.constructBuilding(newObjName, newObjX, newObjY);
                break;
            case CustomMap.objectCodeTree:
                newObj = cons.constructTree(newObjName, newObjX, newObjY, input);
                break;
            default:
                throw new InvalidInputException();
        }

        return newObj;
    }

    // EFFECTS: returns false if type not a valid type of object
    private boolean checkValidType(String type) {
        switch (type) {
            case CustomMap.objectCodeBuilding:
                break;
            case CustomMap.objectCodeTree:
                break;
            default:
                return false;
        }
        return true;
    }

    // REQUIRES: map != null
    // MODIFIES: this
    // EFFECTS: selects map
    void selectMap(CustomMap map) {
        selectedMap = map;
        selectIndex = maps.indexOf(map);
    }

    // EFFECTS: returns a lowercase string of the next user input
    String takeInput() {
        String str = input.next();
        str = str.toLowerCase();
        return str;
    }

    // EFFECTS: returns true if user has entered a wrong input 3 times in a row in a
    // looping menu
    boolean checkSpam() {
        return spamCount >= 3;
    }
}
