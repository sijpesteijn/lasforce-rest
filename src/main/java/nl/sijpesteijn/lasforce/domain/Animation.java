package nl.sijpesteijn.lasforce.domain;

import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public interface Animation {

    String getName();

    List<Layer> getLayers();
    
}
