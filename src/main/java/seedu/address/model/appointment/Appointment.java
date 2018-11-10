package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.format.DateTimeFormatter;

/**
 * This class holds information for appointments
 *
 * @author jeffypie369
 *
 */
public class Appointment {
    /** Date format to be used in HMS */
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private static final String DIVIDER = " | ";

    /** Type of medical procedure */
    private String type;

    /** Name of the medical procedure */
    private String procedureName;

    /** Date and time of procedure */
    private String dateTime;

    /** Name of the doctor-in-charge */
    private String docName;

    public Appointment(String type, String procedureName, String dateTime, String docName) {
        requireAllNonNull(type, procedureName, dateTime, docName);
        this.type = type;
        this.procedureName = procedureName;
        this.dateTime = dateTime;
        this.docName = docName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(DIVIDER).append(procedureName).append(DIVIDER).append(dateTime).append(DIVIDER)
                .append(docName);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Appointment) {
            Appointment a = (Appointment) o;
            return type.equals(a.type) && procedureName.equals(a.procedureName)
                    && dateTime.equals(a.dateTime) && docName.equals(a.docName);
        }

        return false;
    }

    public String getType() {
        return type;
    }

    public String getDate_time() {
        return dateTime;
    }

    public String getDoc_name() {
        return docName;
    }

    public String getProcedure_name() {
        return procedureName;
    }
}
