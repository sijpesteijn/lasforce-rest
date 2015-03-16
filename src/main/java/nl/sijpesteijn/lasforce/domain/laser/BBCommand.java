package nl.sijpesteijn.lasforce.domain.laser;


/**
 * @author Gijs Sijpesteijn
 */
public class BBCommand {
    private String command;
    private Message message;

    public BBCommand(String command, Message message) {
        this.command = command;
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public Message getMessage() {
        return message;
    }
}
