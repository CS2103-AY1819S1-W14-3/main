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
import seedu.address.model.appointment.Type;
import seedu.address.model.medicalhistory.Diagnosis;
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
    public static final String PROCEDURE_VALIDATION_REGEX = "^[A-Za-z- ]+$";
    public static final String MESSAGE_INVALID_PROCEDURE = "Procedure name can take any alphabet, and should not be "
            + "blank.";
    public static final String MESSAGE_INVALID_DATE_TIME_BEFORE_CURRENT = "Input date and time is before current date "
            + "and time.";
    public static final String MESSAGE_INVALID_TYPE = "Invalid input type. Valid types are: PROP, DIAG, THP, SRG";

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
        String doctor = argMultimap.getValue(PREFIX_DOCTOR).get();

        nric = ParserUtil.parseNric(patientNric);
        appt = new Appointment(type, procedure, dateTime, doctor);

        try {
            LocalDateTime.parse(dateTime, Appointment.DATE_TIME_FORMAT);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME);
        }

        if (!isDateTimeAfterCurrent(appt.getDate_time())) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME_BEFORE_CURRENT);
        }

        if (!isValidType(appt.getType())) {
            throw new ParseException(MESSAGE_INVALID_TYPE);
        }

        if (!isValidProcedure(appt.getProcedure_name())) {
            throw new ParseException(MESSAGE_INVALID_PROCEDURE);
        }

        if (!Diagnosis.isValidDoctor(appt.getDoc_name())) {
            throw new ParseException(Diagnosis.MESSAGE_NAME_CONSTRAINTS_DOCTOR);
        }

        return new AddApptCommand(nric, appt);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks if the type entered by user is valid
     * @param typeAbbr abbreviation of type
     * @return true if valid
     */
    public static boolean isValidType(String typeAbbr) {
        for (Type t: Type.values()) {
            if (t.getAbbreviation().equals(typeAbbr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the procedure name is valid
     * @param test the procedure name input by user
     * @return true if valid
     */
    public static boolean isValidProcedure(String test) {
        return test.matches(PROCEDURE_VALIDATION_REGEX);
    }

    /**
     * Checks if date and time input by user is after current time
     * @param test date and time input by user
     * @return true if after current time
     */
    public static boolean isDateTimeAfterCurrent(String test) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime apptDateTime = LocalDateTime.parse(test, Appointment.DATE_TIME_FORMAT);
        return apptDateTime.isAfter(now);
    }
}
