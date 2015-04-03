package nl.sijpesteijn.lasforce.domain.laser.commands;

import nl.sijpesteijn.lasforce.domain.SequenceInfo;

/**
 * @author Gijs Sijpesteijn
 */
public class PlaySequence extends Request {
    private SequenceInfo sequenceInfo;

    public PlaySequence(SequenceInfo sequenceInfo) {
        super("play_sequence_request");
        this.sequenceInfo = sequenceInfo;
    }

    public SequenceInfo getSequenceInfo() {
        return sequenceInfo;
    }
}
