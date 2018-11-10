package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A JAXB-ready version of MedicalHistory.
 */
@XmlRootElement
public class XmlAdaptedMedicalHistory implements Iterable<XmlAdaptedDiagnosis> {
    @XmlElement(required = true)
    private List<XmlAdaptedDiagnosis> medicalHistory;

    public XmlAdaptedMedicalHistory() {
        this.medicalHistory = new ArrayList<>();
    }

    /** Defensive copy c'tor. */
    public XmlAdaptedMedicalHistory(List<XmlAdaptedDiagnosis> mh) {
        this.medicalHistory = new ArrayList<>(mh);
    }

    /** Defensive copy c'tor. */
    public XmlAdaptedMedicalHistory(XmlAdaptedMedicalHistory mh) {
        this(mh.medicalHistory);
    }

    /** Setter method to hot swap the internal list. */
    public void setMedicalHistory(List<XmlAdaptedDiagnosis> mh) {
        this.medicalHistory = new ArrayList<>(mh);
    }

    @Override
    public Iterator<XmlAdaptedDiagnosis> iterator() {
        return medicalHistory.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof XmlAdaptedMedicalHistory)) {
            return false;
        }

        XmlAdaptedMedicalHistory xamh = (XmlAdaptedMedicalHistory) o;
        return medicalHistory.equals(xamh.medicalHistory);
    }
}
