package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code RegisterCommand}.
 */
public class RegisterCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new RegisterCommand(validPerson), model, commandHistory,
                String.format(RegisterCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new RegisterCommand(personInList), model, commandHistory,
                RegisterCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateNric_throwsCommandException() {
        Nric nricInList = model.getAddressBook().getPersonList().get(0).getNric();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("insulin"));
        Person personWithDuplicateNric = new Person(nricInList, new Name("Boon Ping"), new Phone("90000000"),
                new Email("newemail@email.com"), new Address("New Address Road"), tags);
        assertCommandFailure(new RegisterCommand(personWithDuplicateNric), model, commandHistory,
                RegisterCommand.MESSAGE_DUPLICATE_NRIC);
    }

}
