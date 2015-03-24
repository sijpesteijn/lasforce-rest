package nl.sijpesteijn.lasforce.domain.laser.commands;


import nl.sijpesteijn.lasforce.domain.laser.AnimationData;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gijs Sijpesteijn
 */
@XmlRootElement
public class SendAnimationData extends Command {
    private AnimationData animation;

    public SendAnimationData(AnimationData animation) {
        super("animation_data");
        this.animation = animation;
    }

    public AnimationData getAnimation() {
        return animation;
    }
}
