package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUG_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Checks in a patient into the HMS
 */
public class CheckinCommand extends Command {

    public static final String COMMAND_WORD = "checkin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks in a patient into the HMS \n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_DRUG_ALLERGY + "DRUG ALLERGIES]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DRUG_ALLERGY + "aspirin "
            + PREFIX_DRUG_ALLERGY + "insulin";

    public static final String MESSAGE_SUCCESS = "New patient checked in: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already checked in";
    public static final String MESSAGE_DUPLICATE_NRIC = "A person of this NRIC is already checked in";

    private final Person toCheckin;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public CheckinCommand(Person person) {
        requireNonNull(person);
        toCheckin = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model); // note: throws a nullpt exception

        if (model.hasPerson(toCheckin)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        ObservableList<Person> filteredByNric = model.getFilteredPersonList()
                                                        .filtered(p -> toCheckin.getNric().equals(p.getNric()));

        if (filteredByNric.size() == 1) {
            throw new CommandException(MESSAGE_DUPLICATE_NRIC);
        }

        model.addPerson(toCheckin);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toCheckin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckinCommand // instanceof handles nulls
                        && toCheckin.equals(((CheckinCommand) other).toCheckin));
    }
}
