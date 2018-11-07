package seedu.address.testutil;

//@@author yuntongzhang

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.AddDietCommand;
import seedu.address.model.diet.Diet;
import seedu.address.model.person.Nric;

/**
 * Utility class for tests relevant to Diet.
 * @author yuntongzhang
 */
public class DietUtil {
    /**
     * Utility method to get a String representation of the user input of a adddient command.
     * @param nric The nric indicated in the command.
     * @param diet The diet to be added by the command.
     * @return A String representation of the user input to generate the corresponding adddiet command.
     */
    public static String getAddDietCommand(Nric nric, Diet diet) {
        StringBuilder sb = new StringBuilder();
        sb.append(AddDietCommand.COMMAND_WORD).append(" ")
            .append(PREFIX_NRIC).append(nric.toString()).append(" ")
            .append(getAllergyInput(diet));

        return sb.toString();
    }

    /**
     * Utility method to turn an allergy Diet to the user input representation.
     * @param diet The Diet of type allergy.
     * @return A String representation of the user input to generate the corresponding Diet.
     */
    public static String getAllergyInput(Diet diet) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ALLERGY)
            .append(diet.getDetail()).append(" ");

        return sb.toString();
    }
}
