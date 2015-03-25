package nl.sijpesteijn.lasforce.domain.laser.responses;

import nl.sijpesteijn.lasforce.services.AnimationInfo;

import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public class AnimationRequestResponse implements SocketResponse {
    List<AnimationInfo> animations;

    public List<AnimationInfo> getAnimations() {
        return animations;
    }

    public void setAnimations(List<AnimationInfo> animations) {
        this.animations = animations;
    }
}
