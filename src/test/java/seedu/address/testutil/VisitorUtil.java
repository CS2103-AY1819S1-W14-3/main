package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITOR;

import seedu.address.logic.commands.VisitorinCommand;
import seedu.address.model.person.Nric;
import seedu.address.model.visitor.Visitor;

/**
 * utility class for {@code visitor}.
 */
public class VisitorUtil {
    public static String getVisitorInCommand(Nric nric, Visitor visitor) {
        StringBuilder sb = new StringBuilder();
        sb.append(VisitorinCommand.COMMAND_WORD).append(" ")
                .append(PREFIX_NRIC).append(nric.toString()).append(" ")
                .append(PREFIX_VISITOR).append(visitor.getVisitorName());

        return sb.toString();
    }
}
