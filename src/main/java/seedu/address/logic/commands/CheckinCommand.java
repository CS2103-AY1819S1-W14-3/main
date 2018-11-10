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
 * Check in a patient whose records already exist in the HealthBase system.
 * @author yuntongzhang
 */
public class CheckinCommand extends Command {
    public static final String COMMAND_WORD = "checkin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Check in a patient whose information was "
                                               + "previously stored in the system. "
                                               + "Parameters: "
                                               + PREFIX_NRIC + "NRIC\n"
                                               + "Example " + COMMAND_WORD + " "
                                               + PREFIX_NRIC + "S1234567A ";

    public static final String MESSAGE_SUCCESS = "Patient %1$s has been successfully checked in.";
    public static final String MESSAGE_ALREADY_CHECKED_IN = "Patient %1$s is already checked in.";
    public static final String MESSAGE_RECORD_NOT_FOUND = "Record for patient %1$s not found in the system.\n"
                                                        + "Please use register command to register this new patient.";

    private final Nric patientNric;

    /**
     * Creates a CheckinCommand to check in an pre-existing patient to the system.
     * @param patientNric NRIC of the patient to be checked in.
     */
    public CheckinCommand(Nric patientNric) {
        this.patientNric = requireNonNull(patientNric);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> filteredPersonByNric = model.getFilteredPersonList()
            .filtered(p -> patientNric.equals(p.getNric()));

        ObservableList<Person> filteredCheckedOutByNric = model.getFilteredCheckedOutPersonList()
            .filtered(p -> patientNric.equals(p.getNric()));

        if (filteredPersonByNric.size() > 0) {
            throw new CommandException(String.format(MESSAGE_ALREADY_CHECKED_IN, patientNric));
        }

        if (filteredCheckedOutByNric.size() < 1) {
            throw new CommandException(String.format(MESSAGE_RECORD_NOT_FOUND, patientNric));
        }

        Person patientToCheckIn = filteredCheckedOutByNric.get(0);
        model.reCheckInPerson(patientToCheckIn);
        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CheckinCommand)) {
            return false;
        }

        CheckinCommand otherCheckinCommand = (CheckinCommand) other;
        return otherCheckinCommand.patientNric.equals(patientNric);
    }
}
