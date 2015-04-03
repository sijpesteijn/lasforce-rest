package nl.sijpesteijn.lasforce.domain.laser.commands;

/**
 * @author Gijs Sijpesteijn
 */
public class Request {
    private String command;

    public Request(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
