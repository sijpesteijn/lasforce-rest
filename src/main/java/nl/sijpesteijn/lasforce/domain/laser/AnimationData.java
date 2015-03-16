package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.IldaFormat;

/**
 * @author Gijs Sijpesteijn
 */
public class AnimationData {
    private IldaFormat ilda;

    public AnimationData(IldaFormat ilda) {

        this.ilda = ilda;
    }

    public IldaFormat getIlda() {
        return ilda;
    }
}
