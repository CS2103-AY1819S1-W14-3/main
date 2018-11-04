package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;

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
import seedu.address.model.visitor.Visitor;
import seedu.address.model.visitor.VisitorList;
import seedu.address.testutil.PersonBuilder;

//@@author GAO JIAXIN666
public class VisitoroutCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private VisitorList visitorList1 = new VisitorList();
    private Visitor list1Visitor = new Visitor("Amy");
    private VisitorList visitorList2 = new VisitorList();
    private Visitor list2Visitor = new Visitor("Alice");
    private Person patient;
    private Nric patientNric;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        patient = new PersonBuilder().build();
        patientNric = patient.getNric();
        visitorList1.add(list1Visitor);
        visitorList2.add(list2Visitor);
    }

    @Test
    public void contructor_nullVisitor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new VisitorinCommand(patient.getNric(), null);
    }

    @Test
    public void contructor_nullPatientNric_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new VisitorinCommand(null, list1Visitor);
    }

    @Test
    public void execute_visitoroutToExisitngPatient_visitoroutSuccessful() throws CommandException {
        ModelStubAcceptingVisitorout modelStub = new ModelStubAcceptingVisitorout(patient);
        CommandResult commandResult = new VisitoroutCommand(patientNric, list1Visitor)
                .execute(modelStub, commandHistory);
        assertEquals(commandResult.feedbackToUser, String.format(VisitoroutCommand.MESSAGE_NO_VISITORS, patientNric));
    }

    @Test
    public void execute_visitoroutToNonexistentPatient_throwsCommandException() throws Exception {
        Person patientNotInModel = new PersonBuilder().withNric(VALID_NRIC_BOB).build();
        VisitoroutCommand addDietCommand = new VisitoroutCommand(patientNotInModel.getNric(), list1Visitor);
        ModelStubAcceptingVisitorout modelStub = new ModelStubAcceptingVisitorout(patient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(VisitoroutCommand.MESSAGE_UNREGISTERED);
        addDietCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person bob = new PersonBuilder().withVisitorList(visitorList1).withNric("S7654321A").build();
        Person alice = new PersonBuilder().withVisitorList(visitorList2).withNric("S8765432A").build();

        VisitoroutCommand visitoroutCommandBob = new VisitoroutCommand(bob.getNric(), list1Visitor);
        VisitoroutCommand visitoroutCommandAlice = new VisitoroutCommand(alice.getNric(), list2Visitor);

        // same object -> returns true
        assertTrue(visitoroutCommandBob.equals(visitoroutCommandBob));

        // different visitorlist
        assertFalse(visitoroutCommandBob.equals(visitoroutCommandAlice));

        // return false, compared wiht null
        assertFalse(visitoroutCommandAlice.equals(null));
    }

    /**
     * A Model stub that always accepts visitorout commands for a single person.
     */
    private class ModelStubAcceptingVisitorout extends CommandTestUtil.ModelStub {
        private Person patient;

        public ModelStubAcceptingVisitorout(Person patient) {
            this.patient = patient;
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
            patient = updatedPerson;
        }
    }
}
