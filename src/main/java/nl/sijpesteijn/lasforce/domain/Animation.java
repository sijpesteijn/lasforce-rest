package nl.sijpesteijn.lasforce.domain;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * @author Gijs Sijpesteijn
 */
@Entity("animations")
public class Animation {
    @Id
    @Property("_id")
    private long id;
    private String name;
    private long lastUpdate;
    private int frameRate;
    private int nrOfFrames;
    private String fileName;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getNrOfFrames() {
        return nrOfFrames;
    }

    public void setNrOfFrames(int nrOfFrames) {
        this.nrOfFrames = nrOfFrames;
    }
}
