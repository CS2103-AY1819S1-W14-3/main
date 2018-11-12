package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Contains utility methods used for executing commands in the various *Command
 * classes.
 */
public class CommandUtil {

    public static final String MESSAGE_PATIENT_CHECKED_OUT = "The patient with this NRIC was checked out before. "
                                                             + "Please check in this patient before performing this "
                                                             + "operation.";
    public static final String MESSAGE_NO_SUCH_PATIENT = "No such patient was registered before.";
    public static final String MESSAGE_MULTIPLE_PATIENTS = "Multiple such patients are registered in the system. "
                                                           + "Please contact the system administrator.";

    public static final String MESSAGE_ALREADY_CHECKED_IN = "Patient %1$s is already checked in.";
    public static final String MESSAGE_RECORD_NOT_FOUND = "Record for patient %1$s not found in the system.\n"
                                                          + "Please use register command to register this new patient.";


    /**
     * Helper method to get the checked in patient from the Model.
     * This method also checks for several input errors in the command.
     *
     * @param nric The NRIC of the patient (should be unique to the patient)
     * @param model The backing model to query
     * @return The patient in the model
     * @throws CommandException if the patient is checked out, or there are no/multiple patients registered in the
     * system matching the NRIC given.
     */
    public static Person getPatient(Nric nric, Model model) throws CommandException {
        ObservableList<Person> matchedCheckedOutPatients = model.getFilteredCheckedOutPersonList()
            .filtered(p -> nric.equals(p.getNric()));

        if (matchedCheckedOutPatients.size() > 0) {
            throw new CommandException(MESSAGE_PATIENT_CHECKED_OUT);
        }

        ObservableList<Person> matchedCheckedInPatients = model.getFilteredPersonList()
            .filtered(p -> nric.equals(p.getNric()));

        if (matchedCheckedInPatients.size() < 1) {
            throw new CommandException(MESSAGE_NO_SUCH_PATIENT);
        }

        if (matchedCheckedInPatients.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_PATIENTS);
        }

        return matchedCheckedInPatients.get(0);
    }

    /**
     * Helper method to get the checked out patient when checking in that patient.
     * This method also checks for several input errors in the command.
     *
     * @param nric The NRIC of the patient (should be unique to the patient)
     * @param model The backing model to query
     * @return The checked out patient in the model
     * @throws CommandException if the patient is already checked in, or if the patient record is not stored before.
     */
    public static Person getCheckedOutPatient(Nric nric, Model model) throws CommandException {
        ObservableList<Person> matchedCheckedInPatients = model.getFilteredPersonList()
            .filtered(p -> nric.equals(p.getNric()));

        if (matchedCheckedInPatients.size() > 0) {
            throw new CommandException(String.format(MESSAGE_ALREADY_CHECKED_IN, nric));
        }

        ObservableList<Person> matchedCheckedOutPatients = model.getFilteredCheckedOutPersonList()
            .filtered(p -> nric.equals(p.getNric()));

        if (matchedCheckedOutPatients.size() < 1) {
            throw new CommandException(String.format(MESSAGE_RECORD_NOT_FOUND, nric));
        }

        return matchedCheckedOutPatients.get(0);
    }
}
