package ui;

import java.util.*;

import model.CustomMap;

// Map maker / manager application
// Has a list of map objects that you can select
public class MapMaker {
    private List<CustomMap> maps;
    private CustomMap selectedMap;


    // EFFECTS: runs application
    public MapMaker() {

    }

    // MODIFIES: this
    // EFFECTS: handles input until quit
    void runMapMaker() {

    }
    
    // MODIFIES: this
    // EFFECTS: handles operations on selected map object
    void manageMap(CustomMap map) {

    }

    // REQUIRES: selectedMap != null
    // MODIFIES: this
    // EFFECTS: deletes selected map
    void deleteMap() {

    }

    // REQUIRES: name not empty
    // MODIFIES: this
    // EFFECTS: adds a new map to list with specified name and selects it
    void createMap(String name) {

    }


}
