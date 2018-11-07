package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePersonList checkedOutPersons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        checkedOutPersons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replace the contents of the checkedOutPerson list with {@code checkedOutPersons}.
     * {@code checkedOutPersons} must not contain duplicate persons.
     */
    public void setCheckedOutPersons(List<Person> checkedOutPersons) {
        this.checkedOutPersons.setPersons(checkedOutPersons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setCheckedOutPersons(newData.getCheckedOutPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true of a person with the same indetity as {@code person} exists in the checked out patient list.
     */
    public boolean hasCheckedOutPerson(Person person) {
        requireNonNull(person);
        return checkedOutPersons.contains(person);
    }

    /**
     * Check out a patient form the persons list to the checkedOutPersons list.
     * The person {@code toCheckOut} must exist in the persons list.
     * @param toCheckOut
     */
    public void checkOutPerson(Person toCheckOut) {
        requireNonNull(toCheckOut);
        persons.remove(toCheckOut);
        checkedOutPersons.add(toCheckOut);
    }

    /**
     * Re-checkin a patient from the checkedOutPersons list to the persons list.
     * The person {@code toReturn} must exist in the checkedOutPersons list.
     */
    public void reCheckInPerson(Person toReturn) {
        requireNonNull(toReturn);
        checkedOutPersons.remove(toReturn);
        persons.add(toReturn);
    }

    /**
     * Adds a person to the checkedOutPerson list of the address book.
     * The person must not already exist in the checkedOutPerson list of the address book.
     */
    public void addCheckedOutPerson(Person p) {
        checkedOutPersons.add(p);
    }

    /**
     * Adds a person to the person list of address book.
     * The person must not already exist in person list of the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getCheckedOutPersonList() {
        return checkedOutPersons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && checkedOutPersons.equals(((AddressBook) other).checkedOutPersons));
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, checkedOutPersons);
    }

    //todo add records to an existing patient
}
