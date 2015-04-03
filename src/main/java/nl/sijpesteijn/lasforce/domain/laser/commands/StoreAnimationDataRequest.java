package nl.sijpesteijn.lasforce.domain.laser.commands;


import nl.sijpesteijn.lasforce.domain.laser.AnimationData;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gijs Sijpesteijn
 */
@XmlRootElement
public class StoreAnimationDataRequest extends Request {
    private AnimationData animation;

    public StoreAnimationDataRequest(AnimationData animation) {
        super("store_animation_data_request");
        this.animation = animation;
    }

    public AnimationData getAnimation() {
        return animation;
    }
}
