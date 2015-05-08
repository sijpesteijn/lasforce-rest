package nl.sijpesteijn.lasforce.domain;

/**
 * @author Gijs Sijpesteijn
 */
public class Sequence {
    private String name;
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
