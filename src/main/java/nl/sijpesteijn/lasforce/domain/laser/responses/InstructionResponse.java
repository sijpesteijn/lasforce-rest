package nl.sijpesteijn.lasforce.domain.laser.responses;

/**
 * @author Gijs Sijpesteijn
 */
public class InstructionResponse implements SocketResponse {
    private String command;
    private Object data;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InstructionResponse{" +
                "command='" + command + '\'' +
                ", data=" + data +
                '}';
    }
}
