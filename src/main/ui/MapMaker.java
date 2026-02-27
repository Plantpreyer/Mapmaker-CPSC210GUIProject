package ui;

import java.util.*;

import model.CustomMap;
import model.exceptions.InvalidInputException;

// Map maker / manager application
// Has a list of map objects that you can select
public class MapMaker {
    private List<CustomMap> maps;
    private int selectIndex;
    private CustomMap selectedMap;
    private String cmdString;
    private Scanner input;
    private boolean quit;

    // EFFECTS: runs application
    public MapMaker() {
        selectIndex = 0;
        maps = new ArrayList<>();
        cmdString = "";
        quit = false;
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

            cmdString = input.next();
            cmdString = cmdString.toLowerCase();
            try {
                handleInput(cmdString);
            } catch (InvalidInputException e) {
                System.out.println("Error: couldn't interpret input.");
                continue;
            }
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
    // EFFECTS: handles operations on selected map object
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
            cmdString = input.next();
            cmdString = cmdString.toLowerCase();
            try {
                manageMapAction(cmdString);
            } catch (InvalidInputException e) {
                System.out.println("Error: couldn't interpret input.");
                continue;
            }
        }
    }

    private void manageMapAction(String cmdString) throws InvalidInputException {
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
                quit = true;
                break;
            default:
                throw new InvalidInputException();
        }
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
        cmdString = input.next();
        cmdString = cmdString.toLowerCase();
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

    // EFFECTS: asks for specifications from user, then creates a new Feature in
    // selected map
    private void constructFeature() {
        System.out.println("DO NEW FEATURE!!!");
    }

    // REQUIRES: map != null
    // MODIFIES: this
    // EFFECTS: selects map
    void selectMap(CustomMap map) {
        selectedMap = map;
        selectIndex = maps.indexOf(map);
    }
}
