package nl.sijpesteijn.lasforce.domain.laser.responses;

import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public class ListResponse implements SocketResponse {
    private List<SocketResponse> elements;

    public List<SocketResponse> getElements() {
        return elements;
    }

    public void setElements(List<SocketResponse> elements) {
        this.elements = elements;
    }
}
