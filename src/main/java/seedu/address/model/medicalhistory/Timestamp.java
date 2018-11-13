package seedu.address.model.medicalhistory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * POJO class used in the {@code Diagnosis} class, contains the time stamp data that is formatted
 * according to a defined {@code DateTimeFormatter}.
 *
 * Provides an immutable timestamp of when a diagnosis was created.
 */
public class Timestamp {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
            .withLocale(new Locale("en", "SG"));
    /**
     * Format pattern of {@code DATE_TIME_FORMATTER}, follows pattern specified by java.time.format.DateTimeFormatter.
     */
    public static final String DATE_TIME_FORMATTER_PATTERN = "d mmm yyyy, h:mm a";

    private final String timestamp;


    /**
     * Constructs an immutable timestamp for the medical records.
     */
    public Timestamp() {
        this.timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    /**
     * Constructs an immutable Timestamp based off a given {@code LocalDateTime}
     */
    public Timestamp(LocalDateTime ldt) {
        this.timestamp = ldt.format(DATE_TIME_FORMATTER);
    }

    /**
     * Constructs an immutable Timestamp based off a given string.
     *
     * @param timestampAsString represents a {@code LocalDateTime} which
     *                          follows the {@code DATE_TIME_FORMATTER}
     * @throws DateTimeParseException when the {@code timestampAsString} cannot be parsed into a LocalDateTime
     *                                object that follows the {@code DATE_TIME_FORMATTER}
     */
    public Timestamp(String timestampAsString) throws DateTimeParseException {
        LocalDateTime.parse(timestampAsString, DATE_TIME_FORMATTER);
        this.timestamp = timestampAsString;
    }

    /**
     * Getter method for the timestamp, whenever it was called.
     *
     * @return the timestamp generated at the time whenever the constructor was called.
     */
    public String getTimestamp() {
        return this.timestamp;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timestamp)) {
            return false;
        }

        return timestamp.equals(((Timestamp) other).getTimestamp());
    }

    public String toString() {
        return timestamp;
    }
}
