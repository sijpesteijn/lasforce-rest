package nl.sijpesteijn.lasforce.domain.shapes;

/**
 * @author: Gijs Sijpesteijn
 */
public class Matrix {
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double xt;
    private final double yt;

    public Matrix(final double a, final double b, final double c, final double d, final double xt, final double yt) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.xt = xt;
        this.yt = yt;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public double getXt() {
        return xt;
    }

    public double getYt() {
        return yt;
    }
}
