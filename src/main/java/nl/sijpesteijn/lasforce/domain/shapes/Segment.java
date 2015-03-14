package nl.sijpesteijn.lasforce.domain.shapes;

/**
 * @author: Gijs Sijpesteijn
 */
public class Segment {
    private double x;
    private double y;

    public Segment(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
