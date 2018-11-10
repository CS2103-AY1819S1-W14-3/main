package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

public class XmlAdaptedAppointmentTest {
    private static final String INVALID_PROCEDURE = "!";
    private static final String INVALID_DATE_TIME = "@";
    private static final String INVALID_DOCTOR = "1";

    private static final String VALID_TYPE = "SRG";
    private static final String VALID_PROCEDURE = "Heart Bypass";
    private static final String VALID_DATE_TIME = "12-12-2022 12:00";
    private static final String VALID_DOCTOR = "Dr. Pepper";

    private Appointment appt;

    @Before
    public void setUp() throws IllegalValueException {
        appt = new Appointment(VALID_TYPE, VALID_PROCEDURE, VALID_DATE_TIME, VALID_DOCTOR);
    }

    @Test
    public void toModelType_validPersonDetails_returnsPerson() {
        XmlAdaptedAppointment xmlAdaptedAppointment = new XmlAdaptedAppointment(appt);
        assertEquals(appt, xmlAdaptedAppointment.toModelType());
    }

    @Test
    public void toModelType_invalidProcedure_throwsIllegalValueException() {
        new XmlAdaptedAppointment(VALID_TYPE, INVALID_PROCEDURE, VALID_DATE_TIME, VALID_DOCTOR);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedAppointment(VALID_TYPE, VALID_PROCEDURE, INVALID_DATE_TIME, VALID_DOCTOR);
    }

    @Test
    public void toModelType_invalidDoctor_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedAppointment(VALID_TYPE, VALID_PROCEDURE, VALID_DATE_TIME, INVALID_DOCTOR);
    }
}
