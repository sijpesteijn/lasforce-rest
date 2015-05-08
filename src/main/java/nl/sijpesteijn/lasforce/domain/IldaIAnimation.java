package nl.sijpesteijn.lasforce.domain;


import nl.sijpesteijn.ilda.Ilda;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
@XmlRootElement
public class IldaIAnimation implements IAnimation {
    private String name;
    private Ilda ildaFormat;

    @XmlElement
    public void setName(final String name) {
        this.name = name;
    }

    @XmlElement
    public void setIldaFormat(final Ilda ildaFormat) {
        this.ildaFormat = ildaFormat;
    }

    public Ilda getIldaFormat() {
        return ildaFormat;
    }

    public String getName() {
        return name;
    }

    public List<Layer> getLayers() {
        return null;
    }

    @Override
    public String toString() {
        return "Animation{" +
                "name:" + name + "," +
                "ildaFormat:" + ildaFormat +
                "}";
    }
}
