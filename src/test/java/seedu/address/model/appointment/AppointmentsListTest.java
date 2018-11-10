package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test driver class for AppointmentsList wrapper class functionality
 */
public class AppointmentsListTest {
    private String type;
    private String procedure;
    private String dateTime;
    private String doctor;

    private Appointment appt;

    @Before
    public void setUp() {
        type = "SRG";
        procedure = "Heart Bypass";
        dateTime = "12-12-2022 12:00";
        doctor = "Dr. Pepper";

        appt = new Appointment(type, procedure, dateTime, doctor);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullList_throwsNullPointerException() {
        new AppointmentsList((List<Appointment>) null);
    }

    @Test
    public void add() {
        AppointmentsList apptList = new AppointmentsList();
        AppointmentsList apptList1 = new AppointmentsList(Arrays.asList(new Appointment[] { appt}));

        assertFalse(apptList1.equals(apptList));

        apptList.add(appt);
        assertTrue(apptList1.equals(apptList));
    }

    @Test
    public void contains() {
        AppointmentsList apptList = new AppointmentsList();
        assertFalse(apptList.contains(appt));

        apptList.add(appt);
        assertTrue(apptList.contains(appt));
    }

    @Test
    public void remove() {
        AppointmentsList apptList = new AppointmentsList();
        apptList.add(appt);

        assertTrue(apptList.contains(appt));

        apptList.remove(appt);
        assertFalse(apptList.contains(appt));
    }

    @Test
    public void equals_objectEqualsItself_returnsTrue() {
        AppointmentsList apptList = new AppointmentsList();
        assertTrue(apptList.equals(apptList));
    }

    @Test
    public void equals_noArgsConstructorAndListConstructor_returnsTrue() {
        assertTrue(new AppointmentsList().equals(new AppointmentsList(new ArrayList<Appointment>())));
    }

    @Test
    public void equals_apppointmentsListConstructorAndListConstructor_returnsTrue() {
        List<Appointment> apptList = new ArrayList<>();
        apptList.add(appt);
        AppointmentsList apptList1 = new AppointmentsList(apptList);
        assertTrue(apptList1.equals(new AppointmentsList(apptList1)));
    }

    @Test
    public void equals_emptyAppointmentsListAndNull_returnsFalse() {
        assertFalse(new AppointmentsList().equals(null));
    }

    @Test
    public void toStringTest() {
        AppointmentsList apptList = new AppointmentsList(Arrays.asList(new Appointment[] {appt}));
        assertTrue(apptList.toString().equals(appt.toString()));
    }
}
