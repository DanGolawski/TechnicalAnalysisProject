package models;

public class XYobject {

    private String x;

    private float y;

    public XYobject(String x, float y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
