package model;

import java.util.*;

import model.exceptions.InvalidInputException;
import model.feature.Building;
import model.feature.Feature;
import model.feature.MapObject;
import model.feature.MapPoint;
import model.feature.Marker;
import model.feature.Route;
import model.feature.TreeFeature;

// REQUIRES: viewTopLeft not higher or to the left of 0,0
//           viewBotRight not higher or to the right of 192, 192
// represents a customizable map with buildings, roads, trees, routes, and markers
// map has a name, and two points representing the view user has on the map
public class CustomMap {
    protected String name;
    protected List<MapObject> objects;
    protected List<Marker> markers;
    protected List<Route> routes;
    protected Feature selectedFeature;
    private MapPoint viewTopLeft;
    private MapPoint viewBotRight;
    private int viewSize;
    private MapPoint viewCenter;

    public static final String objectCodeBuilding = "buil";
    public static final String objectCodeTree = "tree";

    private static final int MAP_MAX_SIZE = 192;
    private static final int SHIFT_SIZE = MAP_MAX_SIZE / 4;

    // REQUIRES: name not empty
    // MODIFIES: this
    // EFFECTS: constructor
    public CustomMap(String name) {
        this.name = name;
        this.objects = new ArrayList<>();
        this.markers = new ArrayList<>();
        this.routes = new ArrayList<>();
        viewTopLeft = new MapPoint("", 0, 0);
        viewBotRight = new MapPoint("", 192, 192);
        viewSize = MAP_MAX_SIZE;
        viewCenter = new MapPoint("", MAP_MAX_SIZE / 2, MAP_MAX_SIZE / 2);
    }

    // REQUIRES: direction == "left", "right", "up", "down"
    // MODIFIES: this
    // EFFECTS: moves center SHIFT_SIZE in direction, unless it's already at
    // boundary
    public void shiftView(String direction) {
        switch (direction) {
            case "left":
                viewCenter.setXpos(Math.min(0, viewCenter.getXpos() - SHIFT_SIZE));
                break;
            case "right":
                viewCenter.setXpos(Math.max(MAP_MAX_SIZE, viewCenter.getXpos() + SHIFT_SIZE));
                break;
            case "up":
                viewCenter.setXpos(Math.min(0, viewCenter.getYpos() - SHIFT_SIZE));
                break;
            case "down":
                viewCenter.setXpos(Math.min(MAP_MAX_SIZE, viewCenter.getXpos() + SHIFT_SIZE));
                break;
            default:
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: zooms in, unless zoomed in to max
    public void zoomIn() {
        viewSize = Math.max(viewSize - SHIFT_SIZE, SHIFT_SIZE);
        updateView();
    }

    // MODIFIES: this
    // EFFECTS: zooms out, unless zoomed out to max
    public void zoomOut() {
        viewSize = Math.min(viewSize + SHIFT_SIZE, MAP_MAX_SIZE);
        updateView();
    }

    // MODIFIES: this
    // EFFECTS: updates topleft and botright points to match viewsize and viewcenter
    public void updateView() {
        viewTopLeft.setXpos(viewCenter.getXpos() - viewSize / 2);
        viewTopLeft.setYpos(viewCenter.getYpos() - viewSize / 2);

        viewBotRight.setXpos(viewCenter.getXpos() + viewSize / 2);
        viewBotRight.setYpos(viewCenter.getYpos() + viewSize / 2);
    }

    // MODIFIES: this
    // EFFECTS: selects passed feature
    public void selectFeature(Feature f) {
        selectedFeature = f;
    }

    // EFFECTS: returns first route with name in routes
    public Route findRoute(String name) throws InvalidInputException {
        for (Route b : routes) {
            if (name.equals(b.getName().toLowerCase())) {
                return b;
            }
        }
        throw new InvalidInputException();
    }

    // MODIFIES: this
    // EFFECTS: replaces route with a new one
    public void editRoute(Route selectRoute, Route newRoute) {
        selectRoute = newRoute;
    }

    // EFFECTS: returns first route with name in routes
    public MapObject findObject(String name) throws InvalidInputException {
        for (MapObject b : objects) {
            if (name.equals(b.getName().toLowerCase())) {
                return b;
            }
        }
        throw new InvalidInputException();
    }

    // MODIFIES: this
    // EFFECTS: replaces route with a new one
    public void editObject(Route selectRoute, Route newRoute) {

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
    public void addObject(String name, int x, int y, int height) {
        MapObject newObj = new Building(name, x, y, height);
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

    // MODIFIES: this
    // EFFECTS: deletes object
    public void deleteObject(MapObject mapObject) {
        objects.remove(mapObject);
    }

    // MODIFIES: this
    // EFFECTS: adds a route to list of routes
    // REQUIRES: route != null
    public void addRoute(Route route) {
        routes.add(route);
    }

    // MODIFIES: this
    // EFFECTS: deletes object
    public void deleteRoute(Route route) {
        routes.remove(route);
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

        info.add("Routes: " + routes.size());
        List<String> rinfo = routesInfo();
        for (String b : rinfo) {
            info.add(b);
        }

        return info;
    }

    // EFFECTS: returns a list of string of info about objects, routes, and markers
    // in map,
    // respectively
    public List<String> objectsInfo() {
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

    public List<String> routesInfo() {
        ArrayList<String> info = new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            Route tempRoute = routes.get(i);
            info.add("" + (i + 1) + ": \'" + tempRoute.getName() + "\'");

            List<String> tempInfo = tempRoute.getInfo();
            for (String b : tempInfo) {
                info.add("\t" + b);
            }
        }
        return info;
    }

    public List<String> markersInfo() {
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

    public List<Route> getRoutes() {
        return routes;
    }

    public List<MapObject> getObjects() {
        return objects;
    }
}
