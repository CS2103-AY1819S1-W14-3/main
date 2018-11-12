package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalHealthBase;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.HealthBaseChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.HealthBase;
import seedu.address.model.ReadOnlyHealthBase;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlHealthBaseStorage healthBaseStorage = new XmlHealthBaseStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(healthBaseStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void healthBaseReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlHealthBaseStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlHealthBaseStorageTest} class.
         */
        HealthBase original = getTypicalHealthBase();
        storageManager.saveHealthBase(original);
        ReadOnlyHealthBase retrieved = storageManager.readHealthBase().get();
        assertEquals(original, new HealthBase(retrieved));
    }

    @Test
    public void getHealthBaseFilePath() {
        assertNotNull(storageManager.getHealthBaseFilePath());
    }

    @Test
    public void handleHealthBaseChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlHealthBaseStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleHealthBaseChangedEvent(new HealthBaseChangedEvent(new HealthBase()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlHealthBaseStorageExceptionThrowingStub extends XmlHealthBaseStorage {

        public XmlHealthBaseStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveHealthBase(ReadOnlyHealthBase healthBase, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
