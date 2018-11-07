package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.GendataCommand;

public class GendataCommandParserTest {
    private static final String NON_INTEGER_INPUT = "2.3";
    private static final String NEGATIVE_INPUT = "-3";
    private static final String VALID_INPUT = "3";

    private GendataCommandParser parser = new GendataCommandParser();

    @Test
    public void parse_nonInteger_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, NON_INTEGER_INPUT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GendataCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeInteger_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, NEGATIVE_INPUT,
            GendataCommandParser.MESSAGE_MUST_BE_POSITIVE_INTEGER);
    }

    @Test
    public void parse_positiveInteger_throwsParseException() {
        CommandParserTestUtil.assertParseSuccess(parser, VALID_INPUT,
            new GendataCommand(Integer.parseInt(VALID_INPUT)));
    }

}
