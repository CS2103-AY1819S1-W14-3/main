package seedu.address.model.medicalhistory;

import static java.util.Objects.requireNonNull;

/**
 * The medical diagnosis entry for a patient.
 */
public class Diagnosis {
    public static final String MESSAGE_NAME_CONSTRAINT_DIAGNOSIS = "Diagnosis should not be blank.";
    public static final String MESSAGE_NAME_CONSTRAINTS_DOCTOR = "Doctor's title should precede his full name, "
            + "which should be spelt out with capitalisation at the start of every new name word.";
    public static final String DOCTOR_VALIDATION_REGEX = "Dr(\\.|\\.\\s|\\s)([A-Z][a-z]+)(\\s[A-Z][a-z]*)*";
    public static final String DIAGNOSIS_VALIDATION_REGEX = ".*\\S.*";

    private final String diagnosis;
    private final String doctorInCharge;
    private final Timestamp timestamp;

    /**
     * Constructs a {@code Diagnosis}
     *
     * @param description A valid diagnosis description.
     */
    public Diagnosis(String description, String doctorName) {
        requireNonNull(description);
        requireNonNull(doctorName);
        this.diagnosis = description;
        this.doctorInCharge = doctorName;
        this.timestamp = new Timestamp();
    }

    /**
     * Constructs a {@code Diagnosis} from another {@code Diagnosis}
     *
     * @param d A valid diagnosis.
     */
    public Diagnosis(Diagnosis d) {
        requireNonNull(d);
        this.diagnosis = d.getDiagnosis();
        this.doctorInCharge = d.getDoctorInCharge();
        this.timestamp = d.getTimestamp();
    }

    /**
     * Constructs a {@code Diagnosis}. Alternative constructor.
     */
    public Diagnosis(String description, String doctorName, Timestamp timestamp) {
        requireNonNull(description);
        requireNonNull(doctorName);
        requireNonNull(timestamp);
        this.diagnosis = description;
        this.doctorInCharge = doctorName;
        this.timestamp = timestamp;
    }

    /**
     * Returns true if a given string is a valid doctor's name.
     */
    public static boolean isValidDoctor(String doctorName) {
        return doctorName.matches(DOCTOR_VALIDATION_REGEX);
    }

    /**
     * Returns true if given string is not an empty description.
     */
    public static boolean isValidDiagnosis(String diagnosis) {
        return diagnosis.matches(DIAGNOSIS_VALIDATION_REGEX);
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

    /**
     * Getter method for the name of the doctor in charge.
     */
    public String getDoctorInCharge() {
        return this.doctorInCharge;
    }

    /**
     * Getter method for the timestamp of diagnosis.
     */
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
}
