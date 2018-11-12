package seedu.address.storage;

//@@author yuntongzhang

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietType;

/**
 * XML adapted version of the Diet class.
 * @author yuntongzhang
 */
@XmlRootElement
public class XmlAdaptedDiet {

    @XmlElement(required = true)
    private String detail;

    @XmlElement(required = true)
    private DietType type;

    /**
     * Constructs an XmlAdaptedDiet.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDiet() {}

    /**
     * Constructs a {@code XmlAdaptedDiet} with the given {@code detail} and {@code type}.
     */
    public XmlAdaptedDiet(String detail, DietType type) {
        this.detail = detail;
        this.type = type;
    }

    /**
     * Converts a given {@code Diet} into a {@code XmlAdaptedDiet} for JAXB use.
     */
    public XmlAdaptedDiet(Diet source) {
        this.detail = source.getDetail();
        this.type = source.getType();
    }

    /**
     * Converts this JAXB-friendly adapted Diet object into the model's Diet object.
     */
    public Diet toModelType() {
        return new Diet(detail, type);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDiet)) {
            return false;
        }

        XmlAdaptedDiet xmlDiet = (XmlAdaptedDiet) other;
        return this.detail.equals(xmlDiet.detail)
                && this.type == xmlDiet.type;
    }

    /**
     * Compute the hashCode for a XmlAdaptedDiet.
     * This method is used when comparing whether two sets of XmlAdaptedDiet are equal, because the hashCode of a set
     * is the sum of hashCode of the elements in the set.
     * @return hashCode of a XmlAdaptedDiet object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(detail, type);
    }
}
