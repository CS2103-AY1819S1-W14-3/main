package seedu.address.storage;

import java.util.ArrayList;
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
public class XmlAdaptedPrescriptionList {
    @XmlElement
    private List<XmlAdaptedPrescription> prescription;

    public XmlAdaptedPrescriptionList() {
        prescription = new ArrayList<>();
    }

    public List<XmlAdaptedPrescription> getPrescription() {
        return prescription;
    }

    public void setPrescription(List<XmlAdaptedPrescription> prescription) {
        this.prescription = new ArrayList<>(prescription);
    }
}
