package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.Ilda;
import nl.sijpesteijn.lasforce.services.AnimationMetaData;

/**
 * @author Gijs Sijpesteijn
 */
public class AnimationData {
    private AnimationMetaData animationMetaData;
    private Ilda ilda;

    public AnimationData(AnimationMetaData animationMetaData, Ilda ilda) {
        this.animationMetaData = animationMetaData;
        this.ilda = ilda;
    }

    public Ilda getIlda() {
        return ilda;
    }

    public AnimationMetaData getAnimationMetaData() {
        return animationMetaData;
    }
}
