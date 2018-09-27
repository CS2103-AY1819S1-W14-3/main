package seedu.address.model.medicine;

import seedu.address.commons.util.CollectionUtil;

//@@author snajef
/**
 * POJO class to hold information about medicine prescriptions.
 * @author Darien Chong
 *
 */
public class Prescription {
    private static final String DIVIDER = " | ";

    /** The name of the medication. */
    private String medName;

    /** Dose to administer. */
    private Dose dose;

    /** Duration of the prescription in milliseconds. */
    private Duration duration;

    public Prescription(String name, Dose d, Duration dur) {
        CollectionUtil.requireAllNonNull(name, d, dur);
        medName = name;
        dose = d;
        duration = dur;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(medName).append(DIVIDER).append(dose.toString()).append(DIVIDER).append(duration.toString());

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Prescription) {
            Prescription m = (Prescription) o;
            return medName.equals(m.medName) && dose.equals(m.dose) && duration.equals(m.duration);
        }

        return false;
    }

    /**
     * Getter method for the drug name.
     */
    public String getDrugName() {
        return medName;
    }

    /**
     * Getter method for the Dose.
     */
    public Dose getDose() {
        return dose;
    }

    /**
     * Getter method for the Duration.
     */
    public Duration getDuration() {
        return duration;
    }
}
