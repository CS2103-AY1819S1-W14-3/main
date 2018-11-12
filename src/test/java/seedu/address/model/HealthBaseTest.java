package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRUG_ALLERGY_PENICILLIN;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalHealthBase;

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

public class HealthBaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final HealthBase healthBase = new HealthBase();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), healthBase.getPersonList());
        assertEquals(Collections.emptyList(), healthBase.getCheckedOutPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        healthBase.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyHealthBase_replacesData() {
        HealthBase newData = getTypicalHealthBase();
        healthBase.resetData(newData);
        assertEquals(newData, healthBase);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Person> checkedOutPersons = Arrays.asList(BENSON);
        HealthBaseStub newData = new HealthBaseStub(newPersons, checkedOutPersons);

        thrown.expect(DuplicatePersonException.class);
        healthBase.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        healthBase.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInHealthBase_returnsFalse() {
        assertFalse(healthBase.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInHealthBase_returnsTrue() {
        healthBase.addPerson(ALICE);
        assertTrue(healthBase.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInHealthBase_returnsTrue() {
        healthBase.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        assertTrue(healthBase.hasPerson(editedAlice));
    }

    @Test
    public void hasCheckedOutPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        healthBase.hasCheckedOutPerson(null);
    }

    @Test
    public void hasCheckOutPerson_personNotInHealthBase_returnsFalse() {
        assertFalse(healthBase.hasCheckedOutPerson(ALICE));
    }

    @Test
    public void hasCheckedOutPerson_personInHealthBase_returnsTrue() {
        healthBase.addCheckedOutPerson(ALICE);
        assertTrue(healthBase.hasCheckedOutPerson(ALICE));
    }

    @Test
    public void hasCheckedOutPerson_personWithSameIdentityFieldsInHealthBase_returnsTrue() {
        healthBase.addCheckedOutPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_DRUG_ALLERGY_PENICILLIN).build();
        assertTrue(healthBase.hasCheckedOutPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        healthBase.getPersonList().remove(0);
    }

    @Test
    public void getCheckedOutPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        healthBase.getCheckedOutPersonList().remove(0);
    }

    /**
     * A stub ReadOnlyHealthBase whose persons list and checkedOutPersons list can violate interface constraints.
     */
    private static class HealthBaseStub implements ReadOnlyHealthBase {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Person> checkedOutPersons = FXCollections.observableArrayList();

        HealthBaseStub(Collection<Person> persons, Collection<Person> checkedOutPersons) {
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
