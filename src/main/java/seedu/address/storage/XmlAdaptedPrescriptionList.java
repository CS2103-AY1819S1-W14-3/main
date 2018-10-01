package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@@author snajef
/**
 * A JAXB-ready version of the PrescriptionList, for easy un/marshalling.
 * @author Darien Chong
 *
 */
@XmlRootElement
// This annotation is required because JAXB is stupid as FUCK and can't
// differentiate between members and their getters.
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAdaptedPrescriptionList implements Iterable<XmlAdaptedPrescription> {
    @XmlElement
    private List<XmlAdaptedPrescription> prescription;

    public XmlAdaptedPrescriptionList() {
        prescription = new ArrayList<>();
    }

    /** C'tor to wrap a list. */
    public XmlAdaptedPrescriptionList(List<XmlAdaptedPrescription> prescription) {
        this.prescription = new ArrayList<>(prescription);
    }

    /** Setter method to hot swap the internal list. */
    public void setPrescription(List<XmlAdaptedPrescription> prescription) {
        this.prescription = new ArrayList<>(prescription);
    }

    @Override
    public Iterator<XmlAdaptedPrescription> iterator() {
        return prescription.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof XmlAdaptedPrescriptionList)) {
            return false;
        }

        XmlAdaptedPrescriptionList xapxs = (XmlAdaptedPrescriptionList) o;
        return prescription.equals(xapxs.prescription);
    }
}
