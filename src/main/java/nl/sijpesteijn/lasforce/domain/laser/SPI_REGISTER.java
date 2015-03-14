package nl.sijpesteijn.lasforce.domain.laser;

/**
 * @author Gijs Sijpesteijn
 */
public enum SPI_REGISTER {

    MCP23S08_IODIR(0x00),
    MCP23S08_IPOL(0x01),
    MCP23S08_GPINTEN(0x02),
    MCP23S08_DEFVAL(0x03),
    MCP23S08_INTCON(0x04),
    MCP23S08_IOCON(0x05),
    MCP23S08_GPPU(0x06),
    MCP23S08_INTF(0x07),
    MCP23S08_INTCAP(0x08),
    MCP23S08_GPIO(0x09),
    MCP23S08_OLAT(0x0A),;
    private int address;

    SPI_REGISTER(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }
}
