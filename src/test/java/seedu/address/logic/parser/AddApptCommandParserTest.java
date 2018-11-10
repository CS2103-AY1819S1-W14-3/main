package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC_INVALID_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC_MISSING_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC_MISSING_DOCTOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC_MISSING_PROCEDURE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC_MISSING_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROCEDURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE;
import static seedu.address.logic.parser.AddApptCommandParser.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

public class AddApptCommandParserTest {

    private AddApptCommandParser parser;
    private Nric patientNric;
    private Appointment appt;

    @Before
    public void setUp() {
        parser = new AddApptCommandParser();
        patientNric = new Nric(VALID_NRIC_BOB);
        appt = new Appointment(VALID_TYPE, VALID_PROCEDURE, VALID_DATE_TIME, VALID_DOCTOR);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, NRIC_DESC_BOB + VALID_APPOINTMENT_DESC,
                new AddApptCommand(patientNric, appt));
    }

    @Test
    public void parse_invalidDateTime_failure() {
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_APPOINTMENT_DESC_INVALID_DATE_TIME,
                MESSAGE_INVALID_DATE_TIME);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE);

        // missing type prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_APPOINTMENT_DESC_MISSING_TYPE, expectedMessage);

        // missing procedure prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_APPOINTMENT_DESC_MISSING_PROCEDURE,
                expectedMessage);

        // missing dateTime prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_APPOINTMENT_DESC_MISSING_DATE_TIME,
                expectedMessage);

        // missing doctor prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_APPOINTMENT_DESC_MISSING_DOCTOR, expectedMessage);
    }
}
