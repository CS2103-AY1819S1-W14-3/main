package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author snajef
/**
 * Test driver class for PrescriptionList wrapper class functionality.
 *
 * @author Darien Chong
 *
 */
public class PrescriptionListTest {
    private String name;
    private Dose dose;
    private Duration duration;
    private Calendar fiveDaysFromNow;

    private Prescription prescription;

    @Before
    public void setUp() throws IllegalValueException {
        name = "Paracetamol";
        dose = new Dose(2, "tablets", 4);
        duration = new Duration(Duration.MILLISECONDS_IN_A_DAY * Duration.DAYS_PER_WEEK * 2);

        fiveDaysFromNow = Calendar.getInstance();
        fiveDaysFromNow.add(Calendar.DAY_OF_MONTH, 5);

        prescription = new Prescription(name, dose, duration);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullList_throwsNullPointerException() {
        new PrescriptionList((List<Prescription>) null);
    }

    @Test
    public void add() {
        PrescriptionList pxs = new PrescriptionList();
        PrescriptionList pxs1 = new PrescriptionList(Arrays.asList(new Prescription[] {prescription}));

        assertFalse(pxs1.equals(pxs));

        pxs.add(prescription);
        assertTrue(pxs1.equals(pxs));
    }

    @Test
    public void contains() {
        PrescriptionList pxs = new PrescriptionList();
        assertFalse(pxs.contains(prescription));

        pxs.add(prescription);
        assertTrue(pxs.contains(prescription));
    }

    @Test
    public void remove() {
        PrescriptionList pxs = new PrescriptionList();
        pxs.add(prescription);

        assertTrue(pxs.contains(prescription));

        pxs.remove(prescription);
        assertFalse(pxs.contains(prescription));
    }

    @Test
    public void equals_objectEqualToItself_returnsTrue() {
        PrescriptionList pxs = new PrescriptionList();
        assertTrue(pxs.equals(pxs));
    }

    @Test
    public void equals_zeroArgConstructorAndListConstructor_returnsTrue() {
        assertTrue(new PrescriptionList().equals(new PrescriptionList(new ArrayList<Prescription>())));
    }

    @Test
    public void equals_listConstructorAndPrescriptionListConstructor_returnsTrue() {
        List<Prescription> pxs0 = new ArrayList<>();
        pxs0.add(prescription);
        PrescriptionList pxs = new PrescriptionList(pxs0);
        assertTrue(pxs.equals(new PrescriptionList(pxs)));
    }

    @Test
    public void equals_emptyPrescriptionListAndNull_returnsFalse() {
        assertFalse(new PrescriptionList().equals(null));
    }

    @Test
    public void toStringTest() {
        // Admittedly, this isn't a very comprehensive test of the toString method.
        // Then again, it's just a mapping of the Prescription::toString method
        // to all the Prescriptions in the list.
        // Does that even need comprehensive testing?
        PrescriptionList pxs = new PrescriptionList(Arrays.asList(new Prescription[] {prescription}));
        assertTrue(pxs.toString().equals(prescription.toString()));
    }
}
