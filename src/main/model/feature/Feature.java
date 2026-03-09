package model.feature;

// import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import persistence.Writable;

// Represents a location on the map with a name, coords
public abstract class Feature implements Writable {
    // protected Color color;
    protected String name;
    protected int xpos;
    protected int ypos;
    protected int radius;

    public Feature(String name, int x, int y) {
        this.name = name;
        this.xpos = x;
        this.ypos = y;
    }

    // EFFECTS: displays info of feature
    // abstract void showInfo();

    // EFFECTS: returns list of strings with various information meant to be
    // displayed
    public abstract List<String> getInfo();

    // EFFECTS: draws feature on selected map
    // abstract void drawFeature();

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("xpos", xpos);
        json.put("ypos", ypos);
        
        return json;
    }

    public ArrayList<String> getPosList() {
        ArrayList<String> info = new ArrayList<>();
        info.add("Position: " + xpos + ", " + ypos);
        return info;
    }

    // public Color getColor() {
    // return color;
    // }

    // public void setColor(Color color) {
    // this.color = color;
    // }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setXpos(int x) {
        this.xpos = x;
    }

    public void setYpos(int y) {
        this.ypos = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int rad) {
        this.radius = rad;
    }
}
