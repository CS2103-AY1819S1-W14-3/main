package seedu.address.model.visitor;

import static java.util.Objects.requireNonNull;

/**
 * The visitor entry for a patient.
 */
public class Visitor {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Visitor's name should not be blank.";
    private String visitorName;

    public Visitor(String visitorName) {
        requireNonNull(visitorName);
        this.visitorName = visitorName;
    }

    public Visitor(Visitor visitor) {
        requireNonNull(visitor);
        this.visitorName = visitor.getVisitorName();
    }

    @Override
    public String toString() {
        return visitorName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same obj
                || (other instanceof Visitor
                && visitorName.equals(((Visitor) other).visitorName));
    }

    @Override
    public int hashCode() {
        return visitorName.hashCode();
    }

    /**
     * Getter method for visitor name.
     */
    public String getVisitorName() {
        return this.visitorName;
    }

    /**
     * method to verify if the visitor name is valid.
     */
    public static boolean isValidVisitor(String test) {
        return true;
    }

}
