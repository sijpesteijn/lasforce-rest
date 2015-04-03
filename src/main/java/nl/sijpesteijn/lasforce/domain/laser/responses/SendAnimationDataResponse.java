package nl.sijpesteijn.lasforce.domain.laser.responses;

import nl.sijpesteijn.lasforce.services.AnimationMetaData;

import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public class SendAnimationDataResponse implements SocketResponse {
    private List<AnimationMetaData> animations;

    public List<AnimationMetaData> getAnimations() {
        return animations;
    }

    public void setAnimations(List<AnimationMetaData> animations) {
        this.animations = animations;
    }
}
