package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.visitor.Visitor;
import seedu.address.testutil.PersonBuilder;

//@@author GAO JIAXIN666
public class VisitorInCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void contructor_nullVisitor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Person validPerson = new PersonBuilder().build();
        new VisitorinCommand(validPerson.getNric(), null);
    }

    @Test
    public void contructor_nullPatientNric_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        Visitor validPersonRecord = new Visitor("JACK");
        new VisitorinCommand(null, validPersonRecord);
    }

    @Test
    public void execute_patientVisitorAcceptedByModel_addSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Nric validPersonNric = validPerson.getNric();
        Visitor validPersonRecord = new Visitor("JACK");

        ModelStubWithRegisteredPatient modelStub = new ModelStubWithRegisteredPatient(validPerson);
        CommandResult commandResult = new VisitorinCommand(validPersonNric, validPersonRecord)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(VisitorinCommand.MESSAGE_SUCCESS, validPersonNric), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Person jack = new PersonBuilder().withNric("S1234567A").build();
        Person daniel = new PersonBuilder().withNric("S1234567B").build();
        Visitor jackVisitor = new Visitor("GAO JIAXIN");
        Visitor danielVisitor = new Visitor("ENOCH");
        VisitorinCommand addJackVisitorCommand = new VisitorinCommand(jack.getNric(), jackVisitor);
        VisitorinCommand addDanielVisitorCommand = new VisitorinCommand(jack.getNric(), danielVisitor);

        // same object -> returns true
        assertTrue(addJackVisitorCommand.equals(addJackVisitorCommand));

        // same values -> returns true
        VisitorinCommand addJackVisitorCommandCopy = new VisitorinCommand(jack.getNric(), jackVisitor);
        assertTrue(addJackVisitorCommand.equals(addJackVisitorCommandCopy));

        // different types -> returns false
        assertFalse(addJackVisitorCommand.equals(0));

        // null -> returns false
        assertFalse(addJackVisitorCommand.equals(null));

        // different person -> returns false
        assertFalse(addJackVisitorCommand.equals(addDanielVisitorCommand));

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
    }

    /**
     * A Model stub that contains a registered patient
     */
    private class ModelStubWithRegisteredPatient extends ModelStub {
        private Person patient;
        private final ArrayList<Person> patientsAdded = new ArrayList<>();

        ModelStubWithRegisteredPatient(Person patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.patient.isSamePerson(person);
        }


        @Override
        public void addPerson(Person patient) {
            requireNonNull(patient);
            patientsAdded.add(patient);
        }

        @Override
        public void updatePerson(Person target, Person editedPatient) {
            requireAllNonNull(target, editedPatient);
            assertTrue(target.isSamePerson(editedPatient));
            this.patient = editedPatient;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            // is called by {@code AddmhCommand#execute()}
            ObservableList<Person> patients = FXCollections.observableArrayList();
            patients.add(patient); // take it that person already exists

            FilteredList<Person> filteredPatients = new FilteredList<>(patients);
            return FXCollections.unmodifiableObservableList(filteredPatients);
        }
    }

}
