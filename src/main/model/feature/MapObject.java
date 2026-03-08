package model.feature;

// Represents a category of features with dimensions and height
public abstract class MapObject extends Feature {
    // private List<FeatureSection> body;
    protected int height;
    protected int xDim;
    protected int yDim;

    public MapObject(String name, int x, int y, int height) {
        super(name, x, y);
        this.height = height;
        // body = new ArrayList<>();
    }

    // EFFECTS: draws all sections in body on map
    // abstract void drawFeature();

    // EFFECTS: returns a string representing the type of object
    public abstract String getType();

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
        return xDim;
    }

    public void setXdim(int xDim) {
        this.xDim = xDim;
    }

    public int getYdim() {
        return yDim;
    }

    public void setYdim(int yDim) {
        this.yDim = yDim;
    }
}
