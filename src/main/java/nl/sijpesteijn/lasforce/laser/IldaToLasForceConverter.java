package nl.sijpesteijn.lasforce.laser;

import nl.sijpesteijn.ilda.*;
import nl.sijpesteijn.lasforce.domain.LasForceAnimation;
import nl.sijpesteijn.lasforce.domain.Layer;
import nl.sijpesteijn.lasforce.domain.shapes.Child;
import nl.sijpesteijn.lasforce.domain.shapes.Path;
import nl.sijpesteijn.lasforce.domain.shapes.Segment;
import nl.sijpesteijn.lasforce.domain.shapes.StrokeColor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class IldaToLasForceConverter {
    private int VIEWPORT_MIDDLE = 326;
    private int ZOOM = 1;

    public static void main(String[] args) throws IOException, URISyntaxException {
        IldaToLasForceConverter converter = new IldaToLasForceConverter();
        IldaReader reader = new IldaReader();
        final String name = "ilddolf.ild";
        Ilda content = reader.read(new File("domain/src/main/resources/examples/" + name));

        LasForceAnimation animation = converter.convert(content);
        System.out.println(animation);

    }

    public LasForceAnimation convert(Ilda ildaFormat) {
        LasForceAnimation animation = new LasForceAnimation();
        for (CoordinateHeader coordinateHeader : ildaFormat.getCoordinateHeaders()) {
            Layer layer = new Layer();
            layer.setName(coordinateHeader.getFrameName());
            layer.setVisible(false);
            layer.setApplyMatrix(true);
            layer.setChildren(getChildren(coordinateHeader.getCoordinateData()));
            animation.addLayer(layer);
        }
        animation.getLayers().get(0).setVisible(true);
        return animation;
    }

    private List<Child> getChildren(final List<CoordinateData> coordinateDatas) {
        List<Child> children = new ArrayList<Child>();
        boolean wasBlanked = true;
        Path path = null;
        List<List<Segment>> subSegments = new ArrayList();
        for (CoordinateData coordinateData : coordinateDatas) {
            if (!coordinateData.isBlanked()) {
                if (wasBlanked) {
                    path = new Path();
                    children.add(path);
                    path.setApplyMatrix(true);
                    path.setSegments(subSegments);
                }
                Segment segment = new Segment(VIEWPORT_MIDDLE + coordinateData.getX()/ZOOM,
                        VIEWPORT_MIDDLE + coordinateData.getY()/ZOOM);

                subSegments.add(Arrays.asList(segment));
                ColorData colorData = coordinateData.getColorData();
                double blue = getPercentage(colorData.getBlue1());
                double green = getPercentage(colorData.getGreen1());
                double red = getPercentage(colorData.getRed1());
                path.setStrokeColor(new StrokeColor(blue, green, red));
                path.setStrokeWidth(4);
            }
            wasBlanked = coordinateData.isBlanked();
        }
        return children;
    }

    private double getPercentage(final double color) {
        if (color == 0) {
            return 0;
        }
        float percentage;
        percentage = ((float)color)/255f;
        return percentage;
    }
}
