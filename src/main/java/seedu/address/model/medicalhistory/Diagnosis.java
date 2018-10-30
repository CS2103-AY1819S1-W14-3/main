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
        checkArgument(isValidDiagnosis(description), MESSAGE_NAME_CONSTRAINTS);
        this.diagnosis = description;
        this.doctorInCharge = doctorName;
        this.timestamp = new Timestamp();
    }

    /**
     * Constructs a {@code Diagnosis} from another {@code Diagnosis}
     *
     * @param d A valid diagnosis.
     *///todo is it required to make diagnosis immutable?
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
     * Returns true if a given string is a valid diagnosis.
     */
    public static boolean isValidDiagnosis(String test) {
        //TODO complete DIAGNOSIS_VALIDATION_REGEX
        //return test.matches(DIAGNOSIS_VALIDATION_REGEX);
        return true;
    }

    /**
     * Returns true if a given string is a valid doctor's name.
     */
    public static boolean isValidDoctor(String test) {
        //todo complete regex
        //return test.matches(DOCTOR_VALIDATION_REGEX);
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
