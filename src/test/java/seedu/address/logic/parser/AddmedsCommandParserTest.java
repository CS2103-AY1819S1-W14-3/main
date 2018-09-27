package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRESCRIPTION_DESC_MISSING_DOSES_PER_DAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRESCRIPTION_DESC_MISSING_DOSE_UNIT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRESCRIPTION_DESC_MISSING_DRUGNAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRESCRIPTION_DESC_MISSING_DURATION_IN_DAYS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRESCRIPTION_DESC_MISSING_QUANTITY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSES_PER_DAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSE_UNIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRUGNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_IN_DAYS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRESCRIPTION_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddmedsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.person.Nric;

public class AddmedsCommandParserTest {
    private AddmedsCommandParser parser;
    private Nric patientNric;
    private Prescription prescription;

    @Before
    public void setUp() throws IllegalValueException {
        parser = new AddmedsCommandParser();
        patientNric = new Nric(VALID_NRIC_BOB);
        Dose dose = new Dose(VALID_DOSE, VALID_DOSE_UNIT, VALID_DOSES_PER_DAY);
        Duration duration = new Duration(VALID_DURATION_IN_DAYS);
        prescription = new Prescription(VALID_DRUGNAME, dose, duration);
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        // valid input - valid nric and prescription details
        assertParseSuccess(parser, NRIC_DESC_BOB + VALID_PRESCRIPTION_DESC,
                new AddmedsCommand(patientNric, prescription));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddmedsCommand.MESSAGE_USAGE);

        // missing drugname prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_PRESCRIPTION_DESC_MISSING_DRUGNAME, expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_PRESCRIPTION_DESC_MISSING_QUANTITY, expectedMessage);

        // missing dose unit prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_PRESCRIPTION_DESC_MISSING_DOSE_UNIT, expectedMessage);

        // missing doses per day prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_PRESCRIPTION_DESC_MISSING_DOSES_PER_DAY, expectedMessage);

        // all duration in days missing
        assertParseFailure(parser, VALID_NAME_BOB + INVALID_PRESCRIPTION_DESC_MISSING_DURATION_IN_DAYS,
                expectedMessage);
    }
}
