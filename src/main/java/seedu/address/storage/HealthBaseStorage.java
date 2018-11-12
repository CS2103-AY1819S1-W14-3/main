package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.HealthBase;
import seedu.address.model.ReadOnlyHealthBase;

/**
 * Represents a storage for {@link HealthBase}.
 */
public interface HealthBaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHealthBaseFilePath();

    /**
     * Returns HealthBase data as a {@link ReadOnlyHealthBase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyHealthBase> readHealthBase() throws DataConversionException, IOException;

    /**
     * @see #getHealthBaseFilePath()
     */
    Optional<ReadOnlyHealthBase> readHealthBase(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyHealthBase} to the storage.
     * @param healthBase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHealthBase(ReadOnlyHealthBase healthBase) throws IOException;

    /**
     * @see #saveHealthBase(ReadOnlyHealthBase)
     */
    void saveHealthBase(ReadOnlyHealthBase healthBase, Path filePath) throws IOException;

}
