package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.testutil.Assert;

public class XmlAdaptedPrescriptionTest {
    private static final double INVALID_DOSAGE = -0.1;
    private static final int INVALID_DOSES_PER_DAY = -5;
    private static final String INVALID_START_DATE = "-";
    private static final String INVALID_END_DATE = "@";
    private static final double INVALID_DURATION_IN_MS = -10;

    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_DRUGNAME = "Artificial Tears";
    private static final double VALID_DOSAGE = 0.2;
    private static final String VALID_DOSE_UNIT = "ml";
    private static final int VALID_DOSES_PER_DAY = 4;
    private static final String VALID_START_DATE = "01-01-1990";
    private static final String VALID_END_DATE = "02-01-1990";
    private static final int VALID_DURATION_IN_DAYS = 1;
    private static final double VALID_DURATION_IN_MS = 86400 * Math.pow(10, 3);

    private Dose dose;
    private Duration duration;
    private Prescription prescription;

    @Before
    public void setUp() throws IllegalValueException {
        dose = new Dose(VALID_DOSAGE, VALID_DOSE_UNIT, VALID_DOSES_PER_DAY);
        duration = new Duration(VALID_DURATION_IN_DAYS);
        prescription = new Prescription(VALID_NRIC, dose, duration);
    }

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(prescription);
        assertEquals(prescription, xmlAdaptedPrescription.toModelType());
    }

    @Test
    public void toModelType_invalidDose_throwsIllegalValueException() throws IllegalValueException {
        XmlAdaptedPrescription prescription = new XmlAdaptedPrescription(VALID_DRUGNAME, INVALID_DOSAGE,
                VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_MS);
        String expectedMessage = Dose.MESSAGE_DOSAGE_MUST_BE_POSITIVE;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, prescription::toModelType);
    }

    @Test
    public void toModelType_invalidDosesPerDay_throwsIllegalValueException() throws IllegalValueException {
        XmlAdaptedPrescription prescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
                VALID_DOSE_UNIT, INVALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_MS);
        String expectedMessage = Dose.MESSAGE_DOSES_PER_DAY_MUST_BE_POSITIVE_INTEGER;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, prescription::toModelType);
    }

    @Test(expected = IllegalValueException.class)
    public void toModelType_invalidStartDate_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
                VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, INVALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_MS);
    }

    @Test(expected = IllegalValueException.class)
    public void toModelType_invalidEndDate_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
                VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, INVALID_END_DATE, VALID_DURATION_IN_MS);
    }

    @Test
    public void toModelType_invalidDurationInMs_throwsIllegalValueException() throws IllegalValueException {
        String expectedMessage = Duration.MESSAGE_DURATION_MUST_BE_POSITIVE;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> new XmlAdaptedPrescription(
                VALID_DRUGNAME, VALID_DOSAGE, VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE,
                INVALID_DURATION_IN_MS));
    }

    @Test
    public void toModelType_endDateBeforeStartDate_throwsIllegalValueException() throws IllegalValueException {
        String expectedMessage = XmlAdaptedPrescription.MESSAGE_END_DATE_MUST_BE_AFTER_START_DATE;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> new XmlAdaptedPrescription(
                VALID_DRUGNAME, VALID_DOSAGE, VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_END_DATE, VALID_START_DATE,
                VALID_DURATION_IN_MS));
    }

    @Test
    public void equals_itselfAndItself_returnsTrue() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
                VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_MS);
        assertTrue(xmlAdaptedPrescription.equals(xmlAdaptedPrescription));
    }

    @Test
    public void equals_differentType_returnsFalse() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
                VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_MS);
        assertFalse(xmlAdaptedPrescription.equals(1));
    }

    @Test
    public void equals_originalAndCopyOfModelType_returnsTrue() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
                VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_MS);
        XmlAdaptedPrescription anotherXmlAdaptedPrescription = new XmlAdaptedPrescription(
                xmlAdaptedPrescription.toModelType());
        assertTrue(xmlAdaptedPrescription.equals(anotherXmlAdaptedPrescription));
    }

    @Test
    public void equals_constructedUsingDifferentValues_returnsFalse() throws IllegalValueException {
        XmlAdaptedPrescription xmlAdaptedPrescription = new XmlAdaptedPrescription(VALID_DRUGNAME, VALID_DOSAGE,
                VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_MS);
        XmlAdaptedPrescription anotherXmlAdaptedPrescription = new XmlAdaptedPrescription("Paracetalol", VALID_DOSAGE,
                VALID_DOSE_UNIT, VALID_DOSES_PER_DAY, VALID_START_DATE, VALID_END_DATE, VALID_DURATION_IN_MS);
        assertFalse(xmlAdaptedPrescription.equals(anotherXmlAdaptedPrescription));
    }
}
