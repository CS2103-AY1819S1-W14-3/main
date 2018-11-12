package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.HealthBaseChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyHealthBase;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of HealthBase data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HealthBaseStorage healthBaseStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(HealthBaseStorage healthBaseStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.healthBaseStorage = healthBaseStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ HealthBase methods ==============================

    @Override
    public Path getHealthBaseFilePath() {
        return healthBaseStorage.getHealthBaseFilePath();
    }

    @Override
    public Optional<ReadOnlyHealthBase> readHealthBase() throws DataConversionException, IOException {
        return readHealthBase(healthBaseStorage.getHealthBaseFilePath());
    }

    @Override
    public Optional<ReadOnlyHealthBase> readHealthBase(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return healthBaseStorage.readHealthBase(filePath);
    }

    @Override
    public void saveHealthBase(ReadOnlyHealthBase healthBase) throws IOException {
        saveHealthBase(healthBase, healthBaseStorage.getHealthBaseFilePath());
    }

    @Override
    public void saveHealthBase(ReadOnlyHealthBase healthBase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        healthBaseStorage.saveHealthBase(healthBase, filePath);
    }


    @Override
    @Subscribe
    public void handleHealthBaseChangedEvent(HealthBaseChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveHealthBase(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
