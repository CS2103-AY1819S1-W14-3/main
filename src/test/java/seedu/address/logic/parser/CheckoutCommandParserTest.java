package seedu.address.logic.parser;

//@@author yuntongzhang

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.model.person.Nric;

/**
 * Test driver for CheckoutCommandParser class.
 * @author yuntongzhang
 */
public class CheckoutCommandParserTest {
    private CheckoutCommandParser parser;
    private Nric patientNric;

    @Before
    public void setUp() {
        parser = new CheckoutCommandParser();
        patientNric = new Nric(VALID_NRIC_AMY);
    }

    @Test
    public void parse_success() {
        assertParseSuccess(parser, NRIC_DESC_AMY, new CheckoutCommand(patientNric));
    }

    @Test
    public void parse_nricMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " ", expectedMessage);
    }
}
