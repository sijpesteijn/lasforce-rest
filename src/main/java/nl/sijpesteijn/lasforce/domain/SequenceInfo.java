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

    public SequenceInfo(String name, List<AnimationInfo> animations) {
        this.name = name;
        this.animations = animations;
    }

    public String getName() {
        return name;
    }

    public List<AnimationInfo> getAnimations() {
        return animations;
    }
}
