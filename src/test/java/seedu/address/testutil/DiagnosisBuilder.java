package seedu.address.testutil;

import seedu.address.model.medicalhistory.Diagnosis;

/**
 * A builder class for a diagnosis.
 */
public class DiagnosisBuilder {
    private static final String DEFAULT_DIAGNOSIS = "Patient has acute bronchitis.";
    private static final String DEFAULT_DOCTOR = "Dr. Zhang";

    private String diagnosis;
    private String doctorInCharge;

    public DiagnosisBuilder() {
        this.diagnosis = DEFAULT_DIAGNOSIS;
        this.doctorInCharge = DEFAULT_DOCTOR;
    }

    /**
     * Setter method for the diagnosis.
     * //todo for future updating, different from constructor
     */
    public DiagnosisBuilder withDiagnosis(Diagnosis d) {
        diagnosis = d.getDiagnosis();
        return this;
    }

    public Diagnosis build() {
        return new Diagnosis(diagnosis, doctorInCharge);
    }

}
