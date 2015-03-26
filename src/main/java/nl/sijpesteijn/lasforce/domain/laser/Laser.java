package nl.sijpesteijn.lasforce.domain.laser;


import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.domain.laser.commands.Command;
import nl.sijpesteijn.lasforce.exceptions.LaserException;
import nl.sijpesteijn.lasforce.services.AnimationInfo;

/**
 * @author Gijs Sijpesteijn
 */
public interface Laser {

    void playAnimation(AnimationInfo animationInfo) throws LaserException;

    void playSequence(SequenceInfo sequenceInfo) throws LaserException;

    void sendCommand(Command stop_service) throws LaserException;
}
