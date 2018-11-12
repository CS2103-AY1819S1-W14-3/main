package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MED_HISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Adds a diagnosis to the medical records of an existing patient.
 */
public class AddmhCommand extends Command {

    public static final String COMMAND_WORD = "addmh";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a diagnosis to a patient's existing medical history. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_MED_HISTORY + "DIAGNOSIS/REMARK "
            + PREFIX_DOCTOR + "DOCTOR\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A" + " "
            + PREFIX_MED_HISTORY + "Patient has acute terminal stage brain cancer, refer to Dr.Zhang immediately. "
            + PREFIX_DOCTOR + "Dr.Ross";

    public static final String MESSAGE_SUCCESS = "New medical history/record successfully added: %1$s";

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

        Person patientToUpdate = CommandUtil.getPatient(patientNric, model);
        Person updatedPatient = addMedicalHistoryForPatient(patientToUpdate, this.newRecord);

        model.updatePerson(patientToUpdate, updatedPatient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
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

        return patientToEdit.withMedicalHistory(updatedMedicalHistory);
    }

}
