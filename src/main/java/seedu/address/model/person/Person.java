package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.VisitorList;

/**
 * Represents a Person in the address book. Guarantees: details are present and
 * not null, field values are validated, immutable.
 */
public class Person {

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

    private Person(Person p, PrescriptionList pl) {
        nric = p.getNric();
        name = p.getName();
        phone = p.getPhone();
        email = p.getEmail();
        address = p.getAddress();
        tags.addAll(p.getTags());
        prescriptionList = new PrescriptionList(pl);
        appointmentsList = new AppointmentsList(p.getAppointmentsList());
        medicalHistory = new MedicalHistory(p.getMedicalHistory());
        dietCollection = new DietCollection(p.getDietCollection());
        visitorList = new VisitorList(p.getVisitorList());
    }

    private Person(Person p, AppointmentsList al) {
        nric = p.getNric();
        name = p.getName();
        phone = p.getPhone();
        email = p.getEmail();
        address = p.getAddress();
        tags.addAll(p.getTags());
        prescriptionList = new PrescriptionList(p.getPrescriptionList());
        appointmentsList = new AppointmentsList(al);
        medicalHistory = new MedicalHistory(p.getMedicalHistory());
        dietCollection = new DietCollection(p.getDietCollection());
        visitorList = new VisitorList(p.getVisitorList());
    }

    private Person(Person p, MedicalHistory mh) {
        nric = p.getNric();
        name = p.getName();
        phone = p.getPhone();
        email = p.getEmail();
        address = p.getAddress();
        tags.addAll(p.getTags());
        prescriptionList = new PrescriptionList(p.getPrescriptionList());
        appointmentsList = new AppointmentsList(p.getAppointmentsList());
        medicalHistory = new MedicalHistory(mh);
        dietCollection = new DietCollection(p.getDietCollection());
        visitorList = new VisitorList(p.getVisitorList());
    }

    private Person(Person p, DietCollection dc) {
        nric = p.getNric();
        name = p.getName();
        phone = p.getPhone();
        email = p.getEmail();
        address = p.getAddress();
        tags.addAll(p.getTags());
        prescriptionList = new PrescriptionList(p.getPrescriptionList());
        appointmentsList = new AppointmentsList(p.getAppointmentsList());
        medicalHistory = new MedicalHistory(p.getMedicalHistory());
        dietCollection = new DietCollection(dc);
        visitorList = new VisitorList(p.getVisitorList());
    }

    private Person(Person p, VisitorList vl) {
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
        visitorList = new VisitorList(vl);
    }

    public Person withPrescriptionList(PrescriptionList pl) {
        return new Person(this, pl);
    }

    public Person withAppointmentsList(AppointmentsList al) {
        return new Person(this, al);
    }

    public Person withMedicalHistory(MedicalHistory mh) {
        return new Person(this, mh);
    }

    public Person withDietCollection(DietCollection dc) {
        return new Person(this, dc);
    }

    public Person withVisitorList(VisitorList vl) {
        return new Person(this, vl);
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
     * Returns true if both persons of the same name have at least one other
     * identity field that is the same. This defines a weaker notion of equality
     * between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getNric().equals(getNric())
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone())
                        || otherPerson.getEmail().equals(getEmail()));
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
        return otherPerson.getNric().equals(getNric())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(nric, name, phone, email, address, tags, medicalHistory);
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
