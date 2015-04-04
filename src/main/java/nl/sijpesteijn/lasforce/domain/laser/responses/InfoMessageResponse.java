package nl.sijpesteijn.lasforce.domain.laser.responses;

/**
 * @author Gijs Sijpesteijn
 */
public class InfoMessageResponse implements MessageResponse {
    private int priority;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "InfoMessageResponse {" +
                "content='" + content + '\'' +
                ", priority=" + priority +
                '}';
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
