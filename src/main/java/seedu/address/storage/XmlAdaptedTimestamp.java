package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicalhistory.Timestamp;

/**
 * A JAXB-ready version of Timestamp
 */
@XmlRootElement
public class XmlAdaptedTimestamp {
    public static final String MESSAGE_DATE_TIME_FORMAT_INCORRECT = "The given timestamp was not in the "
            + Timestamp.DATE_TIME_FORMATTER_PATTERN;

    @XmlElement(required = true)
    private String timestampAsString;

    /**
     * An empty constructor that is required by JAXB.
     */
    public XmlAdaptedTimestamp(){}

    /**
     * Constructs a JAXB-friendly adapted Timestamp using a string
     * that follows {@code Timestamp.DATE_TIME_FORMATTER_PATTERN}
     */
    public XmlAdaptedTimestamp(String timestampAsString) throws IllegalValueException {
        LocalDateTime ldt;

        try {
            ldt = LocalDateTime.parse(timestampAsString, Timestamp.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_FORMAT_INCORRECT);
        }

        this.timestampAsString = timestampAsString;
    }

    public XmlAdaptedTimestamp(Timestamp source) {
        this.timestampAsString = source.toString();
    }

    /**
     * Returns true if valid date time format as defined by {@code Timestamp.DATE_TIME_FORMATTER_PATTERN}
     */
    public static boolean isValidFormat(String timestampString) throws DateTimeParseException {
        try {
            LocalDateTime ldt = LocalDateTime.parse(timestampString, Timestamp.DATE_TIME_FORMATTER);
            String result = ldt.format(Timestamp.DATE_TIME_FORMATTER);
            return result.equals(timestampString);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Converts this JAXB-friendly adapted Timestamp object into the model's Timestamp object.
     *
     * @throws IllegalValueException if {@code timestampAsString} failed to be parsed into a Timestamp object.
     */
    public Timestamp toModelType() {
        return new Timestamp(timestampAsString);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof XmlAdaptedTimestamp)) {
            return false;
        }

        XmlAdaptedTimestamp xat = (XmlAdaptedTimestamp) o;
        return this.timestampAsString.equals(xat.timestampAsString);
    }
}
