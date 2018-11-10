package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUG_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;

import seedu.address.logic.commands.CheckinCommand;
import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns a register command string for registering the {@code person}.
     */
    public static String getRegisterCommand(Person person) {
        return RegisterCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns a checkout command String for checking out the {@code person}.
     */
    public static String getCheckoutCommand(Person person) {
        return CheckoutCommand.COMMAND_WORD + " " + PREFIX_NRIC + person.getNric().toString();
    }

    /**
     * Returns a checkin command String for checking in the {@code person}.
     */
    public static String getCheckinCommand(Person person) {
        return CheckinCommand.COMMAND_WORD + " " + PREFIX_NRIC + person.getNric().toString();
    }

    /**
     * Returns a register command string for registering the {@code person} with different NRIC
     */
    public static String getRegisterCommandDiffNric(Person person) {
        return RegisterCommand.COMMAND_WORD + " " + getPersonDetailsDiffNric(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NRIC + person.getNric().toString() + " ");
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(s -> sb.append(PREFIX_DRUG_ALLERGY + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code person}'s details less NRIC
     */
    public static String getPersonDetailsDiffNric(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NRIC + "S0000100L" + " ");
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(s -> sb.append(PREFIX_DRUG_ALLERGY + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given
     * {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getNric().ifPresent(nric -> sb.append(PREFIX_NRIC).append(nric.toString()).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_DRUG_ALLERGY);
            } else {
                tags.forEach(s -> sb.append(PREFIX_DRUG_ALLERGY).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
