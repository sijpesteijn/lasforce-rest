package nl.sijpesteijn.lasforce.services;

import com.google.inject.Inject;
import nl.sijpesteijn.lasforce.domain.SequenceInfo;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@Singleton
public class SequenceService implements ISequenceService {
    @Inject
    private IAnimationService animationService;

    @Override
    public List<SequenceInfo> getSequences() {
        SequenceInfo sequenceInfo = new SequenceInfo("start", animationService.getAnimations());
        animationService.getAnimations(sequenceInfo);
        return Arrays.asList(sequenceInfo);
    }

    @Override
    public void play(SequenceInfo sequenceInfo) {

    }
}
