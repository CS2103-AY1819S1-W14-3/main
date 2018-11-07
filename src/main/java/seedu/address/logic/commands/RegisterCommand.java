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
 * Resgister a new patient into HealthBase
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register a new patient into HealthBase \n"
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
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already registered in the system";
    public static final String MESSAGE_DUPLICATE_NRIC = "A person of this NRIC is already registered";

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

        if (model.hasPerson(toRegister) || model.hasCheckedOutPerson(toRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        ObservableList<Person> filteredByNric = model.getFilteredPersonList()
                                                        .filtered(p -> toRegister.getNric().equals(p.getNric()));

        if (filteredByNric.size() == 1) {
            throw new CommandException(MESSAGE_DUPLICATE_NRIC);
        }

        model.addPerson(toRegister);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRegister));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                        && toRegister.equals(((RegisterCommand) other).toRegister));
    }
}
