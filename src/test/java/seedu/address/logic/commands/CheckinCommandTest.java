package seedu.address.logic.commands;

//@@author yuntongzhang

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

/**
 * Test driver for CheckinCommand class.
 * @author yuntongzhang
 */
public class CheckinCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Person patient;
    private Nric patientNric;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        patient = new PersonBuilder().build();
        patientNric = patient.getNric();
    }

    @Test
    public void constructor_nullNric_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CheckinCommand(null));
    }

    @Test
    public void execute_checkinExistingRecord_checkinSuccessful() throws CommandException {
        ModelStubAcceptingCheckin modelStub = new ModelStubAcceptingCheckin();
        CommandResult commandResult = new CheckinCommand(patientNric).execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, String.format(CheckinCommand.MESSAGE_SUCCESS, patientNric));
        assertEquals(Arrays.asList(patient), modelStub.persons);
        assertTrue(modelStub.checkedOutPersons.isEmpty());
    }

    @Test
    public void execute_checkinNonExistingRecord_throwsCommandException() throws Exception {
        ModelStubAcceptingCheckin modelStub = new ModelStubAcceptingCheckin();
        Nric nonExistingNric = DANIEL.getNric();
        CheckinCommand command = new CheckinCommand(nonExistingNric);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(CheckinCommand.MESSAGE_RECORD_NOT_FOUND, nonExistingNric));
        command.execute(modelStub, commandHistory);
    }

    //TODO: another test to check the situation when a person is already checked in.

    @Test
    public void equals() {
        Person otherPatient = new PersonBuilder().withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB).build();
        CheckinCommand checkinCommand = new CheckinCommand(patientNric);
        CheckinCommand checkinCommandCopy = new CheckinCommand(patientNric);
        RegisterCommand registerCommand = new RegisterCommand(patient);
        CheckinCommand checkinOtherNricCommand = new CheckinCommand(otherPatient.getNric());

        // compare with itself -> returns true
        assertTrue(checkinCommand.equals(checkinCommand));

        // compare with a copy of the same command -> returns true
        assertTrue(checkinCommand.equals(checkinCommandCopy));

        // compare with a different type of command -> returns false
        assertFalse(checkinCommand.equals(registerCommand));

        // different Nric -> returns false
        assertFalse(checkinCommand.equals(checkinOtherNricCommand));
    }

    /**
     * A Model stub that always accepts checkin commands for a person recorded in the system.
     * By default, this model stub has a valid person in its checkedOutPersons list at the moment of initialization.
     */
    private class ModelStubAcceptingCheckin extends CommandTestUtil.ModelStub {
        final ArrayList<Person> persons;
        final ArrayList<Person> checkedOutPersons;
        private Person patient;

        public ModelStubAcceptingCheckin() {
            this.patient = new PersonBuilder().build();
            this.persons = new ArrayList<>();
            this.checkedOutPersons = new ArrayList<>();

            checkedOutPersons.add(patient);
        }

        @Override
        public void reCheckInPerson(Person person) {
            checkedOutPersons.remove(person);
            persons.add(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            // called by {@code CheckinCommand#execute()}
            ObservableList<Person> patients = FXCollections.observableArrayList();

            FilteredList<Person> filteredPatients = new FilteredList<>(patients);
            return FXCollections.unmodifiableObservableList(filteredPatients);
        }

        @Override
        public ObservableList<Person> getFilteredCheckedOutPersonList() {
            // called by {@code CheckinCommand#execute()}
            ObservableList<Person> checkedOutPatients = FXCollections.observableArrayList();
            checkedOutPatients.add(patient);

            FilteredList<Person> filteredCheckedOutPatients = new FilteredList<>(checkedOutPatients);
            return FXCollections.unmodifiableObservableList(filteredCheckedOutPatients);
        }
    }
}
