package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores healthbase data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given healthbase data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableHealthBase healthBase)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, healthBase);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns HealthBase in the file or an empty HealthBase
     */
    public static XmlSerializableHealthBase loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableHealthBase.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
