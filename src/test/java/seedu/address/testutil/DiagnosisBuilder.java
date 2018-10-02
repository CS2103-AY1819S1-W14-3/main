package seedu.address.testutil;

import seedu.address.model.medicalhistory.Diagnosis;

//todo unstage this file

/**
 * A builder class for a diagnosis.
 */
public class DiagnosisBuilder {
    private static final String DEFAULT_DIAGNOSIS = "Patient has acute bronchitis.";

    private String diagnosis;

    public DiagnosisBuilder() {
        diagnosis = DEFAULT_DIAGNOSIS;
    }

    /**
     * Constructs a new diagnosis based on another diagnosis.
     */
    public DiagnosisBuilder(Diagnosis d) {
        diagnosis = d.getDiagnosis();
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
        return new Diagnosis(diagnosis); //todo param naming can be improved
    }

}
