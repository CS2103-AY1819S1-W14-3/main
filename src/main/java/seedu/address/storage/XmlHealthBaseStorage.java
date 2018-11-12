package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyHealthBase;

/**
 * A class to access HealthBase data stored as an xml file on the hard disk.
 */
public class XmlHealthBaseStorage implements HealthBaseStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlHealthBaseStorage.class);

    private Path filePath;

    public XmlHealthBaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHealthBaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHealthBase> readHealthBase() throws DataConversionException, IOException {
        return readHealthBase(filePath);
    }

    /**
     * Similar to {@link #readHealthBase()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyHealthBase> readHealthBase(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("HealthBase file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableHealthBase xmlHealthBase = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlHealthBase.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveHealthBase(ReadOnlyHealthBase healthBase) throws IOException {
        saveHealthBase(healthBase, filePath);
    }

    /**
     * Similar to {@link #saveHealthBase(ReadOnlyHealthBase)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveHealthBase(ReadOnlyHealthBase healthBase, Path filePath) throws IOException {
        requireNonNull(healthBase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableHealthBase(healthBase));
    }

}
