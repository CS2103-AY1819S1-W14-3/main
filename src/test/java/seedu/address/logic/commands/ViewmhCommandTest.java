package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PersonBuilder;


//@@ omegafishy
public class ViewmhCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPatientNric_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        Person validPerson = new PersonBuilder().withMedicalHistory(SampleDataUtil.getSampleMedicalHistory()).build();
        new ViewmhCommand(null);
    }

    @Test
    public void execute_unregisteredPatient_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build();
        ModelStubWithRegisteredPatient modelStub = new ModelStubWithRegisteredPatient(validPerson);

        Person diffValidPerson = new PersonBuilder().withNric("S9121222A").withName("Zhang Xin Ze").build();
        ViewmhCommand viewmhCommand = new ViewmhCommand(diffValidPerson.getNric());

        thrown.expect(CommandException.class);
        thrown.expectMessage(ViewmhCommand.MESSAGE_UNREGISTERED);
        viewmhCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_patientNoRecordAvailable_viewSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        ModelStubWithRegisteredPatient modelStub = new ModelStubWithRegisteredPatient(validPerson);
        CommandResult commandResult = new ViewmhCommand(validPerson.getNric()).execute(modelStub, commandHistory);

        assertEquals(String.format(ViewmhCommand.MESSAGE_NO_ENTRIES, validPerson.getNric()),
                commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_patientRecordAvailable_viewSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().withMedicalHistory(SampleDataUtil.getSampleMedicalHistory()).build();
        ModelStubWithRegisteredPatient modelStub = new ModelStubWithRegisteredPatient(validPerson);
        CommandResult commandResult = new ViewmhCommand(validPerson.getNric()).execute(modelStub, commandHistory);

        assertEquals(String.format(ViewmhCommand.MESSAGE_SUCCESS, validPerson.getNric(),
                validPerson.getMedicalHistory()), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").withNric("S9161481A").build();

        Person updatedAlice = new PersonBuilder().withMedicalHistory(SampleDataUtil.getSampleMedicalHistory()).build();
        ViewmhCommand viewAliceMhCommand = new ViewmhCommand(alice.getNric());
        ViewmhCommand viewBobMhCommand = new ViewmhCommand(bob.getNric());
        ViewmhCommand viewUpdatedAliceMhCommand = new ViewmhCommand(updatedAlice.getNric());

        // same object -> returns true
        assertTrue(viewAliceMhCommand.equals(viewAliceMhCommand));

        // same values, same medical history
        ViewmhCommand viewUpdatedAliceMhCommandCopy = new ViewmhCommand(updatedAlice.getNric());
        assertTrue(viewUpdatedAliceMhCommand.equals(viewUpdatedAliceMhCommandCopy));

        // same values, empty medical history -> returns true
        ViewmhCommand viewAliceMhCommandCopy = new ViewmhCommand(alice.getNric());
        assertTrue(viewAliceMhCommand.equals(viewAliceMhCommandCopy));

        // different types -> returns false
        assertFalse(viewAliceMhCommand.equals(1));

        // null -> returns false
        assertFalse(viewAliceMhCommand.equals(null));

        // different person -> returns false
        assertFalse(viewAliceMhCommand.equals(viewBobMhCommand));

    }
    /**
     * A Model stub that contains a registered patient
     */
    private class ModelStubWithRegisteredPatient extends CommandTestUtil.ModelStub {
        private Person patient;

        ModelStubWithRegisteredPatient(Person patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            // is called by {@code ViewmhCommand#execute()}
            ObservableList<Person> patients = FXCollections.observableArrayList();
            patients.add(patient);

            FilteredList<Person> filteredPatients = new FilteredList<>(patients);
            return FXCollections.unmodifiableObservableList(filteredPatients);
        }
    }
}
