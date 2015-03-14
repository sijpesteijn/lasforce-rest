package nl.sijpesteijn.lasforce.domain.shapes;

/**
 * @author: Gijs Sijpesteijn
 */
public class StrokeColor {
    private double blue;
    private double green;
    private double red;

    public StrokeColor(final double blue, final double green, final double red) {
        this.blue = blue;
        this.green = green;
        this.red = red;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(final int blue) {
        this.blue = blue;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(final int green) {
        this.green = green;
    }

    public double getRed() {
        return red;
    }

    public void setRed(final int red) {
        this.red = red;
    }

    @Override
    public String toString() {
        return "[" + blue + ", " + green + ", " + red + "]";
    }
}
