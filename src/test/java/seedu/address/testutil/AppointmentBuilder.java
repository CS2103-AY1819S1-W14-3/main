package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class for Appointment
 */
public class AppointmentBuilder {

    /** We don't construct a default DATE_TIME object here because of the IllegalValueException. */
    private static final String DEFAULT_TYPE = "SRG";
    private static final String DEFAULT_PROCEDURE = "Heart Bypass";
    private static final String DEFAULT_DATE_TIME = "12-12-2022 12:00";
    private static final String DEFAULT_DOCTOR = "Dr. Pepper";

    private String type;
    private String procedure;
    private String dateTime;
    private String doctor;

    public AppointmentBuilder() throws IllegalValueException {
        type = DEFAULT_TYPE;
        procedure = DEFAULT_PROCEDURE;
        dateTime = DEFAULT_DATE_TIME;
        doctor = DEFAULT_DOCTOR;
    }

    /** Copy constructor. */
    public AppointmentBuilder(Appointment a) {
        type = a.getType();
        procedure = a.getProcedure_name();
        dateTime = a.getDate_time();
        doctor = a.getDoc_name();
    }

    /**
     * Setter for type
     * @param type The type of appointment
     * @return this
     */
    public AppointmentBuilder withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Setter for procedure
     * @param proc The procedure object to defensively copy and use.
     * @return this
     */
    public AppointmentBuilder withProcedure(String proc) {
        this.procedure = proc;
        return this;
    }

    /**
     * Setter for DateTime
     * @param d The string object to defensively copy and use.
     * @return this
     */
    public AppointmentBuilder withDateTime(String d) {
        this.dateTime = d;
        return this;
    }

    /**
     * Setter for doctor
     * @param docName The doctor's name to defensively copy and use.
     * @return this
     */
    public AppointmentBuilder withDocName(String docName) {
        this.doctor = docName;
        return this;
    }

    /**
     * Returns an {@code Appointment} object with the given characteristics.
     */
    public Appointment build() {
        return new Appointment(type, procedure, dateTime, doctor);
    }
}

