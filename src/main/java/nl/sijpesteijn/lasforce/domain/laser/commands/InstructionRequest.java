package nl.sijpesteijn.lasforce.domain.laser.commands;

/**
 * @author Gijs Sijpesteijn
 */
public class InstructionRequest extends Request {
    private String key;
    private String value;

    public InstructionRequest(String key) {
        this(key, "");
    }

    public InstructionRequest(String key, String value) {
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
