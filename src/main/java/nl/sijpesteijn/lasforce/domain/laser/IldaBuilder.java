package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.ilda.CoordinateHeader;
import nl.sijpesteijn.ilda.IldaFormat;
import nl.sijpesteijn.ilda.IldaHeader;
import nl.sijpesteijn.lasforce.domain.Animation;
import nl.sijpesteijn.lasforce.domain.IldaAnimation;
import nl.sijpesteijn.lasforce.domain.LaserAnimation;
import nl.sijpesteijn.lasforce.domain.Layer;

/**
 * @author Gijs Sijpesteijn
 */
public class IldaBuilder {
    private static final int MIN_WIDTH = 100;
    private static final int MAX_DEPTH = 200;
    private static final int MAX_HEIGHT = 300;
    private static final int MAX_WIDTH = 400;
    private static final int MIN_DEPTH = 500;
    private static final int MIN_HEIGHT = 600;

    public IldaFormat buildIldaFormat(Animation animation) {
        if (animation instanceof IldaAnimation) {
            return ((IldaAnimation) animation).getIldaFormat();
        }
        IldaFormat ildaFormat = new IldaFormat();
        IldaHeader ildaHeader = new IldaHeader();
        ildaHeader.setDirty(false);
        ildaHeader.setMaxDepth(MAX_DEPTH);
        ildaHeader.setMaxHeight(MAX_HEIGHT);
        ildaHeader.setMaxWidth(MAX_WIDTH);
        ildaHeader.setMinDepth(MIN_DEPTH);
        ildaHeader.setMinHeight(MIN_HEIGHT);
        ildaHeader.setMinWidth(MIN_WIDTH);
        ildaFormat.setHeader(ildaHeader);
        LaserAnimation laserAnimation = (LaserAnimation) animation;
        for(Layer layer : laserAnimation.getLayers()) {
            CoordinateHeader header = new CoordinateHeader();
            ildaFormat.addCoordinateHeader(header);
        }
        return ildaFormat;
    }
}
