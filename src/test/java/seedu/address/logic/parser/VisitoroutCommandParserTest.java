package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VISITOR_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.VisitoroutCommand;
import seedu.address.model.person.Nric;
import seedu.address.model.visitor.Visitor;
import seedu.address.model.visitor.VisitorList;

//@@author GAO JIAXIN666
public class VisitoroutCommandParserTest {
    private VisitorList visitorList = new VisitorList();
    private Visitor listVisitor = new Visitor("GAO JIAXIN"); // same name of VALID_VISITOR
    private VisitoroutCommandParser parser;
    private Nric patientNric;

    @Before
    public void setUp() {
        parser = new VisitoroutCommandParser();
        patientNric = new Nric(VALID_NRIC_BOB);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, (NRIC_DESC_BOB + VALID_VISITOR_DESC),
                new VisitoroutCommand(patientNric, listVisitor));
    }

    @Test
    public void parse_prefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, VisitoroutCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_NRIC_BOB, expectedMessage);
    }

    @Test
    public void parse_nricMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, VisitoroutCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_VISITOR_DESC, expectedMessage);
    }

    @Test
    public void parse_visitorNameMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, VisitoroutCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NRIC_DESC_BOB, expectedMessage);
    }
}
