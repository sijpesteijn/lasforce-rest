package nl.sijpesteijn.lasforce.services;


import nl.sijpesteijn.lasforce.domain.Animation;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.exceptions.LaserException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public interface IAnimationService {

    Animation load(String name) throws IOException, URISyntaxException;

    List<AnimationInfo> getAnimations();

    void save(Animation animation) throws LaserException;

    List<AnimationInfo> getAnimations(SequenceInfo sequenceInfo);
}
