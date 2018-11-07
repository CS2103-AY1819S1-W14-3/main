package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.DataGenerator;
import seedu.address.model.person.Person;

/**
 * Resets the address book and adds new people based on a set of templates.
 * Developer-mode only command.
 */
public class GendataCommand extends Command {

    public static final String COMMAND_WORD = "gendata";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates data based on a set of templates\n"
        + "Parameters: NUMBER_OF_PERSONS_TO_GENERATE (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DATA_GENERATED_SUCCESS = "Data generated!";

    private final int numPersons;
    private final DataGenerator generator = new DataGenerator();

    public GendataCommand(int numPersons) {
        this.numPersons = numPersons;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.resetData(new AddressBook());

        for (int i = 0; i < numPersons; i++) {
            model.addPerson(generatePerson());
        }

        return new CommandResult(MESSAGE_DATA_GENERATED_SUCCESS);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof GendataCommand // instanceof handles nulls
                && numPersons == (((GendataCommand) other).numPersons)); // state check
    }

    /**
     * Generates a person based on example data given in {@DataGenerator.java}.
     */
    public Person generatePerson() {
        return new Person(
            generator.generateNric(),
            generator.generateName(),
            generator.generatePhone(),
            generator.generateEmail(),
            generator.generateAddress(),
            generator.generateDrugAllergies(),
            generator.generatePrescriptionList(),
            generator.generateAppointmentsList(),
            generator.generateMedicalHistory(),
            generator.generateDietCollection(),
            generator.generateVisitorList()
            );
    }
}
