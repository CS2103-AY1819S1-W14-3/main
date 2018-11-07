package seedu.address.logic.commands;

//@@ author yuntongzhang

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
 * Test driver for CheckoutCommand class.
 * @author yuntongzhang
 */
public class CheckoutCommandTest {
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
        Assert.assertThrows(NullPointerException.class, () -> new CheckoutCommand(null));
    }

    @Test
    public void execute_checkoutExistingPatient_checkoutSuccessful() throws CommandException {
        ModelStubAcceptingCheckout modelStub = new ModelStubAcceptingCheckout();
        CommandResult commandResult = new CheckoutCommand(patientNric).execute(modelStub, commandHistory);

        assertEquals(commandResult.feedbackToUser, String.format(CheckoutCommand.MESSAGE_SUCCESS, patientNric));
        assertEquals(Arrays.asList(patient), modelStub.checkedOutPersons);
        assertTrue(modelStub.persons.isEmpty());
    }

    @Test
    public void execute_checkoutNonExistingPatient_throwsCommandException() throws Exception {
        ModelStubAcceptingCheckout modelStub = new ModelStubAcceptingCheckout();
        Nric nonExistingNric = DANIEL.getNric();
        CheckoutCommand command = new CheckoutCommand(nonExistingNric);

        thrown.expect(CommandException.class);
        thrown.expectMessage(CheckoutCommand.MESSAGE_NO_SUCH_PATIENT);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person otherPatient = new PersonBuilder().withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB).build();
        CheckoutCommand checkoutCommand = new CheckoutCommand(patientNric);
        CheckoutCommand checkoutCommandCopy = new CheckoutCommand(patientNric);
        RegisterCommand registerCommand = new RegisterCommand(patient);
        CheckoutCommand checkoutOtherNricCommand = new CheckoutCommand(otherPatient.getNric());

        // compare with itself -> returns true
        assertTrue(checkoutCommand.equals(checkoutCommand));

        // compare with a copy of the same command -> returns true
        assertTrue(checkoutCommand.equals(checkoutCommandCopy));

        // compare with a different type of command -> returns false
        assertFalse(checkoutCommand.equals(registerCommand));

        // different Nric -> returns false
        assertFalse(checkoutCommand.equals(checkoutOtherNricCommand));
    }

    /**
     * A Model stub that always accepts checkout commands for a person in the model.
     * By default, this model stub has a valid person in its persons list at the moment of initialization.
     */
    private class ModelStubAcceptingCheckout extends CommandTestUtil.ModelStub {
        final ArrayList<Person> persons;
        final ArrayList<Person> checkedOutPersons;
        private Person patient;

        public ModelStubAcceptingCheckout() {
            this.patient = new PersonBuilder().build();
            this.persons = new ArrayList<>();
            this.checkedOutPersons = new ArrayList<>();

            persons.add(patient);
        }

        @Override
        public void checkOutPerson(Person person) {
            persons.remove(person);
            checkedOutPersons.add(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            // called by {@code CheckoutCommand#execute()}
            ObservableList<Person> patients = FXCollections.observableArrayList();
            patients.add(patient);

            FilteredList<Person> filteredPatients = new FilteredList<>(patients);
            return FXCollections.unmodifiableObservableList(filteredPatients);
        }
    }
}
