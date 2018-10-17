package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A JAXB-ready version of the AppointmentsList, for easy un/marshalling.
 * @author jeffypie369
 */
@XmlRootElement
public class XmlAdaptedAppointmentsList implements Iterable<XmlAdaptedAppointment> {
    @XmlElement(required = true)
    private List<XmlAdaptedAppointment> appt;

    public XmlAdaptedAppointmentsList() {
        appt = new ArrayList<>();
    }

    public XmlAdaptedAppointmentsList(List<XmlAdaptedAppointment> appt) {
        this.appt = new ArrayList<>(appt);
    }

    /** Setter method to hot swap the internal list. */
    public void setAppt(List<XmlAdaptedAppointment> appt) {
        this.appt = new ArrayList<>(appt);
    }

    @Override
    public Iterator<XmlAdaptedAppointment> iterator() {
        return appt.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof XmlAdaptedAppointmentsList)) {
            return false;
        }

        XmlAdaptedAppointmentsList xaaxs = (XmlAdaptedAppointmentsList) o;
        return appt.equals(xaaxs.appt);
    }
}
