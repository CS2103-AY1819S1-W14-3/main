package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CULTURAL_REQUIREMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIET_COLLECTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHYSICAL_DIFFICULTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddDietCommand;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.diet.DietType;
import seedu.address.model.person.Nric;

//@@author yuntongzhang

/**
 * Test driver for AddDietCommandParser class.
 * @author yuntongzhang
 */
public class AddDietCommandParserTest {
    private AddDietCommandParser parser;
    private Nric patientNric;
    private DietCollection dietToAdd;

    @Before
    public void setUp() {
        parser = new AddDietCommandParser();
        patientNric = new Nric(VALID_NRIC_AMY);
        Set<Diet> dietSet = new HashSet<>();
        dietSet.add(new Diet(VALID_ALLERGY, DietType.ALLERGY));
        dietSet.add(new Diet(VALID_CULTURAL_REQUIREMENT, DietType.CULTURAL));
        dietSet.add(new Diet(VALID_PHYSICAL_DIFFICULTY, DietType.PHYSICAL));
        dietToAdd = new DietCollection(dietSet);
    }

    @Test
    public void parse_success() {
        assertParseSuccess(parser, NRIC_DESC_AMY + VALID_DIET_COLLECTION_DESC,
                new AddDietCommand(patientNric, dietToAdd));
    }

    @Test
    public void parse_nricMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDietCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_DIET_COLLECTION_DESC, expectedMessage);
    }

    @Test
    public void parse_onlyNric_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDietCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NRIC_DESC_AMY, expectedMessage);
    }
}
