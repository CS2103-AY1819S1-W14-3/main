package seedu.address.storage;

//@@author yuntongzhang

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * XML adapted version of the DietCollection class.
 * @author yuntongzhang
 */
@XmlRootElement
public class XmlAdaptedDietCollection {
    @XmlElement(required = true, name = "diets")
    private Set<XmlAdaptedDiet> dietSet;

    public XmlAdaptedDietCollection() {
        this.dietSet = new HashSet<>();
    }

    public XmlAdaptedDietCollection(Set<XmlAdaptedDiet> dietSet) {
        this.dietSet = new HashSet<>(dietSet);
    }

    public XmlAdaptedDietCollection(XmlAdaptedDietCollection dietCollection) {
        this(dietCollection.dietSet);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDietCollection)) {
            return false;
        }

        XmlAdaptedDietCollection xmlDietCollection = (XmlAdaptedDietCollection) other;
        return this.dietSet.equals(xmlDietCollection.dietSet);
    }
}
