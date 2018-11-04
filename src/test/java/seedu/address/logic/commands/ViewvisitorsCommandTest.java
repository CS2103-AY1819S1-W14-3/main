package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.visitor.Visitor;
import seedu.address.model.visitor.VisitorList;
import seedu.address.testutil.PersonBuilder;

//@@author GAO JIAXIN666
public class ViewvisitorsCommandTest {
    private ExpectedException thrown = ExpectedException.none();
    private VisitorList visitorList1 = new VisitorList();
    private Visitor list1Visitor = new Visitor("Amy");
    private VisitorList visitorList2 = new VisitorList();
    private Visitor list2Visitor = new Visitor("Alice");
    private Person patient;
    private CommandHistory commandHistory = new CommandHistory();
    @Before
    public void setUp() {
        patient = new PersonBuilder().build();
        visitorList1.add(list1Visitor);
        visitorList2.add(list2Visitor);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullNric_throwsNullPointerException() {
        Command command = new ViewvisitorsCommand(null);
    }

    @Test
    public void execute_viewvisitorsToExistingPatient_viewvisitorsSuccessful() throws CommandException {
        ModelStubAcceptingViewvisitors modelStub = new ModelStubAcceptingViewvisitors(patient);
        CommandResult commandResult = new ViewvisitorsCommand(patient.getNric())
                .execute(modelStub, commandHistory);
        Assert.assertEquals(String.format(ViewvisitorsCommand.MESSAGE_NO_VISITORS, patient.getNric()),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_patientRecordAvailable_viewSuccessful() throws Exception {
        VisitorList visitorList = SampleDataUtil.getSampleVisitorList();
        Person validPerson = new PersonBuilder().withVisitorList(visitorList).build();
        ModelStubAcceptingViewvisitors modelStub = new ModelStubAcceptingViewvisitors(validPerson);
        CommandResult commandResult = new ViewvisitorsCommand(validPerson.getNric()).execute(modelStub,
                commandHistory);

        Assert.assertEquals(String.format(ViewvisitorsCommand.MESSAGE_SUCCESS, validPerson.getNric(),
                validPerson.getVisitorList()), commandResult.feedbackToUser);
    }

    @Test
    public void equals() {
        Person bob = new PersonBuilder().withVisitorList(visitorList1).withNric("S7654321A").build();
        Person alice = new PersonBuilder().withVisitorList(visitorList2).build();

        ViewvisitorsCommand viewvisitorsCommandBob = new ViewvisitorsCommand(bob.getNric());
        ViewvisitorsCommand viewvisitorsCommandAlice = new ViewvisitorsCommand(alice.getNric());

        // same object -> returns true
        assertTrue(viewvisitorsCommandAlice.equals(viewvisitorsCommandAlice));

        // same visitorlist for same person
        assertTrue(viewvisitorsCommandAlice.equals(viewvisitorsCommandAlice));

        // different visitorlist
        assertFalse(viewvisitorsCommandAlice.equals(viewvisitorsCommandBob));

        // return false, compared wiht null
        assertFalse(viewvisitorsCommandAlice.equals(null));
    }

    /**
     * A Model stub that always accepts viewvisitor commands for a single person.
     */
    private class ModelStubAcceptingViewvisitors extends CommandTestUtil.ModelStub {
        private Person patient;

        public ModelStubAcceptingViewvisitors(Person patient) {
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
