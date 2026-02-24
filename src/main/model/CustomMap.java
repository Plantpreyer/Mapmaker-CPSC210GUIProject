package model;

import java.util.*;

import model.feature.Building;
import model.feature.Feature;
import model.feature.MapObject;
import model.feature.Marker;
import model.feature.TreeFeature;

// represents a customiizable map with buildings, roads, trees, routes, and markers
// map has a name
public class CustomMap {
    protected String name;
    protected List<MapObject> objects;
    protected List<Marker> markers;
    protected Feature selectedFeature;

    private final String objectCodeBuilding = "BUIL";
    private final String objectCodeTree = "TREE";

    // REQUIRES: name not empty
    // MODIFIES: this
    // EFFECTS: constructor
    public CustomMap(String name) {
        this.name = name;
        this.objects = new ArrayList<>();
        this.markers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: selects passed feature
    public void selectFeature(Feature f) {
        selectedFeature = f;
    }

    // MODIFIES: this
    // EFFECTS: selects mapObject at index
    public void selectObject(int ind) {
        selectFeature(objects.get(ind));
    }

    // MODIFIES: this
    // EFFECTS: selects mapObject at index
    public void selectMarker(int ind) {
        selectFeature(markers.get(ind));
    }

    // MODIFIES: this
    // EFFECTS: adds an object to list of objects, if objType not valid throws Exception
    public void addObject(String name, int x, int y, String objType) throws Exception {
        MapObject newObj;
        switch (objType) {
            case objectCodeBuilding:
                newObj = new Building(name, x, y);
                break;
        
            case objectCodeTree:
                newObj = new TreeFeature(name, x, y, 0, 0);
                break;
            default:
                throw new Exception();
        }
        objects.add(newObj);
    }

    public MapObject getObject(int index) {
        return objects.get(index);
    }

    public Feature getSelectedFeature() {
        return selectedFeature;
    }
}
