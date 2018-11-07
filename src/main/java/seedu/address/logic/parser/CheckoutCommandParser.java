package seedu.address.logic.parser;

//@@author yuntongzhang

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

/**
 * Parse input argument and create a new CheckoutCommand object.
 * @author yuntongzhang
 */
public class CheckoutCommandParser implements Parser<CheckoutCommand> {
    /**
     * Parse the given {@code String} of arguments in the context of the CheckoutCommand
     * and returns a CheckoutCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CheckoutCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (!prefixPresent(argumentMultimap, PREFIX_NRIC) || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
        }

        Nric nric = ParserUtil.parseNric(argumentMultimap.getValue(PREFIX_NRIC).get());
        return new CheckoutCommand(nric);
    }

    /**
     * Returns true if the prefix does not contain empty {@code Optional} value in the given {@code ArgumentMultimap}.
     */
    private static boolean prefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
