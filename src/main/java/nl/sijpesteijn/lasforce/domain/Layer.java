package nl.sijpesteijn.lasforce.domain;

import nl.sijpesteijn.lasforce.domain.shapes.Child;

import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class Layer {
    private String name;
    private boolean visible;
    private boolean applyMatrix;
    private List<Child> children;

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public void setApplyMatrix(final boolean applyMatrix) {
        this.applyMatrix = applyMatrix;
    }

    public void setChildren(final List<Child> children) {
        this.children = children;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public boolean isApplyMatrix() {
        return applyMatrix;
    }

    public List<Child> getChildren() {
        return children;
    }
}
