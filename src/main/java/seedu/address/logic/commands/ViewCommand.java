package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwapPanelViewEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.SwappablePanelName;


/**
 * Changes the UI view.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Changes the view.\n"
        + "Parameters: VIEW_NAME (must be one of the views enabled in the app).\n"
        + "See the User Guide for more information on the views enabled.\n"
        + "Example: " + COMMAND_WORD + " " + SwappablePanelName.BLANK.getShortForm();

    public static final String MESSAGE_VIEW_CHANGED_SUCCESS = "Changed view to: %1$s";

    private final SwappablePanelName panelName;

    public ViewCommand(SwappablePanelName panelName) {
        requireNonNull(panelName);
        this.panelName = panelName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        EventsCenter.getInstance().post(new SwapPanelViewEvent(panelName));

        String formattedPanelName = panelName.toString()
                                             .substring(0, 1)
                                             .toUpperCase()
            + panelName.toString()
                       .toLowerCase()
                       .substring(1);
        return new CommandResult(String.format(MESSAGE_VIEW_CHANGED_SUCCESS, formattedPanelName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ViewCommand // instanceof handles nulls
                && panelName.equals(((ViewCommand) other).panelName)); // state check
    }
}
