package model.feature;

import org.json.JSONObject;

// Represents a category of features with dimensions and height
public abstract class MapObject extends Feature {
    // private List<FeatureSection> body;
    protected int height;
    protected int width;
    protected int length;

    public MapObject(String name, int x, int y, int height) {
        super(name, x, y);
        this.height = height;
        // body = new ArrayList<>();
    }

    // EFFECTS: draws all sections in body on map
    // abstract void drawFeature();

    // EFFECTS: returns a string representing the type of object
    public abstract String getType();

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("height", height);

        return json;
    }

    // // EFFECTS: returns list of strings with various information meant to be
    // // displayed
    // public abstract List<String> getInfo();

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getXdim() {
        return width;
    }

    public void setXdim(int width) {
        this.width = width;
    }

    public int getYdim() {
        return length;
    }

    public void setYdim(int length) {
        this.length = length;
    }
}
