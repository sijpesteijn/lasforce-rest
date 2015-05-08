package nl.sijpesteijn.lasforce.domain.laser.responses;

/**
 * @author Gijs Sijpesteijn
 */
public class MessageResponse implements SocketResponse {
    private int priority;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "MessageResponse {" +
                "data='" + data + '\'' +
                ", priority=" + priority +
                '}';
    }

}
