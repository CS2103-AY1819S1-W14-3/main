package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.visitor.Visitor;

/**
 * JAXB-friendly adapted version of the Visitor class.
 * @author GAO JIAXIN666
 */
@XmlRootElement
public class XmlAdaptedVisitor {
    @XmlElement(required = true)
    private String visitor;

    /**
     * Constructs an XmlAdaptedVisitor.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedVisitor() {}

    public XmlAdaptedVisitor(String visitor) {
        this.visitor = visitor;
    }

    public XmlAdaptedVisitor(Visitor source) {
        this.visitor = source.getVisitorName();
    }

    /**
     * Converts this JAXB-friendly adapted Visitor object into the model's Visitor object.
     */
    public Visitor toModelType() {
        return new Visitor(visitor);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedVisitor)) {
            return false;
        }

        XmlAdaptedVisitor xad = (XmlAdaptedVisitor) other;
        return visitor.equals(xad.visitor);
    }

}
