package nl.sijpesteijn.lasforce.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
public class Show {
    private String name;
    private int id;
    private List<Sequence> sequences = new ArrayList();

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

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }
}
