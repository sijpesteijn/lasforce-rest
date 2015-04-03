package nl.sijpesteijn.lasforce.domain.laser;


import nl.sijpesteijn.lasforce.domain.SequenceInfo;
import nl.sijpesteijn.lasforce.domain.laser.commands.Request;
import nl.sijpesteijn.lasforce.domain.laser.responses.SocketResponse;
import nl.sijpesteijn.lasforce.exceptions.LaserException;

/**
 * @author Gijs Sijpesteijn
 */
public interface Laser {

    SocketResponse playSequence(SequenceInfo sequenceInfo) throws LaserException;

    SocketResponse sendCommand(Request stop_service) throws LaserException;
}
