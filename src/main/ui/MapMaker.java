package ui;

import java.util.*;

import model.CustomMap;

// Map maker / manager application
// Has a list of map objects that you can select
public class MapMaker {
    List<CustomMap> maps;
    CustomMap selectedMap;


    // EFFECTS: runs application
    public MapMaker() {

    }

    // MODIFIES: this
    // EFFECTS: handles input until quit
    private void runMapMaker() {

    }
    
    // MODIFIES: this
    // EFFECTS: handles operations on selected map object
    private void manageMap(CustomMap map) {

    }

    // REQUIRES: selectedMap != null
    // MODIFIES: this
    // EFFECTS: deletes selected map
    private void deleteMap() {

    }

    // REQUIRES: name not empty
    // MODIFIES: this
    // EFFECTS: adds a new map to list with specified name and selects it
    private void createMap(String name) {

    }


}
