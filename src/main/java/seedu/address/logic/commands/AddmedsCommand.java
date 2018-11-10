package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSES_PER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSE_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUGNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.VisitorList;

//@@author snajef
/**
 * Adds a person to the address book.
 */
public class AddmedsCommand extends Command {
    public static final String COMMAND_WORD = "addmeds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds medication for a patient. "
        + "Parameters: "
        + PREFIX_NRIC + "NRIC "
        + PREFIX_DRUGNAME + "DRUG_NAME "
        + PREFIX_QUANTITY + "QUANTITY_PER_DOSE "
        + PREFIX_DOSE_UNIT + "DOSAGE_UNIT "
        + PREFIX_DOSES_PER_DAY + "DOSES_PER_DAY "
        + PREFIX_DURATION + "DURATION_IN_DAYS\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NRIC + "S1234567A "
        + PREFIX_DRUGNAME + "Paracetamol "
        + PREFIX_QUANTITY + "2 "
        + PREFIX_DOSE_UNIT + "tablets "
        + PREFIX_DOSES_PER_DAY + "4 "
        + PREFIX_DURATION + "14";

    public static final String MESSAGE_SUCCESS = "Medication added for patient: %1$s";
    public static final String MESSAGE_NO_SUCH_PATIENT = "No such patient exists.";
    public static final String MESSAGE_MULTIPLE_PATIENTS = "Multiple such patients exist. "
        + "Please contact the system administrator.";

    private final Prescription med;
    private final Nric patientNric;

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddmedsCommand(Nric patientNric, Prescription med) {
        this.patientNric = requireNonNull(patientNric);
        this.med = requireNonNull(med);;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person patientToUpdate = getPatient(patientNric, model);
        Person updatedPatient = addMedicineForPerson(patientToUpdate, med);

        model.updatePerson(patientToUpdate, updatedPatient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddmedsCommand // instanceof handles nulls
                && patientNric.equals(((AddmedsCommand) other).patientNric)
                && med.equals(((AddmedsCommand) other).med));
    }

    /**
     * Updates a patient with new medicine by recreating the person and the medicine list.
     *
     * @param personToEdit The patient to update.
     * @param m The medicine to update with.
     * @return An updated patient with the appropriate medicine added.
     */
    private static Person addMedicineForPerson(Person personToEdit, Prescription m) {
        assert personToEdit != null;

        PrescriptionList updatedMedicineList = new PrescriptionList(personToEdit.getPrescriptionList());
        updatedMedicineList.add(m);

        Nric nric = personToEdit.getNric();
        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();
        AppointmentsList appointmentsList = personToEdit.getAppointmentsList();
        MedicalHistory medicalHistory = personToEdit.getMedicalHistory();
        DietCollection dietCollection = personToEdit.getDietCollection();
        VisitorList visitorList = personToEdit.getVisitorList();

        return new Person(nric, name, phone, email, address, tags,
            updatedMedicineList, appointmentsList, medicalHistory, dietCollection);
    }

    /**
     * Helper method to get the requested patient from the Model.
     *
     * @param nric The NRIC of the patient (should be unique to the patient)
     * @param model The backing model to query
     * @return The patient in the model
     * @throws CommandException if there are no/multiple patients matching the NRIC given.
     */
    private static Person getPatient(Nric nric, Model model) throws CommandException {
        ObservableList<Person> patientCandidates = model.getFilteredPersonList()
            .filtered(p -> nric.equals(p.getNric()));

        if (patientCandidates.size() < 1) {
            throw new CommandException(MESSAGE_NO_SUCH_PATIENT);
        }

        if (patientCandidates.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_PATIENTS);
        }

        return patientCandidates.get(0);
    }
}
