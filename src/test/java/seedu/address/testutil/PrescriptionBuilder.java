package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;

//@@author snajef
/**
 * A utility class for Prescription.
 */
public class PrescriptionBuilder {
    private static final String DEFAULT_DRUGNAME = "Paracetamol";

    /** We don't construct a default Dose/Duration object here because of the IllegalValueException. */
    private static final double DEFAULT_DOSAGE = 2;
    private static final String DEFAULT_DOSE_UNIT = "tablets";
    private static final int DEFAULT_DOSES_PER_DAY = 4;
    private static final int DEFAULT_DURATION_IN_DAYS = 14;

    private String drugName;
    private Dose dose;
    private Duration duration;

    public PrescriptionBuilder() throws IllegalValueException {
        drugName = DEFAULT_DRUGNAME;
        dose = new Dose(DEFAULT_DOSAGE, DEFAULT_DOSE_UNIT, DEFAULT_DOSES_PER_DAY);
        duration = new Duration(DEFAULT_DURATION_IN_DAYS);
    }

    /** Copy constructor. */
    public PrescriptionBuilder(Prescription p) {
        drugName = p.getDrugName();
        dose = new Dose(p.getDose());
        duration = new Duration(p.getDuration());
    }

    /**
     * Setter for drugName.
     * @param drugName The drugName to use.
     * @return this
     */
    public PrescriptionBuilder withDrugName(String drugName) {
        this.drugName = drugName;
        return this;
    }

    /**
     * Setter for Dose.
     * @param d The Dose object to defensively copy and use.
     * @return this
     */
    public PrescriptionBuilder withDose(Dose d) {
        dose = new Dose(d);
        return this;
    }

    /**
     * Setter for Duration.
     * @param d The Duration object to defensively copy and use.
     * @return this
     */
    public PrescriptionBuilder withDuration(Duration d) {
        duration = new Duration(d);
        return this;
    }

    /**
     * Returns a {@code Prescription} object with the given characteristics.
     */
    public Prescription build() {
        return new Prescription(drugName, dose, duration);
    }
}
