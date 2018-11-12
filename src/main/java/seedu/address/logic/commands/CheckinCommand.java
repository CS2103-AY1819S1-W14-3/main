package seedu.address.logic.commands;

//@@author yuntongzhang

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Checks in a patient whose records already exist in the HealthBase system.
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

    private final Nric patientNric;

    /**
     * Creates a CheckinCommand to check in an pre-existing checked out patient to the system.
     * @param patientNric NRIC of the patient to be checked in.
     */
    public CheckinCommand(Nric patientNric) {
        this.patientNric = requireNonNull(patientNric);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person patientToCheckIn = CommandUtil.getCheckedOutPatient(patientNric, model);
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
