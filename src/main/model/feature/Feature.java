package model.feature;

import java.awt.Color;
import java.util.List;

// Represents a location on the map with a name, coords
public abstract class Feature {
    protected Color color;
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
    abstract void showInfo();

    // EFFECTS: returns list of strings with various information meant to be
    // displayed
    public abstract List<String> getInfo();

    // EFFECTS: draws feature on selected map
    abstract void drawFeature();

    public Color getColor() {
        return color;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public String getName() {
        return name;
    }

    public int getRadius() {
        return radius;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setXpos(int x) {
        this.xpos = x;
    }

    public void setYpos(int y) {
        this.ypos = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRad(int rad) {
        this.radius = rad;
    }

}
