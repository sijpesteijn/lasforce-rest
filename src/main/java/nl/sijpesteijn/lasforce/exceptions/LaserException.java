package nl.sijpesteijn.lasforce.exceptions;

/**
 * @author: Gijs Sijpesteijn
 */
public class LaserException extends Exception {
    public LaserException(final Exception e) {
        super(e);
    }

    public LaserException(String s, Exception e) {
        super(s, e);
    }
}
