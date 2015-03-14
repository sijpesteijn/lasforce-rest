package nl.sijpesteijn.lasforce.domain;


import nl.sijpesteijn.ilda.IldaFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
@XmlRootElement
public class IldaAnimation implements Animation {
    private String name;
    private IldaFormat ildaFormat;

    @XmlElement
    public void setName(final String name) {
        this.name = name;
    }

    @XmlElement
    public void setIldaFormat(final IldaFormat ildaFormat) {
        this.ildaFormat = ildaFormat;
    }

    public IldaFormat getIldaFormat() {
        return ildaFormat;
    }

    public String getName() {
        return name;
    }

    @Override
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
