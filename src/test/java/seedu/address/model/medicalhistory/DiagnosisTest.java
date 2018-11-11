package seedu.address.model.medicalhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DiagnosisTest {
    private final String validDoctorName = "Dr. Zhang";
    private final String invalidDoctorName = "dr.lame";
    private final String invalidDoctorNameWrongTitle = "Mr. Fake Doctor";
    private final String validDiagnosis = "Terrible flu";
    private final String invalidEmptyDiagnosis = "";

    @Test
    public void validDoctorName_returnsTrue() {
        assertTrue(Diagnosis.isValidDoctor(validDoctorName));
    }

    @Test
    public void invalidDoctorName_returnsFalse() {
        assertFalse(Diagnosis.isValidDoctor(invalidDoctorName));
    }

    @Test
    public void invalidDoctorName_wrongTitle_returnsFalse() {
        assertFalse(Diagnosis.isValidDoctor(invalidDoctorNameWrongTitle));
    }

    @Test
    public void validDiagnosis_returnsTrue() {
        assertTrue(Diagnosis.isValidDiagnosis(validDiagnosis));
    }

    @Test
    public void invalidEmptyDiagnosis_returnsFalse() {
        assertFalse(Diagnosis.isValidDiagnosis(invalidEmptyDiagnosis));
    }
}
