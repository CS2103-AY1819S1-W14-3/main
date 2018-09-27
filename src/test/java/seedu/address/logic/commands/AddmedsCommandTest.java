package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

//@@author snajef
/**
 * Test driver class for AddmedsCommand.
 *
 * @author Darien Chong
 *
 */
public class AddmedsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String validNric;
    private String drugName;
    private Dose dose;
    private Duration duration;
    private Prescription prescription;
    private Person patient;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() throws IllegalValueException {
        /**
         * Valid NRIC here must be something other than PersonBuilder's default NRIC.
         */
        validNric = "S9999999Z";
        patient = new Person(new Nric(validNric), new Name("Addmeds Test"), new Phone("92345678"),
                new Email("addmedstest@mail.com"), new Address("42 Addmeds Ave, #00-00"), new HashSet<Tag>());

        drugName = "Paracetamol";
        dose = new Dose(2, "tablets", 4);
        duration = new Duration(2);
        prescription = new Prescription(drugName, dose, duration);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullNric_throwsNullPointerException() {
        Command command = new AddmedsCommand(null, prescription);
    }

    @Test
    public void execute_addmedsToExistingPatient_addmedsSuccessful() throws CommandException {
        ModelStubAcceptingAddmeds modelStub = new ModelStubAcceptingAddmeds(patient);
        CommandResult commandResult = new AddmedsCommand(patient.getNric(), prescription).execute(modelStub,
                commandHistory);
        assertEquals(String.format(AddmedsCommand.MESSAGE_SUCCESS, patient.getNric()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_addMedToNonexistentPatient_throwsCommandException() throws Exception {
        Person patientNotInModel = new PersonBuilder().build();
        AddmedsCommand addmedsCommand = new AddmedsCommand(patientNotInModel.getNric(), prescription);
        ModelStub modelStub = new ModelStubAcceptingAddmeds(patient);

        // It appears that we can't use the expected parameter in the @Test annotation
        // if we want to expect a certain message as well.
        // Hence the choice of thrown.expect(Exception) over the @Test(expected=Exception) annotation.
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddmedsCommand.MESSAGE_NO_SUCH_PATIENT);
        addmedsCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withNric("S2345678B").withName("Bob").build();
        Prescription differentPrescription = new Prescription("Paracetalol", dose, duration);

        AddmedsCommand addmedsAlicePrescCommand = new AddmedsCommand(alice.getNric(), prescription);
        AddmedsCommand addmedsBobPrescCommand = new AddmedsCommand(bob.getNric(), prescription);
        AddmedsCommand addmedsAliceDiffPrescCommand = new AddmedsCommand(alice.getNric(), differentPrescription);

        // same object -> returns true
        assertTrue(addmedsAlicePrescCommand.equals(addmedsAlicePrescCommand));

        // same values -> returns true
        AddmedsCommand addmedsAlicePrescCommandCopy = new AddmedsCommand(alice.getNric(), prescription);
        assertTrue(addmedsAlicePrescCommand.equals(addmedsAlicePrescCommandCopy));

        // different types -> returns false
        assertFalse(addmedsAlicePrescCommand.equals(1));

        // null -> returns false
        assertFalse(addmedsAlicePrescCommand.equals(null));

        // different person, same prescription -> returns false
        boolean lol = addmedsAlicePrescCommand.equals(addmedsBobPrescCommand);
        assertFalse(addmedsAlicePrescCommand.equals(addmedsBobPrescCommand));

        // same patient, different prescription -> returns false
        assertFalse(addmedsAlicePrescCommand.equals(addmedsAliceDiffPrescCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accepts addmeds commands for a single person.
     */
    private class ModelStubAcceptingAddmeds extends ModelStub {
        private Person patient;

        public ModelStubAcceptingAddmeds(Person patient) {
            this.patient = patient;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.patient.isSamePerson(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            // called by {@code AddmedsCommand#execute()}
            ObservableList<Person> patients = FXCollections.observableArrayList();
            patients.add(patient);

            FilteredList<Person> filteredPatients = new FilteredList<>(patients);
            return FXCollections.unmodifiableObservableList(filteredPatients);
        }

        @Override
        public void updatePerson(Person personToUpdate, Person updatedPerson) {
            requireAllNonNull(personToUpdate, updatedPerson);
            if (!personToUpdate.isSamePerson(updatedPerson)) {
                // TODO: what should be an appropriate response?
                assertTrue(false);
                return;
            }

            patient = updatedPerson;
        }
    }
}
