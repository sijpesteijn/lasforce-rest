package nl.sijpesteijn.lasforce.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class LaserAnimation implements Animation {
    private String name;
    private List<Layer> layers = new ArrayList<Layer>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(final List<Layer> layers) {
        this.layers = layers;
    }

    public void addLayer(final Layer layer) {
        layers.add(layer);
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\": \"" + name + "\"" +
                ", \"layers\":" + layers +
                "}";
    }
}
