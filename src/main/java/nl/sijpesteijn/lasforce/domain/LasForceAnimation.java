package nl.sijpesteijn.lasforce.domain;

import nl.sijpesteijn.lasforce.services.AnimationMetaData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class LasForceAnimation implements IAnimation {
    private AnimationMetaData metaData;
    private List<Layer> layers = new ArrayList<Layer>();

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(final List<Layer> layers) {
        this.layers = layers;
    }

    public void addLayer(final Layer layer) {
        layers.add(layer);
    }

    public AnimationMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(AnimationMetaData metaData) {
        this.metaData = metaData;
    }
}
