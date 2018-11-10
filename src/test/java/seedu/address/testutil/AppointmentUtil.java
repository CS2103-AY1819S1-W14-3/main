package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCEDURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

/**
 * Utility class for Appointment
 */
public class AppointmentUtil {
    /**
     * Utility method to get a String for a corresponding addappt method.
     * @param nric The NRIC to use.
     * @param appt The Appointment to use.
     * @return A String representation of the corresponding command.
     */
    public static String getAddApptCommand(Nric nric, Appointment appt) {
        StringBuilder sb = new StringBuilder();
        sb.append(AddApptCommand.COMMAND_WORD).append(" ")
                .append(PREFIX_NRIC).append(nric.toString()).append(" ")
                .append(getAppointmentDetails(appt));

        return sb.toString();
    }

    /**
     * Utility method to get a String corresponding to the details of a given Appointment object.
     * @param appt The appt to use.
     * @return A prefixed String representation of the details of the Appointment object.
     */
    public static String getAppointmentDetails(Appointment appt) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TYPE + appt.getType() + " ");
        sb.append(PREFIX_PROCEDURE + appt.getProcedure_name() + " ");
        sb.append(PREFIX_DATE_TIME + appt.getDate_time() + " ");
        sb.append(PREFIX_DOCTOR + appt.getDoc_name() + " ");

        return sb.toString();
    }
}
