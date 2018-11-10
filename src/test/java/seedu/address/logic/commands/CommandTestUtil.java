package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CULTURAL_REQUIREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSES_PER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSE_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUGNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUG_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MED_HISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHYSICAL_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCEDURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Type;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_NRIC_AMY = "S0000100H";
    public static final String VALID_NRIC_BOB = "S0000100K";
    public static final String VALID_NRIC_CHARLIE = "S0000100L";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DRUG_ALLERGY_ASPIRIN = "aspirin";
    public static final String VALID_DRUG_ALLERGY_PENICILLIN = "penicillin";

    public static final String VALID_DIAGNOSIS = "Amy has a case of acute bronchitis, referred to Dr. Zhang";

    public static final String VALID_VISITOR = "GAO JIAXIN";

    public static final String VALID_TYPE = Type.SURGICAL.getAbbreviation();
    public static final String VALID_PROCEDURE = "Heart Bypass";
    public static final String VALID_DATE_TIME = "12-12-2022 12:00";
    public static final String INVALID_DATE_TIME = "12-13-2025 23:30";
    public static final String VALID_DOCTOR = "Dr. Gregory House";

    public static final String VALID_DRUGNAME = "Paracetamol";
    public static final int VALID_DOSE = 2;
    public static final String VALID_DOSE_UNIT = "tablets";
    public static final int VALID_DOSES_PER_DAY = 4;
    public static final int VALID_DURATION_IN_DAYS = 14;

    public static final String VALID_ALLERGY = "Egg";
    public static final String VALID_CULTURAL_REQUIREMENT = "Halal";
    public static final String VALID_PHYSICAL_DIFFICULTY = "Hands cannot move";

    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String NRIC_DESC_CHARLIE = " " + PREFIX_NRIC + VALID_NRIC_CHARLIE;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String DRUG_ALLERGY_DESC_ASPIRIN = " " + PREFIX_DRUG_ALLERGY + VALID_DRUG_ALLERGY_ASPIRIN;
    public static final String DRUG_ALLERGY_DESC_PENICILLIN = " " + PREFIX_DRUG_ALLERGY + VALID_DRUG_ALLERGY_PENICILLIN;
    public static final String VALID_VISITOR_DESC = " " + PREFIX_VISITOR + VALID_VISITOR;

    public static final String VALID_DIAGNOSIS_DESC = " " + PREFIX_MED_HISTORY + VALID_DIAGNOSIS
            + " " + PREFIX_DOCTOR + VALID_DOCTOR;

    public static final String VALID_DIET_COLLECTION_DESC = " " + PREFIX_ALLERGY + VALID_ALLERGY
            + " " + PREFIX_CULTURAL_REQUIREMENT + VALID_CULTURAL_REQUIREMENT
            + " " + PREFIX_PHYSICAL_DIFFICULTY + VALID_PHYSICAL_DIFFICULTY;

    public static final String VALID_PRESCRIPTION_DESC = " " + PREFIX_DRUGNAME + VALID_DRUGNAME
            + " " + PREFIX_QUANTITY + VALID_DOSE
            + " " + PREFIX_DOSE_UNIT + VALID_DOSE_UNIT
            + " " + PREFIX_DOSES_PER_DAY + VALID_DOSES_PER_DAY
            + " " + PREFIX_DURATION + VALID_DURATION_IN_DAYS;

    public static final String EMPTY_PRESCRIPTION_DESC = "";

    public static final String INVALID_PRESCRIPTION_DESC_MISSING_DRUGNAME = " " + PREFIX_QUANTITY + VALID_DOSE
            + " " + PREFIX_DOSE_UNIT + VALID_DOSE_UNIT
            + " " + PREFIX_DOSES_PER_DAY + VALID_DOSES_PER_DAY
            + " " + PREFIX_DURATION + VALID_DURATION_IN_DAYS;

    public static final String INVALID_PRESCRIPTION_DESC_MISSING_QUANTITY = " " + PREFIX_DRUGNAME + VALID_DRUGNAME
            + " " + PREFIX_DOSE_UNIT + VALID_DOSE_UNIT
            + " " + PREFIX_DOSES_PER_DAY + VALID_DOSES_PER_DAY
            + " " + PREFIX_DURATION + VALID_DURATION_IN_DAYS;

    public static final String INVALID_PRESCRIPTION_DESC_MISSING_DOSE_UNIT = " " + PREFIX_DRUGNAME + VALID_DRUGNAME
            + " " + PREFIX_QUANTITY + VALID_DOSE
            + " " + PREFIX_DOSES_PER_DAY + VALID_DOSES_PER_DAY
            + " " + PREFIX_DURATION + VALID_DURATION_IN_DAYS;

    public static final String INVALID_PRESCRIPTION_DESC_MISSING_DOSES_PER_DAY = " " + PREFIX_DRUGNAME + VALID_DRUGNAME
            + " " + PREFIX_QUANTITY + VALID_DOSE
            + " " + PREFIX_DOSE_UNIT + VALID_DOSE_UNIT
            + " " + PREFIX_DURATION + VALID_DURATION_IN_DAYS;

    public static final String INVALID_PRESCRIPTION_DESC_MISSING_DURATION_IN_DAYS = " "
            + PREFIX_DRUGNAME + VALID_DRUGNAME
            + " " + PREFIX_QUANTITY + VALID_DOSE
            + " " + PREFIX_DOSE_UNIT + VALID_DOSE_UNIT
            + " " + PREFIX_DOSES_PER_DAY + VALID_DOSES_PER_DAY;

    public static final String VALID_APPOINTMENT_DESC = " " + PREFIX_TYPE + VALID_TYPE
            + " " + PREFIX_PROCEDURE + VALID_PROCEDURE
            + " " + PREFIX_DATE_TIME + VALID_DATE_TIME
            + " " + PREFIX_DOCTOR + VALID_DOCTOR;

    public static final String INVALID_APPOINTMENT_DESC_MISSING_TYPE = " " + PREFIX_PROCEDURE + VALID_PROCEDURE
            + " " + PREFIX_DATE_TIME + VALID_DATE_TIME
            + " " + PREFIX_DOCTOR + VALID_DOCTOR;

    public static final String INVALID_APPOINTMENT_DESC_MISSING_PROCEDURE = " " + PREFIX_TYPE + VALID_TYPE
            + " " + PREFIX_DATE_TIME + VALID_DATE_TIME
            + " " + PREFIX_DOCTOR + VALID_DOCTOR;

    public static final String INVALID_APPOINTMENT_DESC_MISSING_DATE_TIME = " " + PREFIX_TYPE + VALID_TYPE
            + " " + PREFIX_PROCEDURE + VALID_PROCEDURE
            + " " + PREFIX_DOCTOR + VALID_DOCTOR;

    public static final String INVALID_APPOINTMENT_DESC_INVALID_DATE_TIME = " " + PREFIX_TYPE + VALID_TYPE
            + " " + PREFIX_PROCEDURE + VALID_PROCEDURE
            + " " + PREFIX_DATE_TIME + INVALID_DATE_TIME
            + " " + PREFIX_DOCTOR + VALID_DOCTOR;

    public static final String INVALID_APPOINTMENT_DESC_MISSING_DOCTOR = " " + PREFIX_TYPE + VALID_TYPE
            + " " + PREFIX_PROCEDURE + VALID_PROCEDURE
            + " " + PREFIX_DATE_TIME + VALID_DATE_TIME;

    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "AAAAAA"; // NRIC must match NRIC format.
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_DRUG_ALLERGY_DESC = " " + PREFIX_DRUG_ALLERGY + "pills*";
    // '*' not allowed in drug allergy

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_AMY).withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_DRUG_ALLERGY_ASPIRIN).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_DRUG_ALLERGY_PENICILLIN, VALID_DRUG_ALLERGY_ASPIRIN).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel}
     * remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given
     * {@code targetIndex} in the {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from
     * {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    static class ModelStub implements Model {
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
        public boolean hasCheckedOutPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void checkOutPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void reCheckInPerson(Person person) {
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
        public ObservableList<Person> getFilteredCheckedOutPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
