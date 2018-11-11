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
 * Register a new patient into HealthBase
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register a new patient into HealthBase. "
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

    public static final String MESSAGE_SUCCESS = "New patient registered successfully: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "A patient of this NRIC is already registered and checked in";
    public static final String MESSAGE_ALREADY_CHECKED_OUT = "A patient of this NRIC is already registered, but " +
                                                             "was previously checked out. \n" +
                                                             "Please use the checkin command to check in this " +
                                                             "patient instead of registering him/her again.";

    private final Person toRegister;

    /**
     * Creates an RegisterCommand to register the specified {@code Person}
     */
    public RegisterCommand(Person person) {
        requireNonNull(person);
        toRegister = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model); // note: throws a nullpt exception
        checkValidRegister(toRegister, model);

        model.addPerson(toRegister);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRegister));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                        && toRegister.equals(((RegisterCommand) other).toRegister));
    }

    /**
     * Checks whether is valid to register the {@code person} with the given {@model}.
     * @param person The person being registered.
     * @param model The backing model.
     * @throws CommandException If the person was previously registered, or was registered and checked out.
     */
    private static void checkValidRegister(Person person, Model model) throws CommandException {
        if (model.hasPerson(person)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.hasCheckedOutPerson(person)) {
            throw new CommandException(MESSAGE_ALREADY_CHECKED_OUT);
        }
    }
}
