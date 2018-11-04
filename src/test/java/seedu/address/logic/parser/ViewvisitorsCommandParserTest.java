package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ViewvisitorsCommand;
import seedu.address.model.person.Nric;

//@@author GAO JIAXIN666
public class ViewvisitorsCommandParserTest {
    private ViewvisitorsCommandParser parser;
    private Nric patientNric;

    @Before
    public void setUp() {
        parser = new ViewvisitorsCommandParser();
        patientNric = new Nric(VALID_NRIC_BOB);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, NRIC_DESC_BOB, new ViewvisitorsCommand(patientNric));
    }

    @Test
    public void parse_prefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewvisitorsCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_NRIC_BOB, expectedMessage);
    }

    @Test
    public void parse_nricMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewvisitorsCommand.MESSAGE_USAGE);
        assertParseFailure(parser, String.valueOf(PREFIX_NRIC), expectedMessage);
    }
}
