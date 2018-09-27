package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSES_PER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSE_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUGNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.commands.AddmedsCommand;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.person.Nric;

//@@author snajef
/**
 * Utility class for Prescription.
 * @author Darien Chong
 *
 */
public class PrescriptionUtil {
    /**
     * Utility method to get a String for a corresponding addmeds method.
     * @param nric The NRIC to use.
     * @param prescription The Prescription to use.
     * @return A String representation of the corresponding command.
     */
    public static String getAddmedsCommand(Nric nric, Prescription prescription) {
        StringBuilder sb = new StringBuilder();
        sb.append(AddmedsCommand.COMMAND_WORD).append(" ")
        .append(PREFIX_NRIC).append(nric.toString()).append(" ")
        .append(getPrescriptionDetails(prescription));

        return sb.toString();
    }

    /**
     * Utility method to get a String corresponding to the details of a given Prescription object.
     * @param prescription The prescription to use.
     * @return A prefixed String representation of the details of the Prescription object.
     */
    public static String getPrescriptionDetails(Prescription prescription) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DRUGNAME + prescription.getDrugName() + " ");
        sb.append(PREFIX_QUANTITY + Double.toString(prescription.getDose()
                                                                .getDose())
                + " ");
        sb.append(PREFIX_DOSE_UNIT + prescription.getDose()
                                                 .getDoseUnit()
                + " ");
        sb.append(PREFIX_DOSES_PER_DAY + Integer.toString(prescription.getDose()
                                                                      .getDosesPerDay())
                + " ");
        sb.append(PREFIX_DURATION + Integer.toString(Duration.getAsDays(prescription.getDuration()
                                                                                    .getDurationInMilliseconds()))
                + " ");

        return sb.toString();
    }
}
