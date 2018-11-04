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



//@@author GAO JIAXIN666
/**
 * Checks in a visitor into patient's visitor list.
 */
public class VisitorinCommand extends Command {

    public static final String COMMAND_WORD = "visitorin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks in a visitor into the patient's visitor list \n"
            + "Parameters: "
            + PREFIX_NRIC + "PATIENT_NRIC "
            + PREFIX_VISITOR + "VISITOR_NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_VISITOR + "Jane";

    public static final String MESSAGE_SUCCESS = "New visitor checked in: %1$s";
    public static final String MESSAGE_DUPLICATE_VISITORS = "This person is already in the list";
    public static final String MESSAGE_UNREGISTERED = "Patient %1$s is not registered within the system.";
    public static final String MESSAGE_FULL = "Patient can not has more than 5 visitor in the list";

    private final Nric patientNric;
    private final Visitor visitorName;

    /**
     * Creates an VisitorInCommand to add visitors to patient's visitor's list
     */
    public VisitorinCommand(Nric patientNric, Visitor visitorName) {
        requireNonNull(patientNric);
        requireNonNull(visitorName);
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

        Person patientToUpdate = filteredByNric.get(0);

        if (patientToUpdate.getVisitorList().getSize() >= 5) {
            throw new CommandException(MESSAGE_FULL);
        }

        if (patientToUpdate.getVisitorList().contains(visitorName)) {
            throw new CommandException(MESSAGE_DUPLICATE_VISITORS);
        }

        Person updatedPatient = addVisitorForPatient(patientToUpdate, this.visitorName);

        model.updatePerson(patientToUpdate, updatedPatient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitorinCommand //instanceof handles nulls
                && patientNric.equals(((VisitorinCommand) other).patientNric)
                && visitorName.equals(((VisitorinCommand) other).visitorName));
    }

    /**
     * Updates a patient with new visitor and construct a the person class
     *
     * @param patientToEdit The patient to update.
     * @param newVisitor The newly added visitor
     * @return An updated patient with an updated visitorList.
     */
    private static Person addVisitorForPatient(Person patientToEdit, Visitor newVisitor) {
        requireAllNonNull(patientToEdit, newVisitor);

        VisitorList updatedVisitorList = patientToEdit.getVisitorList();
        updatedVisitorList.add(newVisitor);

        Nric nric = patientToEdit.getNric();
        Name name = patientToEdit.getName();
        Phone phone = patientToEdit.getPhone();
        Email email = patientToEdit.getEmail();
        Address address = patientToEdit.getAddress();
        Set<Tag> tags = patientToEdit.getTags();

        return new Person(nric, name, phone, email, address, tags, updatedVisitorList);
    }
}
