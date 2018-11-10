package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.appointment.Appointment;

/**
 * JAXB-friendly adapted version of the Appointment
 */

@XmlRootElement
public class XmlAdaptedAppointment {

    @XmlElement(required = true)
    private String type;

    @XmlElement(required = true)
    private String procedureName;

    @XmlElement(required = true)
    private String dateTime;

    @XmlElement(required = true)
    private String docName;

    /**
     * Constructs an XmlAdaptedAppointment
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs a {@code XmlAdaptedAppointment} with the given details.
     */
    public XmlAdaptedAppointment(String type, String procedureName, String dateTime, String docName) {
        this.type = type;
        this.procedureName = procedureName;
        this.dateTime = dateTime;
        this.dateTime = dateTime;
        this.docName = docName;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     */
    public XmlAdaptedAppointment(Appointment source) {
        this.type = source.getType();
        this.procedureName = source.getProcedure_name();
        this.dateTime = source.getDate_time();
        this.docName = source.getDoc_name();
    }

    /**
     * Converts this JAXB-friendly adapted Appointment object into the model's Appointment object.
     */
    public Appointment toModelType() {
        return new Appointment(type, procedureName, dateTime, docName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }
        return type.equals(((XmlAdaptedAppointment) other).type)
                && procedureName.equals(((XmlAdaptedAppointment) other).procedureName)
                && dateTime.equals(((XmlAdaptedAppointment) other).dateTime)
                && docName.equals(((XmlAdaptedAppointment) other).docName);
    }
}
