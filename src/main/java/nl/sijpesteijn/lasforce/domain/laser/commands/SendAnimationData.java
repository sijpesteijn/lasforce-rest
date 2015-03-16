package nl.sijpesteijn.lasforce.domain.laser.commands;

import nl.sijpesteijn.lasforce.domain.laser.AnimationData;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gijs Sijpesteijn
 */
@XmlRootElement
public class SendAnimationData extends Command {
    private AnimationData animationData;

    public SendAnimationData(AnimationData animationData) {
        super("animation_data");
        this.animationData = animationData;
    }

    public AnimationData getAnimationData() {
        return animationData;
    }
}
