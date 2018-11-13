package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyHealthBase newData);

    /** Returns the HealthBase */
    ReadOnlyHealthBase getHealthBase();

    /**
     * Returns true if a person with the same identity as {@code person} exists in HealthBase.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the list of checked out patients.
     */
    boolean hasCheckedOutPerson(Person person);

    /**
     * Checkout the given person.
     * The person must exist in person list of HealthBase.
     */
    void checkOutPerson(Person person);

    /**
     * Re-checkin the given person.
     * The person must exist in the checkedOutPerson list of HealthBase.
     */
    void reCheckInPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in HealthBase.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in HealthBase.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in HealthBase.
     * The person identity of {@code editedPerson} must not be the same as another existing person in HealthBase.
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered checked out person list */
    ObservableList<Person> getFilteredCheckedOutPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
