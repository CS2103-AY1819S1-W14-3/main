package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIAGNOSIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIAGNOSIS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddmhCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.person.Nric;


public class AddmhCommandParserTest {
    private AddmhCommandParser parser;
    private Nric patientNric;
    private Diagnosis diagnosis;

    @Before
    public void setUp() throws IllegalValueException {
        parser = new AddmhCommandParser();
        patientNric = new Nric(VALID_NRIC_BOB);
        diagnosis = new Diagnosis(VALID_DIAGNOSIS, VALID_DOCTOR);
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        assertParseSuccess(parser, NRIC_DESC_BOB + VALID_DIAGNOSIS_DESC,
                new AddmhCommand(patientNric, diagnosis));
    }

    @Test
    public void parse_compulsoryFilesMissing_failure() {
        //todo complete to improve coverage
    }
}
