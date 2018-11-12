package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.HealthBase;
import seedu.address.testutil.TypicalPersons;

public class XmlSerializableHealthBaseTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableHealthBaseTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsHealthBase.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonHealthBase.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonHealthBase.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableHealthBase dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableHealthBase.class);
        HealthBase healthBaseFromFile = dataFromFile.toModelType();
        HealthBase typicalPersonsHealthBase = TypicalPersons.getTypicalHealthBase();
        assertEquals(healthBaseFromFile, typicalPersonsHealthBase);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableHealthBase dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableHealthBase.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableHealthBase dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableHealthBase.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableHealthBase.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }
}
