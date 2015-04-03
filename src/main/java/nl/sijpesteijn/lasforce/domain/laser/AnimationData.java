package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.IldaFormat;
import nl.sijpesteijn.lasforce.services.AnimationMetaData;

/**
 * @author Gijs Sijpesteijn
 */
public class AnimationData {
    private AnimationMetaData animationMetaData;
    private IldaFormat ilda;

    public AnimationData(AnimationMetaData animationMetaData, IldaFormat ilda) {
        this.animationMetaData = animationMetaData;
        this.ilda = ilda;
    }

    public IldaFormat getIlda() {
        return ilda;
    }

    public AnimationMetaData getAnimationMetaData() {
        return animationMetaData;
    }
}
