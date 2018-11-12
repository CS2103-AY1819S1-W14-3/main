package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicalhistory.Timestamp;

/**
 * JAXB-friendly adapted version of the Diagnosis class.
 */

@XmlRootElement
public class XmlAdaptedDiagnosis {
    @XmlElement(required = true)
    private String description;

    @XmlElement(required = true)
    private String doctorInCharge;

    @XmlElement(required = true)
    private XmlAdaptedTimestamp timestamp;

    /**
     * Constructs an XmlAdaptedDiagnosis.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDiagnosis() {}

    /**
     * Constructs a {@code XmlAdaptedDiagnosis} with the given details.
     */
    public XmlAdaptedDiagnosis(String description, String doctorName, Timestamp timestamp) {
        this.description = description;
        this.doctorInCharge = doctorName;
        this.timestamp = new XmlAdaptedTimestamp(timestamp);
    }

    /**
     * Converts a given Diagnosis into this class for JAXB use.
     */
    public XmlAdaptedDiagnosis(Diagnosis source) {
        this.description = source.getDiagnosis();
        this.doctorInCharge = source.getDoctorInCharge();
        this.timestamp = new XmlAdaptedTimestamp(source.getTimestamp());
    }

    /**
     * Converts this JAXB-friendly adapted Diagnosis object into the model's Diagnosis object.
     */
    public Diagnosis toModelType() {
        return new Diagnosis(this.description, this.doctorInCharge, this.timestamp.toModelType());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDiagnosis)) {
            return false;
        }

        XmlAdaptedDiagnosis xad = (XmlAdaptedDiagnosis) other;
        return description.equals(xad.description)
                && doctorInCharge.equals(xad.doctorInCharge)
                && timestamp.equals(xad.timestamp);
    }
}
