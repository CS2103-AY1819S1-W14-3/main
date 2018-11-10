package seedu.address.model.medicalhistory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Timestamp class used in the {@code Diagnosis} class.
 * Provides an immutable timestamp of when a diagnosis was created.
 */
public class Timestamp {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
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
