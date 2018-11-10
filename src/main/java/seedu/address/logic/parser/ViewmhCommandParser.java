package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewmhCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;


// @@author omegafishy
// Old code removed from HealthBase, no longer relevant.
/**
 * Parses input arguments and creates a new ViewmhCommand object
 */
public class ViewmhCommandParser implements Parser<ViewmhCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewmmhCommand
     * and returns a ViewmhCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public ViewmhCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewmhCommand.MESSAGE_USAGE)));
        }

        Nric patientNric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());

        return new ViewmhCommand(patientNric);
    }

    /**
     * Returns true if the none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
