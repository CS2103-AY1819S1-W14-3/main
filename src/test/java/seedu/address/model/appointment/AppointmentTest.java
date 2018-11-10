package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Test driver class for Appointment POJO class functionality
 */
public class AppointmentTest {
    private String type;
    private String procedure;
    private String dateTime;
    private String doctor;
    private String invalidDateTime;

    @Before
    public void setUp() {
        type = "SRG";
        procedure = "Heart Bypass";
        dateTime = "12-12-2022 12:00";
        invalidDateTime = "12-12-1000 23:30";
        doctor = "Dr. Pepper";
    }

    @Test
    public void constructor_allNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Appointment(null, null, null, null));
    }

    @Test
    public void constructor_typeNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Appointment(null, procedure, dateTime, doctor));
    }

    @Test
    public void constructor_procedureNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Appointment(type, null, dateTime, doctor));
    }

    @Test
    public void constructor_dateTimeNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Appointment(type, procedure, null, doctor));
    }

    @Test
    public void constructor_doctorNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Appointment(type, procedure, dateTime, null));
    }

    @Test
    public void equals_allSame_true() {
        Appointment a1 = new Appointment(type, procedure, dateTime, doctor);
        Appointment a2 = new Appointment(type, procedure, dateTime, doctor);
        assertTrue(a1.equals(a2));
    }

    @Test
    public void toStringTest() {
        Appointment a = new Appointment(type, procedure, dateTime, doctor);
        assertEquals(type + " | " + procedure + " | " + dateTime + " | " + doctor, a.toString());

    }
}
