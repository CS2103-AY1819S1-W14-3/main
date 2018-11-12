package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Contains utility methods used for executing commands in the various *Command classes.
 */
public class CommandUtil {

    public static final String MESSAGE_ALREADY_CHECKED_OUT = "The patient with this NRIC was checked out before. "
                                                             + "Please check in this patient before performing this "
                                                             + "operation.";
    public static final String MESSAGE_NO_SUCH_PATIENT = "No such patient was registered before.";
    public static final String MESSAGE_MULTIPLE_PATIENTS = "Multiple such patients are registered in the system. "
                                                           + "Please contact the system administrator.";

    public static final String MESSAGE_ALREADY_CHECKED_IN = "Patient %1$s is already checked in.";
    public static final String MESSAGE_RECORD_NOT_FOUND = "Record for patient %1$s not found in the system.\n"
                                                          + "Please use register command to register this new patient.";

    public static final String MESSAGE_ALREADY_REGISTERED = "A patient with this NRIC is already registered and "
                                                            + "checked in.";
    public static final String MESSAGE_REGISTERED_AND_CHECKED_OUT = "A patient with this NRIC is already registered, "
                                                                    + "but was previously checked out. \n"
                                                                    + "Please use the checkin command to check in this "
                                                                    + "patient instead of registering him/her again.";

    /**
     * Gets the checked in patient from the model.
     * This method also throws several Exceptions if the patient cannot be retrieved from the model.
     *
     * @param nric The NRIC of the patient (should be unique to the patient).
     * @param model The backing model to query.
     * @return The patient in the model.
     * @throws CommandException if the patient is checked out, or there are no/multiple patients registered in the
     * system matching the NRIC given.
     */
    public static Person getPatient(Nric nric, Model model) throws CommandException {
        ObservableList<Person> matchedCheckedOutPatients = getFilteredCheckedOutPatientsByNric(nric, model);

        if (matchedCheckedOutPatients.size() > 0) {
            throw new CommandException(MESSAGE_ALREADY_CHECKED_OUT);
        }

        ObservableList<Person> matchedCheckedInPatients = getFilteredPatientsByNric(nric, model);

        if (matchedCheckedInPatients.size() < 1) {
            throw new CommandException(MESSAGE_NO_SUCH_PATIENT);
        }

        if (matchedCheckedInPatients.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_PATIENTS);
        }

        return matchedCheckedInPatients.get(0);
    }

    /**
     * Gets the checked out patient when checking in that patient.
     * This method also throws several Exceptions if the patient cannot be retrieved from the model.
     *
     * @param nric The NRIC of the patient (should be unique to the patient).
     * @param model The backing model to query.
     * @return The checked out patient in the model.
     * @throws CommandException if the patient is already checked in, or if the patient record is not stored before.
     */
    public static Person getCheckedOutPatient(Nric nric, Model model) throws CommandException {
        ObservableList<Person> matchedCheckedInPatients = getFilteredPatientsByNric(nric, model);

        if (matchedCheckedInPatients.size() > 0) {
            throw new CommandException(String.format(MESSAGE_ALREADY_CHECKED_IN, nric));
        }

        ObservableList<Person> matchedCheckedOutPatients = getFilteredCheckedOutPatientsByNric(nric, model);

        if (matchedCheckedOutPatients.size() < 1) {
            throw new CommandException(String.format(MESSAGE_RECORD_NOT_FOUND, nric));
        }

        return matchedCheckedOutPatients.get(0);
    }

    /**
     * Verifies the validity to register the {@code patient} with the given {@code model}.
     * This method also throws several Exceptions if the patient cannot be registered to the model.
     *
     * @param patient The patient being registered.
     * @param model The backing model.
     * @throws CommandException If the patient was previously registered, or was registered and checked out.
     */
    public static void checkValidRegister(Person patient, Model model) throws CommandException {
        if (model.hasPerson(patient)) {
            throw new CommandException(MESSAGE_ALREADY_REGISTERED);
        }

        if (model.hasCheckedOutPerson(patient)) {
            throw new CommandException(MESSAGE_REGISTERED_AND_CHECKED_OUT);
        }
    }

    /**
     * Gets the patients list from {@code model} filtered by the given {@code nric}.
     */
    private static ObservableList<Person> getFilteredPatientsByNric(Nric nric, Model model) {
        return model.getFilteredPersonList().filtered(p -> nric.equals(p.getNric()));
    }

    /**
     * Gets the checked out patients list from {@code model} filtered by the given {@code nric}.
     */
    private static ObservableList<Person> getFilteredCheckedOutPatientsByNric(Nric nric, Model model) {
        return model.getFilteredCheckedOutPersonList().filtered(p -> nric.equals(p.getNric()));
    }
}
