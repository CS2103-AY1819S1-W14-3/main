package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewvisitorsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;


//@@ author GAO JIAXIN666
/**
 * Parses input arguments and creates a new ViewvisitorsCommand object
 */
public class ViewvisitorsCommandParser implements Parser<ViewvisitorsCommand> {

    @Override
    public ViewvisitorsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewvisitorsCommand.MESSAGE_USAGE)));
        }

        Nric patientNric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());

        return new ViewvisitorsCommand(patientNric);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
