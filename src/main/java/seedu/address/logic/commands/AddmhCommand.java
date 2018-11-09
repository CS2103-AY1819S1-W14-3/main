package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MED_HISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds a diagnosis to the medical records of an existing patient.
 */
public class AddmhCommand extends Command {

    public static final String COMMAND_WORD = "addmh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patients's medical history. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_MED_HISTORY + "DIAGNOSIS/REMARK"
            + PREFIX_DOCTOR + "DOCTOR\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S9271847A"
            + PREFIX_MED_HISTORY + "Patient has acute terminal stage brain cancer, refer to Dr.Zhang immediately."
            + PREFIX_DOCTOR + "Dr.Ross";

    public static final String MESSAGE_SUCCESS = "New medical history/record successfully added: %1$s";
    public static final String MESSAGE_UNREGISTERED = "Patient %1$s is not registered within the system.";

    private final Nric patientNric;
    private final Diagnosis newRecord;

    /**
     * Creates an AddmhCommand to add the specified {@code Person}'s medical records
     */
    public AddmhCommand(Nric patientNric, Diagnosis newRecord) {
        requireNonNull(patientNric);
        requireNonNull(newRecord);
        this.patientNric = patientNric;
        this.newRecord = newRecord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person patientToUpdate = getPatient(model);
        Person updatedPatient = addMedicalHistoryForPatient(patientToUpdate, this.newRecord);

        model.updatePerson(patientToUpdate, updatedPatient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    // todo refactor, multiple usages in other command methods as well. consider in future
    /**
     * Getter method to get the patient.
     *
     * @param model the model used that stores the data of HMK2K18
     * @return the `Person` with the matching NRIC
     * @throws CommandException if there is no person in the `model` that matches the person's NRIC.
     */
    private Person getPatient(Model model) throws CommandException {
        ObservableList<Person> filteredByNric = model.getFilteredPersonList()
                .filtered(p -> patientNric.equals(p.getNric()));

        if (filteredByNric.size() < 1) {
            throw new CommandException(MESSAGE_UNREGISTERED);
        }

        return filteredByNric.get(0);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddmhCommand //instanceof handles nulls
                && patientNric.equals(((AddmhCommand) other).patientNric)
                && newRecord.equals(((AddmhCommand) other).newRecord));
    }

    /**
     * Updates a patient with new medical history by creating the person and the medical history
     *
     * @param patientToEdit The patient to update.
     * @param diagnosis The diagnosis to be added to patient's existing medical history.
     * @return An updated patient with an updated medical history.
     */
    private static Person addMedicalHistoryForPatient(Person patientToEdit, Diagnosis diagnosis) {
        requireAllNonNull(patientToEdit, diagnosis);

        MedicalHistory updatedMedicalHistory = patientToEdit.getMedicalHistory();
        updatedMedicalHistory.add(diagnosis);

        Nric nric = patientToEdit.getNric();
        Name name = patientToEdit.getName();
        Phone phone = patientToEdit.getPhone();
        Email email = patientToEdit.getEmail();
        Address address = patientToEdit.getAddress();
        Set<Tag> tags = patientToEdit.getTags();

        return new Person(nric, name, phone, email, address, tags, updatedMedicalHistory);
    }

}
