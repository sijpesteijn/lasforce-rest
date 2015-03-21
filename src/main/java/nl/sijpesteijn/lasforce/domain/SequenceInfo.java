package nl.sijpesteijn.lasforce.domain;

import nl.sijpesteijn.lasforce.services.AnimationInfo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@XmlRootElement
public class SequenceInfo {
    private String name;
    private List<AnimationInfo> animations;

    public String getName() {
        return name;
    }

    public List<AnimationInfo> getAnimations() {
        return animations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnimations(List<AnimationInfo> animations) {
        this.animations = animations;
    }

    public int getAnimations_length() {
        return animations.size();
    }
}
