package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XmlAdaptedMedicalHistoryTest {
    private XmlAdaptedMedicalHistory xmlAdaptedMedicalHistory;
    private XmlAdaptedDiagnosis xmlAdaptedDiagnosis;

    @Before
    public void setUp() {
        xmlAdaptedMedicalHistory = new XmlAdaptedMedicalHistory();
        xmlAdaptedDiagnosis = new XmlAdaptedDiagnosis(
                XmlAdaptedDiagnosisTest.VALID_DIAGNOSIS_DESC,
                XmlAdaptedDiagnosisTest.VALID_DOCTOR_NAME,
                XmlAdaptedDiagnosisTest.VALID_TIMESTAMP);
    }

    //edit this
    @Test
    public void setMedicalHistory() {
        List<XmlAdaptedDiagnosis> newList = new ArrayList<>();
        newList.add(xmlAdaptedDiagnosis);

        xmlAdaptedMedicalHistory.setMedicalHistory(newList);
        assertTrue(xmlAdaptedMedicalHistory.equals(new XmlAdaptedMedicalHistory(newList)));
    }

    @Test
    public void equals_itself_returnsTrue() {
        assertTrue(xmlAdaptedMedicalHistory.equals(xmlAdaptedMedicalHistory));
    }

    @Test
    public void equals_defensiveCopy_returnsTrue() {
        assertTrue(xmlAdaptedMedicalHistory.equals(new XmlAdaptedMedicalHistory(xmlAdaptedMedicalHistory)));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(xmlAdaptedMedicalHistory.equals(1));
    }

}
