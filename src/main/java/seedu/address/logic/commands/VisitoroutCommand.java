package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITOR;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.Visitor;
import seedu.address.model.visitor.VisitorList;

/**
 * Visitorout command to check out visitor for patient's visitor list
 */
public class VisitoroutCommand extends Command {
    public static final String COMMAND_WORD = "visitorout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": check out visitor form patient visitor list"
            + "Parameters: "
            + PREFIX_NRIC + "PATIENT_NRIC "
            + PREFIX_VISITOR + "VISITOR_NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_VISITOR + "Jane";

    public static final String MESSAGE_UNREGISTERED = "Person %1$s is not registered within the system.\n";
    public static final String MESSAGE_NO_VISITORS = "Patient %1$s has no existing visitors at present";
    public static final String MESSAGE_NO_REQUIRED_VISITOR = "Patient %1$s has no existing visitors at present";
    public static final String MESSAGE_SUCCESS = "visitor is checked out from %1$s";

    private final Nric patientNric;
    private final Visitor visitorName;

    public VisitoroutCommand(Nric patientNric, Visitor visitorName) {
        requireNonNull(patientNric);
        this.patientNric = patientNric;
        this.visitorName = visitorName;
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

        if (!patientVisitorList.contains(visitorName)) {
            return new CommandResult(String.format(MESSAGE_NO_REQUIRED_VISITOR, patientNric));
        }

        Person updatedPatient = removeVisitorForPatient(selectedPatient, this.visitorName);

        model.updatePerson(selectedPatient, updatedPatient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitoroutCommand //instanceof handles nulls
                && patientNric.equals(((VisitoroutCommand) other).patientNric)
                && visitorName.equals(((VisitoroutCommand) other).visitorName));
    }

    /**
     * Updates a patient to remove a visitor from the patient's visitor list
     *
     * @param patientToEdit The patient to update.
     * @param visitor visitor needs to be removed
     * @return An updated patient with an updated visitorList.
     */
    private static Person removeVisitorForPatient(Person patientToEdit, Visitor visitor) {
        requireAllNonNull(patientToEdit, visitor);

        VisitorList updatedVisitorList = patientToEdit.getVisitorList();
        updatedVisitorList.remove(visitor);

        Nric nric = patientToEdit.getNric();
        Name name = patientToEdit.getName();
        Phone phone = patientToEdit.getPhone();
        Email email = patientToEdit.getEmail();
        Address address = patientToEdit.getAddress();
        Set<Tag> tags = patientToEdit.getTags();

        return new Person(nric, name, phone, email, address, tags, updatedVisitorList);
    }
}
