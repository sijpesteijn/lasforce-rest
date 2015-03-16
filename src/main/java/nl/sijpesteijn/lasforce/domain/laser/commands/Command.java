package nl.sijpesteijn.lasforce.domain.laser.commands;

/**
 * @author Gijs Sijpesteijn
 */
public class Command {
    private String command;

    public Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
