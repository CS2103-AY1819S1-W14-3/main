package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CULTURAL_REQUIREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHYSICAL_DIFFICULTY;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddDietCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.diet.DietType;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddDietCommand object.
 * @author yuntongzhang
 */
public class AddDietCommandParser implements Parser<AddDietCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddDietCommand
     * and returns an AddDietCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddDietCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC,
                PREFIX_ALLERGY, PREFIX_CULTURAL_REQUIREMENT, PREFIX_PHYSICAL_DIFFICULTY);

        if (!prefixPresent(argMultimap, PREFIX_NRIC)
            || !anyPrefixesPresent(argMultimap, PREFIX_ALLERGY, PREFIX_CULTURAL_REQUIREMENT, PREFIX_PHYSICAL_DIFFICULTY)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDietCommand.MESSAGE_USAGE));
        }

        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());

        Set<Diet> allergies = ParserUtil.parseDiet(argMultimap.getAllValues(PREFIX_ALLERGY), DietType.ALLERGY);
        Set<Diet> culturalRequirements = ParserUtil.parseDiet(argMultimap
                .getAllValues(PREFIX_CULTURAL_REQUIREMENT), DietType.CULTURAL);
        Set<Diet> physicalDifficulties = ParserUtil.parseDiet(argMultimap
                .getAllValues(PREFIX_PHYSICAL_DIFFICULTY), DietType.PHYSICAL);

        DietCollection dietsToAdd = new DietCollection(allergies, culturalRequirements, physicalDifficulties);
        return new AddDietCommand(nric, dietsToAdd);
    }

    /**
     * Returns true if the one of the prefixes does not contain empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if the prefix does not contain empty {@code Optional} value in the given {@code ArgumentMultimap}.
     */
    private static boolean prefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
