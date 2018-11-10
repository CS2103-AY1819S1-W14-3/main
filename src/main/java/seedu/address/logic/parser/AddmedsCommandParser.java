package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSES_PER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSE_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUGNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddmedsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.person.Nric;

//@@author snajef
/**
 * Parses input arguments and creates a new AddmedsCommand object
 */
public class AddmedsCommandParser implements Parser<AddmedsCommand> {
    public static final String MESSAGE_DOSAGE_FORMAT = "Incorrect format for dosage detected. "
        + "Please ensure that the dosage is a positive number, "
        + "and that the doses per day is a positive integer value!";
    public static final String MESSAGE_DURATION_FORMAT = "Incorrect format for duration detected. "
        + "Please ensure that the duration is a positive integer value, "
        + "and that that value is not too high!";
    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddmedsCommand and returns an AddmedsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public AddmedsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_DRUGNAME, PREFIX_QUANTITY,
            PREFIX_DOSE_UNIT, PREFIX_DOSES_PER_DAY, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_DRUGNAME, PREFIX_QUANTITY, PREFIX_DOSE_UNIT,
            PREFIX_DOSES_PER_DAY, PREFIX_DURATION) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddmedsCommand.MESSAGE_USAGE));
        }

        String patientNric = argMultimap.getValue(PREFIX_NRIC).get();
        String drugName = argMultimap.getValue(PREFIX_DRUGNAME).get();
        Dose dose;
        Duration duration;
        Nric nric;
        Prescription med;

        try {
            dose = ParserUtil.parseDose(
                Double.parseDouble(argMultimap.getValue(PREFIX_QUANTITY).get()),
                argMultimap.getValue(PREFIX_DOSE_UNIT).get(),
                Integer.parseInt(argMultimap.getValue(PREFIX_DOSES_PER_DAY).get())
                );
        } catch (NumberFormatException | IllegalValueException e) {
            throw new ParseException(MESSAGE_DOSAGE_FORMAT, e);
        }

        try {
            duration = ParserUtil.parseDuration(Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get()));
        } catch (NumberFormatException | IllegalValueException e) {
            throw new ParseException(MESSAGE_DURATION_FORMAT, e);
        }

        nric = ParserUtil.parseNric(patientNric);
        med = new Prescription(drugName, dose, duration);

        return new AddmedsCommand(nric, med);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
