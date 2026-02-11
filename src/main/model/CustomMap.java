package model;


// represents a customiizable map with buildings, roads, trees, routes, and markers
// map has a name
public class CustomMap {
    String name;

    // REQUIRES: name not empty
    // MODIFIES: this
    // EFFECTS: constructor
    public CustomMap(String name) {
        this.name = name;
    }

}
