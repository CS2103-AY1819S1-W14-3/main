package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCEDURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddApptCommand object
 */
public class AddApptCommandParser implements Parser<AddApptCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddApptCommand and returns an AddApptCommand object for execution.
     *
     * @throws ParseException
     *             if the user input does not conform the expected format
     */

    public static final String MESSAGE_INVALID_DATE_TIME = "Input date and time is invalid or in incorrect format.";

    @Override
    public AddApptCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_TYPE, PREFIX_PROCEDURE,
                PREFIX_DATE_TIME, PREFIX_DOCTOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_TYPE, PREFIX_PROCEDURE,
                PREFIX_DATE_TIME, PREFIX_DOCTOR) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE));
        }

        Nric nric;
        Appointment appt;
        String patientNric = argMultimap.getValue(PREFIX_NRIC).get();
        String type = argMultimap.getValue(PREFIX_TYPE).get();
        String procedure = argMultimap.getValue(PREFIX_PROCEDURE).get();
        String dateTime = argMultimap.getValue(PREFIX_DATE_TIME).get();

        if (!Nric.isValidNric(patientNric)) {
            throw new ParseException(Nric.MESSAGE_NAME_CONSTRAINTS);
        }

        try {
            LocalDateTime.parse(dateTime, Appointment.DATE_TIME_FORMAT);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME);
        }

        String doctor = argMultimap.getValue(PREFIX_DOCTOR).get();

        nric = new Nric(patientNric);
        appt = new Appointment(type, procedure, dateTime, doctor);

        return new AddApptCommand(nric, appt);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
