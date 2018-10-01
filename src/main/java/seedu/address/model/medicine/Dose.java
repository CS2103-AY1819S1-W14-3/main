package seedu.address.model.medicine;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author snajef
/**
 * POJO class for medicine dosage.
 * @author Darien Chong
 *
 */
public class Dose {
    public static final String MESSAGE_DOSAGE_MUST_BE_POSITIVE = "Dosage must be a positive number!";
    public static final String MESSAGE_DOSES_PER_DAY_MUST_BE_POSITIVE_INTEGER = "Doses per day "
            + "must be a positive integer!";

    private double dose;
    private String doseUnit;
    private int dosesPerDay;

    /**
     * C'tor for the Dose object.
     * All fields must be non-null.
     *
     * @param d The dose to give per instance.
     * @param ds The dosage unit e.g. tablets, drops
     * @param dpd The number of doses per day.
     * @throws IllegalValueException when a non-positive dosage is passed to the c'tor.
     */
    public Dose(double d, String ds, int dpd) throws IllegalValueException {
        Objects.requireNonNull(ds);

        if (d <= 0) {
            throw new IllegalValueException(MESSAGE_DOSAGE_MUST_BE_POSITIVE);
        }

        if (dpd <= 0) {
            throw new IllegalValueException(MESSAGE_DOSES_PER_DAY_MUST_BE_POSITIVE_INTEGER);
        }

        dose = d;
        doseUnit = ds;
        dosesPerDay = dpd;
    }

    /**
     * Defensive copy c'tor.
     * @param d The Dose object to defensively copy.
     */
    public Dose(Dose d) {
        dose = d.dose;
        doseUnit = d.doseUnit;
        dosesPerDay = d.dosesPerDay;
    }

    @Override
    public String toString() {
        return Double.toString(dose) + " " + doseUnit + " " + fuzzyStringRepresentation(dosesPerDay);
    }

    /**
     * Generates a string representation of the dose per day.
     * Representation differs based on the dose quantity.
     *
     * @param dpd The dose per day.
     * @return A String representation of the dose per day.
     */
    public static String fuzzyStringRepresentation(int dpd) {
        switch (dpd) {
        case 1:
            return "once a day.";

        case 2:
            return "twice a day.";

        case 3:
            return "thrice a day.";

        default:
            return dpd + " times a day.";
        }
    }

    public double getDose() {
        return dose;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public int getDosesPerDay() {
        return dosesPerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Dose) {
            Dose d = (Dose) o;
            return dose == d.dose && doseUnit.equals(d.doseUnit) && dosesPerDay == d.dosesPerDay;
        }

        return false;
    }
}
