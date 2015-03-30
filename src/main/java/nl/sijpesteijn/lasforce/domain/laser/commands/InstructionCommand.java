package nl.sijpesteijn.lasforce.domain.laser.commands;

/**
 * @author Gijs Sijpesteijn
 */
public class InstructionCommand extends Command {
    private String key;
    private String value;

    public InstructionCommand(String key, String value) {
        super("instruction_request");
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
