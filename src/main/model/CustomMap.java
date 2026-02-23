package model;

import java.util.*;

import model.feature.Feature;
import model.feature.MapObject;
import model.feature.Marker;

// represents a customiizable map with buildings, roads, trees, routes, and markers
// map has a name
public class CustomMap {
    String name;
    List<MapObject> objects;
    List<Marker> markers;
    Feature selectedFeature;

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

    }
}
