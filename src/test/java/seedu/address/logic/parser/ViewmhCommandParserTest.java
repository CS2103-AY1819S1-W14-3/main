package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ViewmhCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

// @@author omegafishy
// Old code removed from HealthBase, no longer relevant.
public class ViewmhCommandParserTest {
    private ViewmhCommandParser parser;
    private Nric patientNric;

    @Before
    public void setUp() throws IllegalValueException {
        parser = new ViewmhCommandParser();
        patientNric = new Nric(VALID_NRIC_BOB);
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        assertParseSuccess(parser, NRIC_DESC_BOB, new ViewmhCommand(patientNric));
    }
}
