package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.commands.AddDietCommand;
import seedu.address.logic.commands.AddmedsCommand;
import seedu.address.logic.commands.AddmhCommand;
import seedu.address.logic.commands.CheckinCommand;
import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GendataCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewvisitorsCommand;
import seedu.address.logic.commands.VisitorinCommand;
import seedu.address.logic.commands.VisitoroutCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String MESSAGE_INSUFFICIENT_PERMISSIONS = "You have insufficient permissions"
        + " to invoke this command!";
    private static final String TOGGLE_DEV_MODE_COMMAND_WORD = "dev-mode";
    private boolean isDevModeEnabled = false;
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case RegisterCommand.COMMAND_WORD:
            return new RegisterCommandParser().parse(arguments);

        case CheckoutCommand.COMMAND_WORD:
            return new CheckoutCommandParser().parse(arguments);

        case CheckinCommand.COMMAND_WORD:
            return new CheckinCommandParser().parse(arguments);

        case AddDietCommand.COMMAND_WORD:
            return new AddDietCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddmhCommand.COMMAND_WORD:
            return new AddmhCommandParser().parse(arguments);

        case AddmedsCommand.COMMAND_WORD:
            return new AddmedsCommandParser().parse(arguments);

        case ViewvisitorsCommand.COMMAND_WORD:
            return new ViewvisitorsCommandParser().parse(arguments);

        case VisitorinCommand.COMMAND_WORD:
            return new VisitorinCommandParser().parse(arguments);

        case VisitoroutCommand.COMMAND_WORD:
            return new VisitoroutCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case AddApptCommand.COMMAND_WORD:
            return new AddApptCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case GendataCommand.COMMAND_WORD:
            if (!isDevModeEnabled) {
                throw new ParseException(MESSAGE_INSUFFICIENT_PERMISSIONS);
            }
            return new GendataCommandParser().parse(arguments);

        case TOGGLE_DEV_MODE_COMMAND_WORD:
            isDevModeEnabled = !isDevModeEnabled;
            return new Command() {
                @Override
                public CommandResult execute(Model model, CommandHistory history) throws CommandException {
                    return new CommandResult("Developer mode " + (isDevModeEnabled ? "enabled!" : "disabled!"));
                }
            };

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

        }
    }

}
