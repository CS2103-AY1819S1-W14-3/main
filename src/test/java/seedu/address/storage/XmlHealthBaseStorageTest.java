package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalHealthBase;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.HealthBase;
import seedu.address.model.ReadOnlyHealthBase;

public class XmlHealthBaseStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlHealthBaseStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readHealthBase_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readHealthBase(null);
    }

    private java.util.Optional<ReadOnlyHealthBase> readHealthBase(String filePath) throws Exception {
        return new XmlHealthBaseStorage(Paths.get(filePath)).readHealthBase(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHealthBase("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readHealthBase("NotXmlFormatHealthBase.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readHealthBase_invalidPersonHealthBase_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readHealthBase("invalidPersonHealthBase.xml");
    }

    @Test
    public void readHealthBase_invalidAndValidPersonHealthBase_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readHealthBase("invalidAndValidPersonHealthBase.xml");
    }

    @Test
    public void readAndSaveHealthBase_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempHealthBase.xml");
        HealthBase original = getTypicalHealthBase();
        XmlHealthBaseStorage xmlHealthBaseStorage = new XmlHealthBaseStorage(filePath);

        //Save in new file and read back
        xmlHealthBaseStorage.saveHealthBase(original, filePath);
        ReadOnlyHealthBase readBack = xmlHealthBaseStorage.readHealthBase(filePath).get();
        assertEquals(original, new HealthBase(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlHealthBaseStorage.saveHealthBase(original, filePath);
        readBack = xmlHealthBaseStorage.readHealthBase(filePath).get();
        assertEquals(original, new HealthBase(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlHealthBaseStorage.saveHealthBase(original); //file path not specified
        readBack = xmlHealthBaseStorage.readHealthBase().get(); //file path not specified
        assertEquals(original, new HealthBase(readBack));

    }

    @Test
    public void saveHealthBase_nullHealthBase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveHealthBase(null, "SomeFile.xml");
    }

    /**
     * Saves {@code healthBase} at the specified {@code filePath}.
     */
    private void saveHealthBase(ReadOnlyHealthBase healthBase, String filePath) {
        try {
            new XmlHealthBaseStorage(Paths.get(filePath))
                    .saveHealthBase(healthBase, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHealthBase_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveHealthBase(new HealthBase(), null);
    }


}
