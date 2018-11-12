package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.SwappablePanelName;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    public static final String MESSAGE_NO_SUCH_PANEL = "No such panel by the name \"%1$s\" exists!";
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewCommand parse(String args) throws ParseException {
        String shortPanelName = args.toLowerCase().trim();

        if (shortPanelName.length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        SwappablePanelName panelName = SwappablePanelName.fromShortForm(shortPanelName);

        if (panelName == null) {
            throw new ParseException(String.format(MESSAGE_NO_SUCH_PANEL, shortPanelName));
        }

        return new ViewCommand(panelName);
    }

}
