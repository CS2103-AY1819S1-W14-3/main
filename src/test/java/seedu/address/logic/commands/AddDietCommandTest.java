package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CULTURAL_REQUIREMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHYSICAL_DIFFICULTY;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.diet.DietType;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

//@@author yuntongzhang

/**
 * Test driver for AddDietCommand class.
 * @author yuntongzhang
 */
public class AddDietCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Person patient;
    private Nric patientNric;
    private Set<Diet> dietSet;
    private DietCollection dietsToAdd;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        patient = new PersonBuilder().build();
        patientNric = patient.getNric();

        dietSet = new HashSet<>();
        dietSet.add(new Diet(VALID_ALLERGY, DietType.ALLERGY));
        dietSet.add(new Diet(VALID_CULTURAL_REQUIREMENT, DietType.CULTURAL));
        dietSet.add(new Diet(VALID_PHYSICAL_DIFFICULTY, DietType.PHYSICAL));
        dietsToAdd = new DietCollection(dietSet);
    }

    @Test
    public void constructor_nullNric_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddDietCommand(null, dietsToAdd));
    }

    @Test
    public void constructor_nullDietsToAdd_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddDietCommand(patientNric, null));
    }

    @Test
    public void execute_adddietToExisitngPatient_adddietSuccessful() throws CommandException {
        ModelStubAcceptingAdddiet modelStub = new ModelStubAcceptingAdddiet(patient);
        CommandResult commandResult = new AddDietCommand(patientNric, dietsToAdd)
                .execute(modelStub, commandHistory);
        assertEquals(commandResult.feedbackToUser, String.format(AddDietCommand.MESSAGE_SUCCESS, patientNric));
    }

    @Test
    public void execute_adddietToNonexistentPatient_throwsCommandException() throws Exception {
        Person patientNotInModel = new PersonBuilder().withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB).build();
        AddDietCommand addDietCommand = new AddDietCommand(patientNotInModel.getNric(), dietsToAdd);
        ModelStubAcceptingAdddiet modelStub = new ModelStubAcceptingAdddiet(patient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddDietCommand.MESSAGE_NO_SUCH_PATIENT);
        addDietCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person otherPatient = new PersonBuilder().withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB).build();
        Set<Diet> otherDietSet = new HashSet<>();
        otherDietSet.add(new Diet(VALID_ALLERGY, DietType.ALLERGY));
        DietCollection otherDietCollection = new DietCollection(otherDietSet);

        AddDietCommand adddietCommand = new AddDietCommand(patientNric, dietsToAdd);
        AddDietCommand adddietCommandCopy = new AddDietCommand(patientNric, dietsToAdd);
        RegisterCommand registerCommand = new RegisterCommand(patient);
        AddDietCommand adddietOtherDietCommand = new AddDietCommand(patientNric, otherDietCollection);
        AddDietCommand adddietOtherPatientCommand = new AddDietCommand(otherPatient.getNric(), dietsToAdd);

        // same AddDietCommand, returns true
        assertTrue(adddietCommand.equals(adddietCommand));

        // compare with a copy of the same command, returns true
        assertTrue(adddietCommand.equals(adddietCommandCopy));

        // compare with a different type of command, returns false
        assertFalse(adddietCommand.equals(registerCommand));

        // same patientNric, different dietsToAdd, return false
        assertFalse(adddietCommand.equals(adddietOtherDietCommand));

        // different patientNric, same dietsToAdd, return false
        assertFalse(adddietCommand.equals(adddietOtherPatientCommand));
    }

    /**
     * A Model stub that always accepts adddiet commands for a single person.
     */
    private class ModelStubAcceptingAdddiet extends CommandTestUtil.ModelStub {
        private Person patient;

        public ModelStubAcceptingAdddiet(Person patient) {
            this.patient = patient;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            // called by {@code AddDietCommand#execute()}
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
