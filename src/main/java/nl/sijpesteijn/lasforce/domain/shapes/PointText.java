package nl.sijpesteijn.lasforce.domain.shapes;

/**
 * @author: Gijs Sijpesteijn
 */
public class PointText implements Child {
    private String type = "PointText";
    private boolean applyMatrix;
    private Matrix matrix;
    private String content;
    private FillColor fillColor;
    private String fontFamily;
    private String fontWeight;
    private int fontSize;
    private String font;
    private int leading;

    public void setApplyMatrix(final boolean applyMatrix) {
        this.applyMatrix = applyMatrix;
    }

    public boolean isApplyMatrix() {
        return applyMatrix;
    }

    public void setMatrix(final Matrix matrix) {
        this.matrix = matrix;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setFillColor(final FillColor fillColor) {
        this.fillColor = fillColor;
    }

    public void setFontFamily(final String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public void setFontWeight(final String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
    }

    public void setFont(final String font) {
        this.font = font;
    }

    public void setLeading(final int leading) {
        this.leading = leading;
    }

    public FillColor getFillColor() {
        return fillColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getFont() {
        return font;
    }

    public int getLeading() {
        return leading;
    }

    public String getType() {
        return type;
    }
}
