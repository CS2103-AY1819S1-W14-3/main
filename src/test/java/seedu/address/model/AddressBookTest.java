package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRUG_ALLERGY_PENICILLIN;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getCheckedOutPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Person> checkedOutPersons = Arrays.asList(BENSON);
        AddressBookStub newData = new AddressBookStub(newPersons, checkedOutPersons);

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasCheckedOutPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasCheckedOutPerson(null);
    }

    @Test
    public void hasCheckOutPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasCheckedOutPerson(ALICE));
    }

    @Test
    public void hasCheckedOutPerson_personInAddressBook_returnsTrue() {
        addressBook.addCheckedOutPerson(ALICE);
        assertTrue(addressBook.hasCheckedOutPerson(ALICE));
    }

    @Test
    public void hasCheckedOutPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCheckedOutPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        assertTrue(addressBook.hasCheckedOutPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getCheckedOutPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getCheckedOutPersonList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list and checkedOutPersons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Person> checkedOutPersons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Person> checkedOutPersons) {
            this.persons.setAll(persons);
            this.checkedOutPersons.setAll(checkedOutPersons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Person> getCheckedOutPersonList() {
            return checkedOutPersons;
        }
    }

}
