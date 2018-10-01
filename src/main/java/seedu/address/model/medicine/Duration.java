package seedu.address.model.medicine;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author snajef
/**
 * POJO class to hold durations.
 *
 * @author Darien Chong
 *
 */
public class Duration {
    public static final double DAYS_PER_WEEK = 7;
    public static final double AVERAGE_DAYS_PER_MONTH = 30.4;
    public static final double AVERAGE_DAYS_PER_YEAR = 365.25;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    public static final String MESSAGE_DURATION_MUST_BE_POSITIVE = "Duration must be a positive value!";

    public static final double MILLISECONDS_IN_A_DAY = 8.64 * Math.pow(10, 7);
    public static final double MILLISECONDS_IN_A_WEEK = DAYS_PER_WEEK * MILLISECONDS_IN_A_DAY;
    public static final double MILLISECONDS_IN_A_MONTH = AVERAGE_DAYS_PER_MONTH * MILLISECONDS_IN_A_DAY;
    public static final double MILLISECONDS_IN_A_YEAR = AVERAGE_DAYS_PER_YEAR * MILLISECONDS_IN_A_DAY;

    private double durationInMilliseconds;
    private Calendar startDate;
    private Calendar endDate;

    public Duration(double ms) throws IllegalValueException {
        if (ms <= 0) {
            throw new IllegalValueException(MESSAGE_DURATION_MUST_BE_POSITIVE);
        }
        durationInMilliseconds = ms;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        endDate.add(Calendar.MILLISECOND, (int) durationInMilliseconds);
    }

    public Duration(int days) throws IllegalValueException {
        if (days <= 0) {
            throw new IllegalValueException(MESSAGE_DURATION_MUST_BE_POSITIVE);
        }
        durationInMilliseconds = MILLISECONDS_IN_A_DAY * days;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        endDate.add(Calendar.MILLISECOND, (int) durationInMilliseconds);
    }

    /** Defensive copy constructor. */
    public Duration(Duration d) {
        durationInMilliseconds = d.getDurationInMilliseconds();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        endDate.add(Calendar.MILLISECOND, (int) durationInMilliseconds);
    }

    public static int getAsDays(double d) {
        return (int) Math.floor(d / MILLISECONDS_IN_A_DAY);
    }

    public static int getAsWeeks(double d) {
        return (int) Math.floor(d / MILLISECONDS_IN_A_WEEK);
    }

    public static int getAsMonths(double d) {
        return (int) Math.floor(d / MILLISECONDS_IN_A_MONTH);
    }

    public static int getAsYears(double d) {
        return (int) Math.floor(d / MILLISECONDS_IN_A_YEAR);
    }

    public double getDurationInMilliseconds() {
        return durationInMilliseconds;
    }

    /**
     * Shifts the date range of the duration. Maintains the same duration in
     * milliseconds.
     *
     * @param newStartDate The new start date to use.
     */
    public void shiftDateRange(Calendar newStartDate) {
        startDate = (Calendar) newStartDate.clone();
        endDate = (Calendar) newStartDate.clone();
        endDate.add(Calendar.MILLISECOND, (int) this.getDurationInMilliseconds());
    }

    public String getStartDateAsString() {
        return DATE_FORMAT.format(startDate.getTime());
    }

    public String getEndDateAsString() {
        return DATE_FORMAT.format(endDate.getTime());
    }

    public String getDurationAsString() {
        StringBuilder duration = new StringBuilder();
        double d = durationInMilliseconds;

        assert d >= 0;

        if (getAsYears(d) >= 1) {
            duration.append(getAsYears(d)).append(" year(s),");
            d -= getAsYears(d) * MILLISECONDS_IN_A_YEAR;
        }

        assert d >= 0;

        if (getAsMonths(d) >= 1) {
            duration.append(" ").append(getAsMonths(d)).append(" month(s),");
            d -= getAsMonths(d) * MILLISECONDS_IN_A_MONTH;
        }

        assert d >= 0;

        if (getAsWeeks(d) >= 1) {
            duration.append(" ").append(getAsWeeks(d)).append(" week(s),");
            d -= getAsWeeks(d) * MILLISECONDS_IN_A_WEEK;
        }

        assert d >= 0;

        if (getAsDays(d) >= 1) {
            duration.append(" ").append(getAsDays(d)).append(" day(s),");
            d -= getAsDays(d) * MILLISECONDS_IN_A_DAY;
        }

        // Remove trailing comma
        String result = duration.toString().trim();
        result = result.substring(0, result.length() - 1);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder fromTo = new StringBuilder();
        fromTo.append("from ").append(getStartDateAsString()).append(" to ").append(getEndDateAsString());

        return fromTo.toString() + " [" + getDurationAsString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Duration) {
            Duration d = (Duration) o;
            return startDate.get(Calendar.DAY_OF_YEAR) == d.startDate.get(Calendar.DAY_OF_YEAR)
                    && endDate.get(Calendar.YEAR) == d.endDate.get(Calendar.YEAR);
        }

        return false;
    }

    /**
     * Helper method to test if two Calendar dates are equal.
     * @param date to test
     * @param anotherDate to test
     * @return true iff the dates are the same on the Calendar (i.e. same day, month, year)
     */
    public static boolean areDatesEqual(Calendar date, Calendar anotherDate) {
        return date == anotherDate || (date.get(Calendar.DAY_OF_YEAR) == anotherDate.get(Calendar.DAY_OF_YEAR)
                && date.get(Calendar.YEAR) == anotherDate.get(Calendar.YEAR));
    }
}
