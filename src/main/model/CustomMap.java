package model;

import java.util.*;

import model.feature.MapObject;
import model.feature.Marker;

// represents a customiizable map with buildings, roads, trees, routes, and markers
// map has a name
public class CustomMap {
    String name;
    List<MapObject> objects;
    List<Marker> markers;

    // REQUIRES: name not empty
    // MODIFIES: this
    // EFFECTS: constructor
    public CustomMap(String name) {
        this.name = name;
        this.objects = new ArrayList<>();
        this.markers = new ArrayList<>();
    }

}
