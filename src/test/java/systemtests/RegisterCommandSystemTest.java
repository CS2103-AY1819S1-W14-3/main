package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DRUG_ALLERGY_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.DRUG_ALLERGY_DESC_PENICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DRUG_ALLERGY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class RegisterCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void register() {
        /* ----------------------- Perform register operations on the shown unfiltered list ------------------------- */

        /* Case: register a person to a non-empty HealthBase, command with leading spaces and trailing spaces
         * -> added
         */
        Person toRegister = AMY;
        String command = "   " + RegisterCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                         + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   "
                         + DRUG_ALLERGY_DESC_ASPIRIN + " " + NRIC_DESC_AMY;
        assertCommandSuccess(command, toRegister);

        /* Case: register a person with all fields same as another person in the HealthBase except nric -> added */
        toRegister = new PersonBuilder(AMY).withNric(VALID_NRIC_BOB).build();
        command = RegisterCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DRUG_ALLERGY_DESC_ASPIRIN + NRIC_DESC_BOB;
        assertCommandSuccess(command, toRegister);

        /* Case: register a person with all fields same as another person in the HealthBase except phone, email and nric
         * -> added
         */
        toRegister = new PersonBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withNric(VALID_NRIC_CHARLIE).build();
        command = PersonUtil.getRegisterCommand(toRegister);
        assertCommandSuccess(command, toRegister);

        /* Case: register to empty HealthBase -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: register a person with drug allergies, command with parameters in random order -> added */
        toRegister = BOB;
        command = RegisterCommand.COMMAND_WORD + DRUG_ALLERGY_DESC_ASPIRIN + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + NAME_DESC_BOB
                + DRUG_ALLERGY_DESC_PENICILLIN + EMAIL_DESC_BOB + NRIC_DESC_BOB;
        assertCommandSuccess(command, toRegister);

        /* Case: register a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* ------------------------- Perform register operation on the shown filtered list -------------------------- */

        /* Case: filters the person list before registering -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ---------------------- Perform register operation while a person card is selected ------------------------ */

        /* Case: selects first card in the person list, register a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* --------------------------------- Perform invalid register operations ------------------------------------ */

        /* Case: register a duplicate person -> rejected */
        command = PersonUtil.getRegisterCommand(HOON);
        assertCommandFailure(command, RegisterCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: register a duplicate person except with different phone -> rejected */
        toRegister = new PersonBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = PersonUtil.getRegisterCommand(toRegister);
        assertCommandFailure(command, RegisterCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: register a duplicate person except with different email -> rejected */
        toRegister = new PersonBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = PersonUtil.getRegisterCommand(toRegister);
        assertCommandFailure(command, RegisterCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: register a duplicate person except with different address -> rejected */
        toRegister = new PersonBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = PersonUtil.getRegisterCommand(toRegister);
        assertCommandFailure(command, RegisterCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: register a duplicate person except with different tags -> rejected */
        command = PersonUtil.getRegisterCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, RegisterCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing nric -> rejected */
        command = RegisterCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));

        /* Case: missing name -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "registers " + PersonUtil.getPersonDetails(toRegister);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + INVALID_NAME_DESC
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + NAME_DESC_AMY + INVALID_PHONE_DESC
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY
                + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid drug allergy -> rejected */
        command = RegisterCommand.COMMAND_WORD + NRIC_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_DRUG_ALLERGY_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code RegisterCommand} that adds {@code toRegister} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code RegisterCommand} with the details of
     * {@code toRegister}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toRegister}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toRegister) {
        assertCommandSuccess(PersonUtil.getRegisterCommand(toRegister), toRegister);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see RegisterCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toRegister) {
        Model expectedModel = getModel();
        expectedModel.addPerson(toRegister);
        String expectedResultMessage = String.format(RegisterCommand.MESSAGE_SUCCESS, toRegister);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see RegisterCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
