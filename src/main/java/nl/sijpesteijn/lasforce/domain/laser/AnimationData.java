package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.IldaFormat;
import nl.sijpesteijn.lasforce.services.AnimationInfo;

/**
 * @author Gijs Sijpesteijn
 */
public class AnimationData {
    private AnimationInfo animationMetaData;
    private IldaFormat ilda;

    public AnimationData(AnimationInfo animationMetaData, IldaFormat ilda) {
        this.animationMetaData = animationMetaData;
        this.ilda = ilda;
    }

    public IldaFormat getIlda() {
        return ilda;
    }

    public AnimationInfo getAnimationMetaData() {
        return animationMetaData;
    }
}
