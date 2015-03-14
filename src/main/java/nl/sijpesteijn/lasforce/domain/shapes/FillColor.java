package nl.sijpesteijn.lasforce.domain.shapes;

/**
 * @author: Gijs Sijpesteijn
 */
public class FillColor {
    private final double blue;
    private final double green;
    private final double red;

    public FillColor(final double blue, final double green, final double red) {
        this.blue = blue;
        this.green = green;
        this.red = red;
    }

    public double getBlue() {
        return blue;
    }

    public double getGreen() {
        return green;
    }

    public double getRed() {
        return red;
    }
}
