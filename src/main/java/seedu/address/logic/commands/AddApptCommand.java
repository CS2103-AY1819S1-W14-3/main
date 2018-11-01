package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCEDURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.time.LocalDateTime;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds an appointment for a patient
 */
public class AddApptCommand extends Command {
    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds appointment for a patient. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_PROCEDURE + "PROCEDURE_NAME "
            + PREFIX_DATE_TIME + "DATE_TIME "
            + PREFIX_DOCTOR + "DOCTOR-IN-CHARGE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_TYPE + "SRG "
            + PREFIX_PROCEDURE + "Brain transplant "
            + PREFIX_DATE_TIME + "27-04-2019 10:30 "
            + PREFIX_DOCTOR + "Dr. Pepper ";

    public static final String MESSAGE_SUCCESS = "Appointment added for patient: %1$s";
    public static final String MESSAGE_NO_SUCH_PATIENT = "No such patient exists.";
    public static final String MESSAGE_DATE_TIME_INVALID = "Input date and time is invalid or before current date and "
        + "time.";
    public static final String DATE_TIME_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1"
            + "[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1"
            + "\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2}\\s\\d\\d:\\d\\d)$";

    private final Appointment appt;
    private final Nric patientNric;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddApptCommand(Nric patientNric, Appointment appt) {
        this.patientNric = requireNonNull(patientNric);
        this.appt = requireNonNull(appt);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> filteredByNric = model.getFilteredPersonList()
                .filtered(p -> patientNric.equals(p.getNric()));

        if (filteredByNric.size() < 1) {
            throw new CommandException(MESSAGE_NO_SUCH_PATIENT);
        }

        if (!isDateTimeValid(appt.getDate_time())) {
            throw new CommandException(MESSAGE_DATE_TIME_INVALID);
        }

        Person patientToUpdate = filteredByNric.get(0);
        Person updatedPatient = addApptForPerson(patientToUpdate, appt);

        model.updatePerson(patientToUpdate, updatedPatient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddApptCommand // instanceof handles nulls
                && patientNric.equals(((AddApptCommand) other).patientNric)
                && appt.equals(((AddApptCommand) other).appt));
    }

    /**
     * Updates a patient with new appointment by recreating the person and the list of appointments of the patient
     *
     * @param personToEdit The patient to update
     * @param appt The appointment to update with
     * @return An updated patient with an appointment added
     */
    private static Person addApptForPerson(Person personToEdit, Appointment appt) {
        assert personToEdit != null;

        AppointmentsList updatedAppointmentsList = new AppointmentsList(personToEdit.getAppointmentsList());
        updatedAppointmentsList.add(appt);

        Nric nric = personToEdit.getNric();
        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();
        PrescriptionList prescriptionList = personToEdit.getPrescriptionList();

        return new Person(nric, name, phone, email, address, tags, prescriptionList, updatedAppointmentsList);
    }

    /**
     * Checks if date and time input by user is valid
     * @param dateTime date and time input by user
     * @return true if valid
     */
    private static boolean isDateTimeValid(String dateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime apptDateTime = LocalDateTime.parse(dateTime, Appointment.DATE_TIME_FORMAT);
        return dateTime.matches(DATE_TIME_VALIDATION_REGEX) && apptDateTime.isAfter(now);
    }
}
