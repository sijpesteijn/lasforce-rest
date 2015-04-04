package nl.sijpesteijn.lasforce.domain.laser.responses;

/**
 * @author Gijs Sijpesteijn
 */
public class OkResponse implements SocketResponse {
    private String originalRequest;

    public String getOriginalRequest() {
        return originalRequest;
    }

    public void setOriginalRequest(String originalRequest) {
        this.originalRequest = originalRequest;
    }

    @Override
    public String toString() {
        return "OkResponse{" +
                "originalRequest='" + originalRequest + '\'' +
                '}';
    }
}
