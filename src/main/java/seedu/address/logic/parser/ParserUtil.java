package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietType;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.Visitor;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     *             if the specified index is invalid (not non-zero unsigned
     *             integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    // @@author yuntongzhang
    /**
     * Parse a {@code List<String> diets} in to a {@code Set<Diet> diets}, given the {@code DietType type}.
     * Leading and trialing whitespaces for each item in the List will be trimmed.
     */
    public static Set<Diet> parseDiet(List<String> diets, DietType type) {
        requireAllNonNull(diets, type);
        Set<Diet> parsedDiets = new HashSet<>();
        for (String diet: diets) {
            String trimmedDiet = diet.trim();
            parsedDiets.add(new Diet(trimmedDiet, type));
        }
        return parsedDiets;
    }

    // @@author snajef
    /**
     * Parses a {@code String nric} into a {@code nric}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException
     *             if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedName = nric.trim();
        if (!Nric.isValidNric(trimmedName)) {
            throw new ParseException(Nric.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Nric(trimmedName);
    }

    /**
     * Parses a {@code String diagnosis} into a {@code Diagnosis}. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     *              if the given {@code diagnosis} is invalid.
     */
    public static Diagnosis parseDiagnosis(String diagnosis, String doctor) throws ParseException {
        requireNonNull(diagnosis);
        requireNonNull(doctor);
        String trimmedDiagnosis = diagnosis.trim();
        String trimmedDoctor = doctor.trim();
        if (!Diagnosis.isValidDoctor(trimmedDoctor)) {
            throw new ParseException(Diagnosis.MESSAGE_NAME_CONSTRAINTS_DOCTOR);
        }
        if (!Diagnosis.isValidDiagnosis(trimmedDiagnosis)) {
            throw new ParseException(Diagnosis.MESSAGE_NAME_CONSTRAINT_DIAGNOSIS);
        }
        return new Diagnosis(trimmedDiagnosis, trimmedDoctor);
    }
    // @@ GAO JIAXIN
    /**
     * Parses a {@code String visitor} into a {@code visitor}. Leading
     * and trailing whitespaces will be trimmed.
     * @throws ParseException
     *              if the given {@code visitor} is invalid.
     */
    public static Visitor parseVisitor(String visitor) throws ParseException {
        requireNonNull(visitor);
        String trimmedVisitor = visitor.trim();
        if (!Visitor.isValidVisitor(trimmedVisitor)) {
            throw new ParseException((Visitor.MESSAGE_NAME_CONSTRAINTS));
        }
        return new Visitor(trimmedVisitor);
    }

    //@@author
    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException
     *             if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException
     *             if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException
     *             if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException
     *             if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException
     *             if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    // @@author snajef
    /**
     * Parses a {@code int dose}, {@code String doseUnit},{@int dosePerDay} into a
     * {@code Dose}.
     *
     * @return a Dose object with the parsed parameters as members.
     */
    public static Dose parseDose(double dose, String doseUnit, int dosePerDay) throws IllegalValueException {
        requireNonNull(doseUnit);
        return new Dose(dose, doseUnit, dosePerDay);
    }

    // @@author snajef
    /**
     * Parses a {@code durationInDays} into a {@code Duration}.
     *
     * @return a parsed Duration object.
     * @throws IllegalValueException If the duration in days is not a positive integer.
     */
    public static Duration parseDuration(int durationInDays) throws IllegalValueException {
        return new Duration(durationInDays);
    }

    //@@author jeffypie369
    /**
     * Parses a String date into a LocalDateTime date
     * @param dateTime String of the date
     * @return LocalDateTime object which contains the date
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, Appointment.DATE_TIME_FORMAT);
    }

    //@@author
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a String of column indices into a int array.
     */
    public static int[] parseColIdx(String colIdx) {
        List<Integer> colIdxList = Arrays.stream(colIdx.trim()
                                                       .split(""))
                                         .map(Integer::parseInt)
                                         .collect(Collectors.toList());
        int[] toReturn = new int[colIdxList.size()];

        for (int i = 0; i < colIdxList.size(); i++) {
            toReturn[i] = colIdxList.get(i);
        }

        return toReturn;
    }
}
