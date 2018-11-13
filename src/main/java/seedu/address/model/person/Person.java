package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.VisitorList;

/**
 * Represents a Person in HealthBase. Guarantees: details are present and
 * not null, field values are validated, immutable.
 */
public class Person {
    // We used the Reflection API in some places.
    // These constants enable that usage.
    // If the below data fields are renamed, these constants should be changed accordingly.
    private static final String APPOINTMENTSLIST_FIELD_NAME = "appointmentsList";
    private static final String DIETCOLLECTION_FIELD_NAME = "dietCollection";
    private static final String MEDICALHISTORY_FIELD_NAME = "medicalHistory";
    private static final String PRESCRIPTIONLIST_FIELD_NAME = "prescriptionList";
    private static final String VISITORLIST_FIELD_NAME = "visitorList";

    // Identity fields
    private final Nric nric;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final DietCollection dietCollection;
    private final Set<Tag> tags = new HashSet<>();
    private final PrescriptionList prescriptionList;
    private final AppointmentsList appointmentsList;
    private final MedicalHistory medicalHistory;
    private final VisitorList visitorList;

    /**
     * Every field must be present and not null.
     */
    public Person(Nric nric, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(nric, name, phone, email, address, tags);
        this.nric = nric;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.prescriptionList = new PrescriptionList();
        this.appointmentsList = new AppointmentsList();
        this.medicalHistory = new MedicalHistory();
        this.dietCollection = new DietCollection();
        this.visitorList = new VisitorList();
    }

    /**
     * Defensive copy c'tor.
     */
    public Person(Person p) {
        nric = p.getNric();
        name = p.getName();
        phone = p.getPhone();
        email = p.getEmail();
        address = p.getAddress();
        tags.addAll(p.getTags());
        prescriptionList = new PrescriptionList(p.getPrescriptionList());
        appointmentsList = new AppointmentsList(p.getAppointmentsList());
        medicalHistory = new MedicalHistory(p.getMedicalHistory());
        dietCollection = new DietCollection(p.getDietCollection());
        visitorList = new VisitorList(p.getVisitorList());
    }

    /**
     * Returns a copy of this {@code Person} with the PrescriptionList changed.
     */
    public Person withPrescriptionList(PrescriptionList pl) {
        Person p = new Person(this);
        setPropertyForObject(PRESCRIPTIONLIST_FIELD_NAME, new PrescriptionList(pl), p);
        return p;
    }

    /**
     * Returns a copy of this {@code Person} with the AppointmentsList changed.
     */
    public Person withAppointmentsList(AppointmentsList al) {
        Person p = new Person(this);
        setPropertyForObject(APPOINTMENTSLIST_FIELD_NAME, new AppointmentsList(al), p);
        return p;
    }

    /**
     * Returns a copy of this {@code Person} with the MedicalHistory changed.
     */
    public Person withMedicalHistory(MedicalHistory mh) {
        Person p = new Person(this);
        setPropertyForObject(MEDICALHISTORY_FIELD_NAME, new MedicalHistory(mh), p);
        return p;
    }

    /**
     * Returns a copy of this {@code Person} with the DietCollection changed.
     */
    public Person withDietCollection(DietCollection dc) {
        Person p = new Person(this);
        setPropertyForObject(DIETCOLLECTION_FIELD_NAME, new DietCollection(dc), p);
        return p;
    }

    /**
     * Returns a copy of this {@code Person} with the VisitorList changed.
     */
    public Person withVisitorList(VisitorList vl) {
        Person p = new Person(this);
        setPropertyForObject(VISITORLIST_FIELD_NAME, new VisitorList(vl), p);
        return p;
    }

    public Nric getNric() {
        return nric;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public PrescriptionList getPrescriptionList() {
        return prescriptionList;
    }

    public VisitorList getVisitorList() {
        return visitorList;
    }

    public AppointmentsList getAppointmentsList() {
        return appointmentsList;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public DietCollection getDietCollection() {
        return this.dietCollection;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same NRIC. NRIC is used as the
     * unique identifier of Person objects.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getNric().equals(getNric());
    }

    private void setPropertyForObject(String fieldName, Object prop, Object obj) {
        Field f;
        try {
            f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(obj, prop);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            LogsCenter.getLogger(this.getClass()).log(Level.SEVERE, "", e);
        }

    }

    /**
     * Returns true if both persons have the same identity and data fields. This
     * defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getNric().equals(getNric());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(nric);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getNric())
            .append(" Name: ")
            .append(getName())
            .append(" Phone: ")
            .append(getPhone())
            .append(" Email: ")
            .append(getEmail())
            .append(" Address: ")
            .append(getAddress());

        if (getTags().isEmpty()) {
            return builder.toString();
        }

        builder.append(" Drug Allergies: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
