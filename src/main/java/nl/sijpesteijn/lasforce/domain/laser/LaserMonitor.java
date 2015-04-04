package nl.sijpesteijn.lasforce.domain.laser;

import nl.sijpesteijn.lasforce.domain.laser.responses.MessageResponse;

/**
 * @author Gijs Sijpesteijn
 */
public interface LaserMonitor {

    void socketMessage(MessageResponse messageResponse);
}
