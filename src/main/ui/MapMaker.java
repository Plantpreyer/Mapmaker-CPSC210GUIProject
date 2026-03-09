package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.json.JSONException;

import model.CustomMap;
import model.exceptions.InvalidInputException;
import model.exceptions.ObjectClassificationException;
import model.feature.MapObject;
import model.feature.Route;
import persistence.JsonReader;
import persistence.JsonWriter;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Map maker / manager application
// Has a list of map objects that you can select
@ExcludeFromJacocoGeneratedReport
public class MapMaker {
    private List<CustomMap> maps;
    private int selectIndex;
    private CustomMap selectedMap;
    private String cmdString;
    private Scanner input;
    private boolean quit;
    private int spamCount;
    private ConstructorClass cons;

    private static final String JSON_LOCATION = "./data/MapData.json";

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
        System.out.println("\tl -> Load Saved Maps");
        if (canCycleSelect()) {
            System.out.println("\ta -> Previous Map Slot");
            System.out.println("\td -> Next Map Slot");
        }
        if (!maps.isEmpty()) {
            System.out.println("\tm -> Manage Map");
            System.out.println("\tf -> Map Info");
            System.out.println("\ts -> Save maps");
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
            default:
                handleInputPt2(cmdString);
        }
    }

    // MODIFIES: this
    // EFFECTS: see above method
    private void handleInputPt2(String cmdStr) throws InvalidInputException {
        switch (cmdStr) {
            case "s":
                saveMapsState();
                break;
            case "l":
                loadMapsState();
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

    // MODIFIES, EFFECTS: see above
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
                editFeature();
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
        System.out.println("\te -> Edit Feature");
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
    // EFFECTS: asks which feature user wants to edit and does that
    private void editFeature() throws InvalidInputException {
        System.out.println("\tMarker (we haven't implemented marker yet): m");
        System.out.println("\tRoute: r");
        System.out.println("\tObject: o");

        String choice = takeInput();
        switch (choice) {
            case "m":
                System.out.println("Sorry, we haven't programmed this part yet!");
                break;
            case "r":
                editRoute();
                break;
            case "o":
                editObject();
                break;
            default:
                throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for input and then modifies selected route
    private void editRoute() throws InvalidInputException {
        if (selectedMap.getRoutes().isEmpty()) {
            System.out.println("No routes!");
            return;
        }
        System.out.println("Which route?");

        cmdString = takeInput();
        Route selectRoute = selectedMap.findRoute(cmdString);

        System.out.println("Delete? (y/n)");

        if (takeYesNo()) {
            selectedMap.deleteRoute(selectRoute);
            return;
        }

        try {
            System.out.print("name: ");
            String newName = input.next();

            Route newRoute = cons.constructRoute(newName, 0, 0, input);
            selectedMap.editRoute(selectRoute, newRoute);
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for input and then modifies selected route
    private void editObject() throws InvalidInputException {
        if (selectedMap.getObjects().isEmpty()) {
            System.out.println("No objects!");
            return;
        }
        System.out.println("Which object?");

        cmdString = takeInput();
        MapObject selectObject = selectedMap.findObject(cmdString);

        try {
            handleEditObject(selectObject);
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // EFFECTS: prints a menu for when user is editing an object
    private void printEditObjectMenu() {
        System.out.println("Edit name (n)");
        System.out.println("Edit position (a)");
        System.out.println("Edit dimensions (s)");
        System.out.println("Edit height (f)");
        System.out.println("Delete (d)");
    }

    // MODIFIES: this
    // EFFECTS: asks user for input and will do different edits depending on input
    // to selectObject
    private void handleEditObject(MapObject selectObject) throws InvalidInputException, ObjectClassificationException {
        printEditObjectMenu();
        cmdString = takeInput();

        switch (cmdString) {
            case "n":
                System.out.println("name: ");
                cmdString = input.next();
                selectObject.setName(cmdString);
                break;
            case "a":
                editObjectCoords(selectObject);
                break;
            case "s":
                editObjectDimensions(selectObject);
                break;
            case "f":
                editObjectHeight(selectObject);
                break;
            case "d":
                selectedMap.deleteObject(selectObject);
                break;
            default:
                throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user for input and edits selectObject
    private void editObjectCoords(MapObject selectObject)
            throws InvalidInputException {
        try {
            System.out.println("x: ");
            selectObject.setXpos(Integer.parseInt(input.next()));
            System.out.println("y: ");
            selectObject.setYpos(Integer.parseInt(input.next()));
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for input and appropriately changes passed object
    private void editObjectDimensions(MapObject selectObject)
            throws ObjectClassificationException, InvalidInputException {
        try {
            switch (selectObject.getType()) {
                case "building":
                    System.out.println("width (x): ");
                    selectObject.setXdim(Integer.parseInt(input.next()));
                    System.out.println("length (y): ");
                    selectObject.setYdim(Integer.parseInt(input.next()));
                    break;
                case "tree":
                    System.out.println("radius: ");
                    selectObject.setRadius(Integer.parseInt(input.next()));
                    break;
                default:
                    throw new ObjectClassificationException();
            }
        } catch (ObjectClassificationException e) {
            System.out.println("Couldn't find object type.");
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets object height to user input
    private void editObjectHeight(MapObject selectObject)
            throws InvalidInputException {
        try {
            System.out.println("height: ");
            selectObject.setHeight(Integer.parseInt(input.next()));
        } catch (Exception e) {
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
            System.out.println("height: ");
            int newObjHeight = input.nextInt();

            MapObject newObj = convertObjType(type, newObjX, newObjY, newObjName, newObjHeight);

            selectedMap.addObject(newObj);
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for specifications from user, then creates a new route in
    // selected map
    private void constructRoute() throws InvalidInputException {
        try {
            System.out.print("name: ");
            String newName = input.next();

            selectedMap.addRoute(cons.constructRoute(newName, 0, 0, input));
        } catch (Exception e) {
            throw new InvalidInputException();
        }
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

    // EFFECTS: writes map information to file
    private void saveMapsState() throws InvalidInputException {
        if (maps.isEmpty()) {
            throw new InvalidInputException();
        }

        System.out.println("Are you sure you want to save the current maps to the file? (y/n)");
        if (!takeYesNo()) {
            System.out.println("Cancelled.");
            return;
        }

        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_LOCATION);
            jsonWriter.open();
            jsonWriter.write(maps);
            jsonWriter.close();
            System.out.println("Saved maps to " + JSON_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_LOCATION);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads map information from file
    private void loadMapsState() throws InvalidInputException {
        System.out.println("Are you sure you want to load the saved maps from file, replacing the current maps? (y/n)");
        if (!takeYesNo()) {
            System.out.println("Cancelled.");
            return;
        }

        try {
            JsonReader jsonReader = new JsonReader(JSON_LOCATION);
            maps = jsonReader.read();
            System.out.println("Loaded maps from " + JSON_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_LOCATION);
        } catch (ObjectClassificationException e) {
            System.out.println("Unable to determine object type");
        } catch (JSONException e) {
            try {
                JsonWriter jsonWriter = new JsonWriter(JSON_LOCATION);
                jsonWriter.open();
                jsonWriter.write(new ArrayList<>());
                jsonWriter.close();
            } catch (FileNotFoundException e2) {
                System.out.println("Unable to write to file: " + JSON_LOCATION);
            }

        }
    }

    // EFFECTS: returns a subclass of mapobject depending on string passed
    private MapObject convertObjType(String type, int newObjX, int newObjY, String newObjName, int newObjHeight)
            throws InvalidInputException {
        MapObject newObj;

        switch (type) {
            case CustomMap.objectCodeBuilding:
                newObj = cons.constructBuilding(newObjName, newObjX, newObjY, newObjHeight, input);
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

    // EFFECTS: returns true if input is y, false if input is n, else throws
    // exception
    boolean takeYesNo() throws InvalidInputException {
        cmdString = takeInput();
        switch (cmdString) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                throw new InvalidInputException();
        }
    }
}
