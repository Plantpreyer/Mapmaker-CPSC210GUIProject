package model;

import java.util.*;

import model.feature.Building;
import model.feature.Feature;
import model.feature.MapObject;
import model.feature.Marker;
import model.feature.TreeFeature;

// represents a customizable map with buildings, roads, trees, routes, and markers
// map has a name
public class CustomMap {
    protected String name;
    protected List<MapObject> objects;
    protected List<Marker> markers;
    protected Feature selectedFeature;

    public static final String objectCodeBuilding = "buil";
    public static final String objectCodeTree = "tree";

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
    // EFFECTS: adds an object to list of objects
    public void addObject(String name, int x, int y) {
        MapObject newObj = new Building(name, x, y);
        objects.add(newObj);
    }

    // MODIFIES: this
    // EFFECTS: adds an object to list of objects
    public void addObject(String name, int x, int y, int height, int radius) {
        MapObject newObj = new TreeFeature(name, x, y, radius, height);
        objects.add(newObj);
    }

    // MODIFIES: this
    // EFFECTS: adds an object to list of objects
    public void addObject(MapObject mapObject) {
        objects.add(mapObject);
    }

    // EFFECTS: returns list of strings of info about this map, excluding name
    public List<String> mapInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add("Objects: " + objects.size());
        List<String> oinfo = objectsInfo();
        for (String b : oinfo) {
            info.add(b);
        }

        info.add("Markers: " + markers.size());
        List<String> minfo = markersInfo();
        for (String b : minfo) {
            info.add(b);
        }

        return info;
    }

    // EFFECTS: returns a list of string of info about objects and markers in map,
    // respectively
    private List<String> objectsInfo() {
        ArrayList<String> info = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            MapObject tempObj = objects.get(i);
            info.add("" + (i + 1) + ": \'" + tempObj.getName() + "\', Type: " + tempObj.getType());

            List<String> tempInfo = tempObj.getInfo();
            for (String b : tempInfo) {
                info.add("\t" + b);
            }
        }
        return info;
    }

    private List<String> markersInfo() {
        ArrayList<String> info = new ArrayList<>();
        return info; // stub
    }

    public MapObject getObject(int index) {
        return objects.get(index);
    }

    public Feature getSelectedFeature() {
        return selectedFeature;
    }

    public String getName() {
        return name;
    }
}
