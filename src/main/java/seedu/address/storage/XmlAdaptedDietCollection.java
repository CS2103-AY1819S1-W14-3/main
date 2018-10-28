package seedu.address.storage;

//@@author yuntongzhang

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * XML adapted version of the DietCollection class.
 * @author yuntongzhang
 */
@XmlRootElement
public class XmlAdaptedDietCollection implements Iterable<XmlAdaptedDiet> {
    @XmlElement(required = true, name = "diet")
    private Set<XmlAdaptedDiet> dietSet;

    public XmlAdaptedDietCollection() {
        this.dietSet = new HashSet<>();
    }

    public XmlAdaptedDietCollection(Set<XmlAdaptedDiet> dietSet) {
        this.dietSet = new HashSet<>(dietSet);
    }

    /** Setter method to change the internal dietSet. */
    public void setDiet(Set<XmlAdaptedDiet> newDietSet) {
        this.dietSet = new HashSet<>(newDietSet);
    }

    @Override
    public Iterator<XmlAdaptedDiet> iterator() {
        return dietSet.iterator();
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
