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
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Type;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Test driver class for AddApptCommand
 */
public class AddApptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String validNric;
    private Type type;
    private String procedure;
    private String dateTime;
    private String invalidDateTime;
    private String doctor;
    private Appointment appt;
    private Person patient;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        validNric = "S8888888A";
        patient = new Person(new Nric(validNric), new Name("AddAppt Test"), new Phone("91234567"),
                new Email("addappttest@gmail.com"), new Address("12 Addappt Ave, #01-01"), new HashSet<Tag>());
        type = Type.SURGICAL;
        procedure = "Heart Bypass";
        dateTime = "12-12-2022 10:30";
        invalidDateTime = "12-12-1000 23:30";
        doctor = "Dr. Pepper";
        appt = new Appointment(type, procedure, dateTime, doctor);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullNric_throwsNullPointerEception() {
        Command command = new AddApptCommand(null, appt);
    }

    @Test
    public void execute_addapptToExistingPatient_addapptSuccessful() throws CommandException {
        ModelStubAcceptingAddappt modelStub = new ModelStubAcceptingAddappt(patient);
        CommandResult commandResult = new AddApptCommand(patient.getNric(), appt).execute(modelStub,
                commandHistory);
        assertEquals(String.format(AddApptCommand.MESSAGE_SUCCESS, patient.getNric()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_addapptToNonexistentPatient_throwsCommandException() throws Exception {
        Person patientNotInModel = new PersonBuilder().build();
        AddApptCommand addApptCommand = new AddApptCommand(patientNotInModel.getNric(), appt);
        ModelStub modelStub = new ModelStubAcceptingAddappt(patient);
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddApptCommand.MESSAGE_NO_SUCH_PATIENT);
        addApptCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_addapptWithInvalidDateTime_throwsCommandException() throws Exception {
        appt = new Appointment(type, procedure, invalidDateTime, doctor);
        AddApptCommand addApptCommand = new AddApptCommand(patient.getNric(), appt);
        ModelStub modelStub = new ModelStubAcceptingAddappt(patient);
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddApptCommand.MESSAGE_DATE_TIME_INVALID);
        addApptCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withNric("S2345678B").withName("Bob").build();
        Appointment diffAppt = new Appointment(type, "Heat Bypass", dateTime, doctor);

        AddApptCommand addApptAliceApptCommand = new AddApptCommand(alice.getNric(), appt);
        AddApptCommand addApptBobApptCommand = new AddApptCommand(bob.getNric(), appt);
        AddApptCommand addApptAliceDiffApptCommand = new AddApptCommand(alice.getNric(), diffAppt);

        // same object -> returns true
        assertTrue(addApptAliceApptCommand.equals(addApptAliceApptCommand));

        // same values -> returns true
        AddApptCommand addapptAliceApptCommandCopy = new AddApptCommand(alice.getNric(), appt);
        assertTrue(addapptAliceApptCommandCopy.equals(addapptAliceApptCommandCopy));

        // different types -> returns false
        assertFalse(addApptAliceApptCommand.equals(1));

        // null -> returns false
        assertFalse(addApptAliceApptCommand.equals(null));

        // different person, same prescription -> returns false
        assertFalse(addApptAliceApptCommand.equals(addApptBobApptCommand));

        // same patient, different prescription -> returns false
        assertFalse(addApptAliceApptCommand.equals(addApptAliceDiffApptCommand));
    }

    /**
     * A default model stub that has all of the methods failing
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
    }

    /**
     * A Model stub that always accepts addappt commands for a single person.
     */
    private class ModelStubAcceptingAddappt extends ModelStub {
        private Person patient;

        public ModelStubAcceptingAddappt(Person patient) {
            this.patient = patient;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.patient.isSamePerson(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
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
