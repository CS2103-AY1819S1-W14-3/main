package seedu.address.model.medicine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.Assert;

//@@author snajef
/**
 * Test driver class for Prescription POJO class functionality.
 *
 * @author Darien Chong
 *
 */
public class PrescriptionTest {
    private String name;
    private Dose dose;
    private Duration duration;
    private Calendar fiveDaysFromNow;

    @Before
    public void setUp() throws IllegalValueException {
        name = "Paracetamol";
        dose = new Dose(2, "tablets", 4);
        duration = new Duration(Duration.MILLISECONDS_IN_A_DAY * Duration.DAYS_PER_WEEK * 2);

        fiveDaysFromNow = Calendar.getInstance();
        fiveDaysFromNow.add(Calendar.DAY_OF_MONTH, 5);
    }

    @Test
    public void constructor_allNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Prescription(null, null, null));
    }

    @Test
    public void constructor_nameNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Prescription(null, dose, duration));
    }

    @Test
    public void constructor_doseNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Prescription(name, null, duration));
    }

    @Test
    public void constructor_durationNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Prescription(name, dose, null));
    }

    @Test
    public void equals_differentStartDate_false() {
        Prescription p1 = new Prescription(name, dose, duration);

        Duration durationShiftedFiveDays = new Duration(duration);
        durationShiftedFiveDays.shiftDateRange(fiveDaysFromNow);
        Prescription p2 = new Prescription(name, dose, durationShiftedFiveDays);

        assertFalse(p1.equals(p2));
    }

    @Test
    public void equals_allSame_true() {
        Prescription p1 = new Prescription(name, dose, duration);
        Prescription p2 = new Prescription(name, dose, duration);

        assertTrue(p1.equals(p2));
    }

    @Test
    public void equals_differentDose_true() throws IllegalValueException {
        Prescription p1 = new Prescription(name, dose, duration);

        Dose differentDose = new Dose(3, "tablets", 1);
        Prescription p2 = new Prescription(name, differentDose, duration);

        assertFalse(p1.equals(p2));
    }

    @Test
    public void toStringTest() {
        Prescription p1 = new Prescription(name, dose, duration);
        assertEquals(name + " | " + dose.toString() + " | " + duration.toString(), p1.toString());
    }
}
