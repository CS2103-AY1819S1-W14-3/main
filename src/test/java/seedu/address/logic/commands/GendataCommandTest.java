package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;

public class GendataCommandTest {
    @Test
    public void execute() throws CommandException {
        new GendataCommand(10).execute(new ModelManager(), new CommandHistory());
    }

    @Test
    public void equals() {
        final GendataCommand standardCommand = new GendataCommand(100);

        GendataCommand commandWithSameValues = new GendataCommand(100);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // different index -> returns false
        assertFalse(standardCommand.equals(new GendataCommand(10)));
    }
}
