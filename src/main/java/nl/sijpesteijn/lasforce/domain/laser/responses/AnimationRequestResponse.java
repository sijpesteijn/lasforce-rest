package nl.sijpesteijn.lasforce.domain.laser.responses;

import nl.sijpesteijn.lasforce.services.AnimationMetaData;

import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public class AnimationRequestResponse implements SocketResponse {
    List<AnimationMetaData> animations;

    public List<AnimationMetaData> getAnimations() {
        return animations;
    }

    public void setAnimations(List<AnimationMetaData> animations) {
        this.animations = animations;
    }

    @Override
    public String toString() {
        return "AnimationRequestResponse{" +
                "animations=" + animations +
                '}';
    }
}
