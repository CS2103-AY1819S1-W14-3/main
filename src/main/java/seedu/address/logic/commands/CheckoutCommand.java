package seedu.address.logic.commands;

//@@author yuntongzhang

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Check out a patient from the HealthBase system.
 * @author yuntongzhang
 */
public class CheckoutCommand extends Command {
    public static final String COMMAND_WORD = "checkout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Check out a patient from the system. "
                                               + "Parameters: "
                                               + PREFIX_NRIC + "NRIC\n"
                                               + "Example " + COMMAND_WORD + " "
                                               + PREFIX_NRIC + "S1234567A ";

    public static final String MESSAGE_SUCCESS = "Patient %1$s has been successfully checked out.";
    static final String MESSAGE_NO_SUCH_PATIENT = "No such patient exists.";
    static final String MESSAGE_MULTIPLE_PATIENTS = "Multiple such patients exist. "
                                                           + "Please contact the system administrator.";

    private final Nric patientNric;

    /**
     * Creates a CheckoutCommand to check out a patient from the system.
     * @param patientNric NRIC of the patient to be checked out.
     */
    public CheckoutCommand(Nric patientNric) {
        this.patientNric = requireNonNull(patientNric);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> filteredByNric = model.getFilteredPersonList()
            .filtered(p -> patientNric.equals(p.getNric()));

        if (filteredByNric.size() < 1) {
            throw new CommandException(MESSAGE_NO_SUCH_PATIENT);
        }

        if (filteredByNric.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_PATIENTS);
        }

        Person patientToCheckOut = filteredByNric.get(0);
        model.checkOutPerson(patientToCheckOut);
        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CheckoutCommand)) {
            return false;
        }

        CheckoutCommand otherCheckoutCommand = (CheckoutCommand) other;
        return otherCheckoutCommand.patientNric.equals(patientNric);
    }
}
