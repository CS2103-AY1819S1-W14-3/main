package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicalhistory.Timestamp;

public class XmlAdaptedDiagnosisTest {
    public static final String VALID_DIAGNOSIS_DESC = "Patient has terminal illnesses.";
    public static final String VALID_DOCTOR_NAME = "Dr. Malfoy";
    public static final Timestamp VALID_TIMESTAMP = new Timestamp();

    private Diagnosis diagnosis;
    private Diagnosis diagnosisWithDifferentTimestamp;

    @Before
    public void setUp() {
        diagnosis = new Diagnosis(VALID_DIAGNOSIS_DESC, VALID_DOCTOR_NAME);
        Timestamp timestamp = diagnosis.getTimestamp();
        LocalDateTime initialLdt = LocalDateTime.parse(timestamp.getTimestamp(), Timestamp.DATE_TIME_FORMATTER);
        LocalDateTime diffTimeLdt = initialLdt.plusHours(10);
        Timestamp diffTimestamp = new Timestamp(diffTimeLdt);
        diagnosisWithDifferentTimestamp = new Diagnosis(VALID_DIAGNOSIS_DESC, VALID_DOCTOR_NAME, diffTimestamp);
    }

    @Test
    public void toModelType_validPersonDetails_returnsDiagnosis() {
        XmlAdaptedDiagnosis xad = new XmlAdaptedDiagnosis(diagnosis);
        assertEquals(diagnosis, xad.toModelType());
    }

    @Test
    public void equals_itself_returnsTrue() {
        XmlAdaptedDiagnosis xad = new XmlAdaptedDiagnosis(diagnosis);
        assertTrue(xad.equals(xad));
    }

    @Test
    public void equals_noArgConstructedXmlAdaptedDiagnosis_returnsFalse() {
        XmlAdaptedDiagnosis xad = new XmlAdaptedDiagnosis(diagnosis);
        XmlAdaptedDiagnosis xadEmpty = new XmlAdaptedDiagnosis();
        assertFalse(xad.equals(xadEmpty));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        XmlAdaptedDiagnosis xad = new XmlAdaptedDiagnosis(diagnosis);
        assertFalse(xad.equals(1));
    }

    @Test
    public void equals_originalAndFromModelTypeCopy_returnsTrue() {
        XmlAdaptedDiagnosis xad = new XmlAdaptedDiagnosis(diagnosis);
        XmlAdaptedDiagnosis xadCopy = new XmlAdaptedDiagnosis(xad.toModelType());

        assertTrue(xad.equals(xadCopy));
    }

    @Test
    public void equals_constructedAtDifferentTimes_returnsFalse() {
        XmlAdaptedDiagnosis xad = new XmlAdaptedDiagnosis(diagnosis);
        XmlAdaptedDiagnosis xadDifferentTimestamp = new XmlAdaptedDiagnosis(diagnosisWithDifferentTimestamp);

        assertFalse(xad.equals(xadDifferentTimestamp));
    }

    @Test
    public void testerror() {
        System.out.println(diagnosis.getTimestamp());
    }

}
