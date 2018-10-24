package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author snajef
/**
 * Represents a Person's NRIC in the address book. Guarantees: immutable; is
 * valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_NAME_CONSTRAINTS = "NRICs should consist of a starting letter (capital), "
        + "followed by 7 numerical digits, followed by a letter (again capital). Please check that the NRIC "
        + "entered is valid, and try again.";

    public static final String NRIC_VALIDATION_REGEX = "(?<nric>[S|T|F|G][0-9]{7}[A-Z])";

    public final String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_NAME_CONSTRAINTS);
        this.nric = nric;
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        // TODO: Implement NRIC validation algorithm?
        // see: http://www.ngiam.net/NRIC/ppframe.htm
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                        && nric.equals(((Nric) other).nric)); // state check
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }

}
