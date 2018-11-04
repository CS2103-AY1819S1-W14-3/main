package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.visitor.VisitorList;


//@@author GAO JIAXIN666
/**
 * View the visitor list of the requested patient
 */
public class ViewvisitorsCommand extends Command {
    public static final String COMMAND_WORD = "viewvisitors";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views a registered patient's all visitors."
            + "Parameters: "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A";
    public static final String MESSAGE_UNREGISTERED = "Person %1$s is not registered within the system.\n";
    public static final String MESSAGE_NO_VISITORS = "Patient %1$s has no existing visitors at present";
    public static final String MESSAGE_SUCCESS = "Displaying patient %s's visitors: \n %s \n";

    private final Nric patientNric;

    public ViewvisitorsCommand(Nric patientNric) {
        requireNonNull(patientNric);
        this.patientNric = patientNric;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> filteredByNric = model.getFilteredPersonList()
                .filtered(p -> patientNric.equals(p.getNric()));

        if (filteredByNric.size() < 1) {
            throw new CommandException(MESSAGE_UNREGISTERED);
        }

        Person selectedPatient = filteredByNric.get(0);
        VisitorList patientVisitorList = selectedPatient.getVisitorList();


        if (patientVisitorList.getSize() == 0) {
            return new CommandResult(String.format(MESSAGE_NO_VISITORS, patientNric));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, selectedPatient.getNric(), patientVisitorList));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewvisitorsCommand
                && patientNric.equals(((ViewvisitorsCommand) other).patientNric));
    }
}
