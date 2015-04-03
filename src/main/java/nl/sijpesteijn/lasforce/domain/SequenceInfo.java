package nl.sijpesteijn.lasforce.domain;

import nl.sijpesteijn.lasforce.services.AnimationMetaData;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@XmlRootElement
public class SequenceInfo {
    private String name;
    private int loopCount;
    private List<AnimationMetaData> animations;

    public String getName() {
        return name;
    }

    public List<AnimationMetaData> getAnimations() {
        return animations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnimations(List<AnimationMetaData> animations) {
        this.animations = animations;
    }

    public int getAnimations_length() {
        return animations.size();
    }

    public int getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }
}
