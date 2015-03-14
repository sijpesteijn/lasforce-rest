package nl.sijpesteijn.lasforce.services;

import nl.sijpesteijn.lasforce.domain.SequenceInfo;

import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public interface ISequenceService {

    List<SequenceInfo> getSequences();

    void play(SequenceInfo sequenceInfo);
}
