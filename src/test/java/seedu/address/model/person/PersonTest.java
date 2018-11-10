package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRUG_ALLERGY_PENICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.VisitorList;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        person.getTags().remove(0);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullPrescriptionList_throwsNullPointerException() {
        Person person = new Person(new Nric(VALID_NRIC_BOB), new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB),
                new Email(VALID_EMAIL_BOB), new Address(VALID_ADDRESS_BOB), new HashSet<Tag>(), null,
                new AppointmentsList(), new MedicalHistory());
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullAppointmentsList_throwsNullPointerException() {
        Person person = new Person(new Nric(VALID_NRIC_BOB), new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB),
                new Email(VALID_EMAIL_BOB), new Address(VALID_ADDRESS_BOB), new HashSet<Tag>(), new PrescriptionList(),
                null, new MedicalHistory());
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullMedicalHistory_throwsNullPointerException() {
        Person person = new Person(new Nric(VALID_NRIC_BOB), new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB),
                new Email(VALID_EMAIL_BOB), new Address(VALID_ADDRESS_BOB), new HashSet<Tag>(), new PrescriptionList(),
                new AppointmentsList(), null, new VisitorList());
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different drug allergies -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
