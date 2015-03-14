package nl.sijpesteijn.lasforce.rest.dto;

import nl.sijpesteijn.lasforce.domain.SequenceInfo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Gijs Sijpesteijn
 */
@XmlRootElement
public class Playlist {
    private String name;
    private List<SequenceInfo> sequences;

    public Playlist(String name, List<SequenceInfo> sequences) {
        this.name = name;
        this.sequences = sequences;
    }

    public String getName() {
        return name;
    }

    public List<SequenceInfo> getSequences() {
        return sequences;
    }
}
