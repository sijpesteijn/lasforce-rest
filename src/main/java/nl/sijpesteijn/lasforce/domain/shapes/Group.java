package nl.sijpesteijn.lasforce.domain.shapes;

import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class Group implements Child {
    private String type = "Group";
    private List<Child> children;
    private boolean applyMatrix;

    public void setChildren(final List<Child> children) {
        this.children = children;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setApplyMatrix(final boolean applyMatrix) {
        this.applyMatrix = applyMatrix;
    }

    public boolean isApplyMatrix() {
        return applyMatrix;
    }
}
