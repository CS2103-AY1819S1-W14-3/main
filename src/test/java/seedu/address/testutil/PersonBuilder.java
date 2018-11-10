package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.visitor.VisitorList;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {
    public static final String DEFAULT_NRIC = "S0000100Z";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Nric nric;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private MedicalHistory medicalHistory;
    private PrescriptionList prescriptionList;
    private VisitorList visitorList;
    private AppointmentsList appointmentsList;
    private DietCollection dietCollection;

    public PersonBuilder() {
        nric = new Nric(DEFAULT_NRIC);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        prescriptionList = new PrescriptionList();
        medicalHistory = new MedicalHistory();
        visitorList = new VisitorList();
        appointmentsList = new AppointmentsList();
        dietCollection = new DietCollection();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        nric = personToCopy.getNric();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        medicalHistory = personToCopy.getMedicalHistory();
        prescriptionList = personToCopy.getPrescriptionList();
        visitorList = new VisitorList(personToCopy.getVisitorList());
        appointmentsList = personToCopy.getAppointmentsList();
        dietCollection = personToCopy.getDietCollection();
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = new PrescriptionList(prescriptionList);
        return this;
    }

    /**
     * Sets the {@code ArrayList<Diagnosis> medicalhistory} of the {@code Person} that we are building.
     */
    public PersonBuilder withMedicalHistory(List<Diagnosis> medicalHistory) {
        this.medicalHistory = new MedicalHistory(medicalHistory);
        return this;
    }

    /**
     * Sets the {@code ArrayList<Visitor> visitorlist} of the {@code Person} that we are building.
     */
    public PersonBuilder withVisitorList(VisitorList vl) {
        this.visitorList = vl;
        return this;
    }

    /**
     * Sets the {@code AppointmentsList} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppointmentsList(List<Appointment> appointmentsList) {
        this.appointmentsList = new AppointmentsList(appointmentsList);
        return this;
    }

    /**
     * Sets the {@code DietCollection} of the {@code Person} that we are building.
     * @param dietSet The set of Diet which this person should have.
     * @return The updated PersonBuilder.
     */
    public PersonBuilder withDietCollection(Set<Diet> dietSet) {
        this.dietCollection = new DietCollection(dietSet);
        return this;
    }

    /**
     * construct person class
     */
    public Person build() {
        return new Person(nric, name, phone, email, address, tags, prescriptionList, appointmentsList,
                medicalHistory, visitorList);
    }

}
