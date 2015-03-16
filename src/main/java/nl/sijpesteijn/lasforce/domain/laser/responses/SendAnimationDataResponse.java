package nl.sijpesteijn.lasforce.domain.laser.responses;

/**
 * @author Gijs Sijpesteijn
 */
public class SendAnimationDataResponse implements SocketResponse {
    private int id;
    private String name;
    private String lastUpdate;
    private int frameRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }
}
