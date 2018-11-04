package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITOR;

import java.util.stream.Stream;

import seedu.address.logic.commands.VisitoroutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.visitor.Visitor;

/**
 * Parses input arguments and creates a new VisitorOutCommand object
 */
public class VisitoroutCommandParser implements Parser<VisitoroutCommand> {

    @Override
    public VisitoroutCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_VISITOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_VISITOR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, VisitoroutCommand.MESSAGE_USAGE)));
        }

        Nric patientNric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Visitor visitor = ParserUtil.parseVisitor(argMultimap.getValue(PREFIX_VISITOR).get());

        return new VisitoroutCommand(patientNric, visitor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
