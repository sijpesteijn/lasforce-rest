package nl.sijpesteijn.lasforce.domain.shapes;

import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class Path implements Child {
    private String type = "Path";
    private boolean applyMatrix;
    private int strokeWidth;
    private StrokeColor strokeColor;
    private List<List<Segment>> segments;
    private boolean closed;

    public boolean isApplyMatrix() {
        return applyMatrix;
    }

    public void setApplyMatrix(final boolean applyMatrix) {
        this.applyMatrix = applyMatrix;
    }

    public void setClosed(final boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(final int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public StrokeColor getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(final StrokeColor strokeColor) {
        this.strokeColor = strokeColor;
    }

    public List<List<Segment>> getSegments() {
        return segments;
    }

    public void setSegments(final List<List<Segment>> segments) {
        this.segments = segments;
    }

    public String getType() {
        return type;
    }
}
