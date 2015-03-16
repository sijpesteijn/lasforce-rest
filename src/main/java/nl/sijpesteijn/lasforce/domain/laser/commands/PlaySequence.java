package nl.sijpesteijn.lasforce.domain.laser.commands;

import nl.sijpesteijn.lasforce.domain.SequenceInfo;

/**
 * @author Gijs Sijpesteijn
 */
public class PlaySequence extends Command {
    private SequenceInfo sequenceInfo;

    public PlaySequence(SequenceInfo sequenceInfo) {
        super("play_sequence");
        this.sequenceInfo = sequenceInfo;
    }

    public SequenceInfo getSequenceInfo() {
        return sequenceInfo;
    }
}
