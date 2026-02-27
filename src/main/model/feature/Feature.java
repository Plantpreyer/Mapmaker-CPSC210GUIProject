package model.feature;

import java.awt.Color;

// Represents a location on the map with a name, coords
public abstract class Feature {
    protected Color color;
    protected String name;
    protected int xpos;
    protected int ypos;

    public Feature(String name, int x, int y) {
        this.name = name;
        this.xpos = x;
        this.ypos = y;
    }

    // EFFECTS: displays info of feature
    abstract void showInfo();

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

    protected void setName(String name) {
        this.name = name;
    }

    protected void setXpos(int x) {
        this.xpos = x;
    }

    protected void setYpos(int y) {
        this.ypos = y;
    }

    protected void setColor(Color color) {
        this.color = color;
    }

}
