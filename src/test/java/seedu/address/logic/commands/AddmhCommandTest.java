package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PersonBuilder;

//@@ omegafishy
public class AddmhCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void contructor_nullDiagnosis_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Person validPerson = new PersonBuilder().build();
        new AddmhCommand(validPerson.getNric(), null);
    }

    @Test
    public void contructor_nullPatientNric_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        Person validPerson = new PersonBuilder().withMedicalHistory(SampleDataUtil.getSampleMedicalHistory()).build();
        Diagnosis validPersonRecord = new Diagnosis("Prescribed patient with 2g paracetamol for slight fever",
                "Dr. Zhang");
        new AddmhCommand(null, validPersonRecord);
    }

    @Test
    public void execute_patientRecordAcceptedByModel_addSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Nric validPersonNric = validPerson.getNric();
        Diagnosis validPersonRecord = new Diagnosis("Prescribed patient with 2g paracetamol for slight fever",
                "Dr. Zhang");

        ModelStubWithRegisteredPatient modelStub = new ModelStubWithRegisteredPatient(validPerson);
        CommandResult commandResult = new AddmhCommand(validPersonNric, validPersonRecord)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddmhCommand.MESSAGE_SUCCESS, validPersonNric), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        //TODO assertEquals(DS storing strings, the DS in the model used to store)
    }

    @Test
    public void execute_unregisteredPatient_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build(); //TODO refactor the person to patient
        CommandTestUtil.ModelStub modelStub = new ModelStubWithRegisteredPatient(validPerson);

        Person diffValidPerson = new PersonBuilder().withNric("S9121222A").withName("Zhang Xin Ze").build();
        Diagnosis record = new Diagnosis("Prescribed patient with 2g paracetamol for slight fever",
                "Dr. Ling");
        AddmhCommand addmhCommand = new AddmhCommand(diffValidPerson.getNric(), record);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddmhCommand.MESSAGE_UNREGISTERED);
        addmhCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        Diagnosis aliceDiagnosis = new Diagnosis("Alice has an unknown illness", "Dr. Zhang");
        Diagnosis bobDiagnosis = new Diagnosis("Bob has a chronic knee injury", "Dr Sie");
        AddmhCommand addAliceMhCommand = new AddmhCommand(alice.getNric(), aliceDiagnosis);
        AddmhCommand addBobMhCommand = new AddmhCommand(bob.getNric(), bobDiagnosis);

        // same object -> returns true
        assertTrue(addAliceMhCommand.equals(addAliceMhCommand));

        // same values -> returns true
        AddmhCommand addAliceMhCommandCopy = new AddmhCommand(alice.getNric(), aliceDiagnosis);
        assertTrue(addAliceMhCommand.equals(addAliceMhCommandCopy));

        // different types -> returns false
        assertFalse(addAliceMhCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceMhCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceMhCommand.equals(addBobMhCommand));

        //todo add extra to test for same records
    }

    /**
     * A Model stub that contains a registered patient
     */
    private class ModelStubWithRegisteredPatient extends CommandTestUtil.ModelStub {
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
            //TODO refactor person to patient
            requireNonNull(patient);
            patientsAdded.add(patient); // TODO add record as well?
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
