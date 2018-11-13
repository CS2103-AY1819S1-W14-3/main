package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.HealthBase;
import seedu.address.model.ReadOnlyHealthBase;
import seedu.address.model.person.Person;

/**
 * An Immutable HealthBase that is serializable to XML format
 */
@XmlRootElement(name = "healthbase")
public class XmlSerializableHealthBase {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_CHECKED_OUT_PERSON =
        "CheckedOutPersons list contains duplicate person(s).";

    @XmlElement(name = "person")
    private List<XmlAdaptedPerson> persons;

    @XmlElement(name = "checkedout")
    private List<XmlAdaptedPerson> checkedOutPersons;

    /**
     * Creates an empty XmlSerializableHealthBase.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableHealthBase() {
        persons = new ArrayList<>();
        checkedOutPersons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableHealthBase(ReadOnlyHealthBase src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        checkedOutPersons.addAll(src.getCheckedOutPersonList().stream()
            .map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this HealthBase into the model's {@code HealthBase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public HealthBase toModelType() throws IllegalValueException {
        HealthBase healthBase = new HealthBase();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (healthBase.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            healthBase.addPerson(person);
        }

        for (XmlAdaptedPerson p : checkedOutPersons) {
            Person person = p.toModelType();
            if (healthBase.hasCheckedOutPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CHECKED_OUT_PERSON);
            }
            healthBase.addCheckedOutPerson(person);
        }
        return healthBase;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableHealthBase)) {
            return false;
        }
        return persons.equals(((XmlSerializableHealthBase) other).persons)
            && checkedOutPersons.equals(((XmlSerializableHealthBase) other).checkedOutPersons);
    }
}
