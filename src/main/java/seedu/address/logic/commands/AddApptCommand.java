package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCEDURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

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
}
