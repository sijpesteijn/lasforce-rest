package nl.sijpesteijn.lasforce.domain.laser.commands;

import nl.sijpesteijn.lasforce.domain.laser.AnimationData;
import nl.sijpesteijn.lasforce.domain.laser.responses.SendAnimationDataResponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gijs Sijpesteijn
 */
@XmlRootElement
public class SendAnimationData extends Command {
    private SendAnimationDataResponse animationMetaData;
    private AnimationData animationData;

    public SendAnimationData(SendAnimationDataResponse animationMetaData, AnimationData animationData) {
        super("animation_data");
        this.animationMetaData = animationMetaData;
        this.animationData = animationData;
    }

    public AnimationData getAnimationData() {
        return animationData;
    }

    public SendAnimationDataResponse getAnimationMetaData() {
        return animationMetaData;
    }
}
