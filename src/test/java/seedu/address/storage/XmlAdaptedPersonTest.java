package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.diet.Diet;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.Assert;
import seedu.address.testutil.DiagnosisBuilder;
import seedu.address.testutil.DietBuilder;
import seedu.address.testutil.PrescriptionBuilder;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NRIC = "S12345AA";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream().map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static Prescription validPrescription;
    private static Appointment validAppointment;
    private static Diagnosis validDiagnosis;
    private static Diet validDiet;
    private static List<XmlAdaptedPrescription> validPrescriptions;
    private static List<XmlAdaptedAppointment> validAppointments;
    private static List<XmlAdaptedDiagnosis> validDiagnoses;
    private static Set<XmlAdaptedDiet> validDiets;

    @Before
    public void setUp() throws IllegalValueException {
        validPrescription = new PrescriptionBuilder().build();
        validPrescriptions = Arrays.asList(new Prescription[] { validPrescription })
                .stream()
                .map(XmlAdaptedPrescription::new)
                .collect(Collectors.toList());

        validAppointment = new AppointmentBuilder().build();
        validAppointments = Arrays.asList(new Appointment[] { validAppointment })
                .stream()
                .map(XmlAdaptedAppointment::new)
                .collect(Collectors.toList());

        validDiagnosis = new DiagnosisBuilder().build();
        validDiagnoses = Arrays.asList(new Diagnosis[] {validDiagnosis})
                .stream()
                .map(XmlAdaptedDiagnosis::new)
                .collect(Collectors.toList());

        validDiet = new DietBuilder().build();
        validDiets = new HashSet<>(Set.of(new XmlAdaptedDiet(validDiet)));
    }

    @Test
    public void constructor_zeroArg_works() {
        new XmlAdaptedPerson();
    }

    @Test
    public void constructor_prescriptionList_returnsPerson() throws IllegalValueException {
        BENSON.getPrescriptionList().add(validPrescription);
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void constructor_appointmentsList_returnsPerson() throws IllegalValueException {
        BENSON.getAppointmentsList().add(validAppointment);
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        BENSON.getPrescriptionList().add(validPrescription);
        BENSON.getAppointmentsList().add(validAppointment);
        BENSON.getMedicalHistory().add(validDiagnosis);
        BENSON.getDietCollection().add(validDiet);
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, validPrescriptions, validAppointments, validDiagnoses, validDiets);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(INVALID_NRIC, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Nric.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void equals_objectAndItself_returnsTrue() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS);
        assertTrue(person.equals(person));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS).equals(1));
    }

    @Test
    public void equals_personAndCopy_returnsTrue() {
        XmlAdaptedPerson bensonCopy = new XmlAdaptedPerson(BENSON);
        XmlAdaptedPerson anotherBensonCopy = new XmlAdaptedPerson(VALID_NRIC, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        assertTrue(bensonCopy.equals(anotherBensonCopy));
    }
}
