package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.HealthBaseChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyHealthBase;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends HealthBaseStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getHealthBaseFilePath();

    @Override
    Optional<ReadOnlyHealthBase> readHealthBase() throws DataConversionException, IOException;

    @Override
    void saveHealthBase(ReadOnlyHealthBase healthBase) throws IOException;

    /**
     * Saves the current version of HealthBase to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleHealthBaseChangedEvent(HealthBaseChangedEvent abce);
}
