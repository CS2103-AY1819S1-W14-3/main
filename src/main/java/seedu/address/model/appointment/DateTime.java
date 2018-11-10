package seedu.address.model.appointment;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Handles the date and time of appointments
 */
public class DateTime {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public static final String MESSAGE_DATE_TIME_BEFORE_NOW = "Date and time must be after current date and time";
    // TODO: improve time to be restricted
    public static final String DATE_TIME_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1"
            + "[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1"
            + "\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2}\\s\\d\\d:\\d\\d)$";

    private Calendar apptDate;

    public DateTime(Calendar apptDate) throws IllegalValueException {
        Date date = Calendar.getInstance().getTime();
        Date appt = apptDate.getTime();
        if (appt.before(date)) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_BEFORE_NOW);
        }
        checkArgument(isValidDateTime(apptDate.toString()));
        this.apptDate = apptDate;
    }

    @Override
    public String toString() {
        return DATE_TIME_FORMAT.format(apptDate.getTime());
    }

    /**
     * Returns if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(DATE_TIME_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof DateTime) {
            DateTime d = (DateTime) o;
            return apptDate.get(Calendar.DAY_OF_YEAR) == d.apptDate.get(Calendar.DAY_OF_YEAR) // check proper way
                    && apptDate.get(Calendar.DATE) == d.apptDate.get(Calendar.DATE)
                    && apptDate.get(Calendar.MONTH) == d.apptDate.get(Calendar.MONTH)
                    && apptDate.get(Calendar.YEAR) == d.apptDate.get(Calendar.YEAR);
        }

        return false;
    }
}
