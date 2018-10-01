package seedu.address.model.medicine;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.Assert;

//@@author snajef
/**
 * Test driver class for Dose POJO class functionality.
 * @author Darien Chong
 *
 */
public class DoseTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String doseUnit;
    private double negativeDose;
    private double singleDose;
    private double twoDoses;
    private double threeDoses;
    private double oneMillionDoses;

    private int negativeOncePerDay;
    private int oncePerDay;
    private int twicePerDay;
    private int thricePerDay;
    private int fourTimesADay;

    @Before
    public void setUp() {
        negativeDose = -1;
        singleDose = 1;
        twoDoses = 2;
        threeDoses = 3;
        oneMillionDoses = 1_000_000;

        negativeOncePerDay = -1;
        oncePerDay = 1;
        twicePerDay = 2;
        thricePerDay = 3;
        fourTimesADay = 4;

        doseUnit = "tablets";
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Dose(0, null, 0));
    }

    @Test
    public void constructor_negativeDose_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Dose.MESSAGE_DOSAGE_MUST_BE_POSITIVE);
        new Dose(negativeDose, doseUnit, oncePerDay);
    }

    @Test
    public void constructor_negativeDosesPerDay_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Dose.MESSAGE_DOSES_PER_DAY_MUST_BE_POSITIVE_INTEGER);
        new Dose(singleDose, doseUnit, negativeOncePerDay);
    }

    @Test
    public void isCorrectFuzzyStringRepresentation() {
        org.junit.Assert.assertEquals("once a day.", Dose.fuzzyStringRepresentation(oncePerDay));
        org.junit.Assert.assertEquals("twice a day.", Dose.fuzzyStringRepresentation(twicePerDay));
        org.junit.Assert.assertEquals("thrice a day.", Dose.fuzzyStringRepresentation(thricePerDay));
        org.junit.Assert.assertEquals("4 times a day.", Dose.fuzzyStringRepresentation(fourTimesADay));
    }

    @Test
    public void testToString() throws IllegalValueException {
        double[] doses = new double[] { singleDose, twoDoses, threeDoses, oneMillionDoses };
        int[] dosesPerDay = new int[] { oncePerDay, twicePerDay, thricePerDay, fourTimesADay };

        for (double dose : doses) {
            for (int dosePerDay : dosesPerDay) {
                org.junit.Assert.assertEquals(dose + " " + doseUnit + " " + Dose.fuzzyStringRepresentation(dosePerDay),
                        new Dose(dose, doseUnit, dosePerDay).toString());
            }
        }
    }

    @Test
    public void equals() throws IllegalValueException {
        Dose oneDoseOncePerDay = new Dose(singleDose, doseUnit, oncePerDay);
        Dose oneDoseOncePerDayCopy = new Dose(singleDose, doseUnit, oncePerDay);
        Dose twoDosesOncePerDay = new Dose(twoDoses, doseUnit, oncePerDay);
        Dose oneDoseTwicePerDay = new Dose(singleDose, doseUnit, twicePerDay);
        Dose oneDoseOncePerDayDroplets = new Dose(singleDose, "droplets", oncePerDay);

        // Dose equal to itself
        org.junit.Assert.assertTrue(oneDoseOncePerDay.equals(oneDoseOncePerDay));

        // Dose equal to copy with same values
        org.junit.Assert.assertTrue(oneDoseOncePerDay.equals(oneDoseOncePerDayCopy));

        // Dose not equal to other dose with different dosage
        org.junit.Assert.assertFalse(oneDoseOncePerDay.equals(twoDosesOncePerDay));

        // Dose not equal to other dose with different dosage per day
        org.junit.Assert.assertFalse(oneDoseOncePerDay.equals(oneDoseTwicePerDay));

        // Dose not equal to other dose with different dosage unit
        org.junit.Assert.assertFalse(oneDoseOncePerDay.equals(oneDoseOncePerDayDroplets));
    }
}
