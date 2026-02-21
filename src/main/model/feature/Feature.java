package model.feature;

import java.awt.Color;


// Represents a location on the map with a name, coords
public abstract class Feature {
    protected Color color;
    protected String name;
    protected int x;
    protected int y;


    public Feature(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    // EFFECTS: draws feature on selected map
    abstract void drawFeature();

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
    
    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setColor(Color color) {
        this.color = color;
    }

}
