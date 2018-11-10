package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MED_HISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.AddmhCommand;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.person.Nric;

/**
 * A utility class for {@code diagnosis}.
 */
public class DiagnosisUtil {

    /**
     * Utility method to get a {@code String} that represents an addmh command.
     *
     * @param nric {@code Nric} of the patient used.
     * @param diagnosis {@code Diagnosis} The new diagnosis of the patient.
     * @return A {@code String} representation of the corresponding addmh command.
     */
    public static String getAddmhCommand(Nric nric, Diagnosis diagnosis) {
        StringBuilder sb = new StringBuilder();
        sb.append(AddmhCommand.COMMAND_WORD).append(" ")
                .append(PREFIX_NRIC).append(nric.toString()).append(" ")
                .append(PREFIX_MED_HISTORY).append(diagnosis.getDiagnosis()).append(" ")
                .append(PREFIX_DOCTOR).append(diagnosis.getDoctorInCharge());

        return sb.toString();
    }
}
