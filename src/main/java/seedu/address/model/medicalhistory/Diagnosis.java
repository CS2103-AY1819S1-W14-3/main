package seedu.address.model.medicalhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * The medical diagnosis entry for a patient.
 */
public class Diagnosis {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Diagnosis should not be blank, should include doctor's"
            + "signature, data and time specified."; // TODO specify more accurate requirements
    public static final String DIAGNOSIS_VALIDATION_REGEX = ".";

    private String diagnosis;

    /**
     * Constructs a {@code Diagnosis}
     *
     * @param diagnosis A valid diagnosis description.
     */
    public Diagnosis(String diagnosis) {
        requireNonNull(diagnosis);
        checkArgument(isValidDiagnosis(diagnosis), MESSAGE_NAME_CONSTRAINTS);
        this.diagnosis = diagnosis;
    }

    /**
     * Constructs a {@code Diagnosis} from another {@code Diagnosis}
     *
     * @param d A valid diagnosis.
     */
    public Diagnosis(Diagnosis d) {
        requireNonNull(d);
        this.diagnosis = d.getDiagnosis();
    }

    /**
     * Returns true if a given string is a valid diagnosis.
     */
    public static boolean isValidDiagnosis(String test) {
        //TODO complete DIAGNOSIS_VALIDATION_REGEX
        //return test.matches(DIAGNOSIS_VALIDATION_REGEX);
        return true;
    }

    @Override
    public String toString() {
        return diagnosis;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same obj
                || (other instanceof Diagnosis
                    && diagnosis.equals(((Diagnosis) other).diagnosis));
    }

    @Override
    public int hashCode() {
        return diagnosis.hashCode();
    }

    /**
     * Getter method for diagnosis.
     */
    public String getDiagnosis() {
        return this.diagnosis;
    }
}
