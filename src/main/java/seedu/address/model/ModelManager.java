package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.HealthBaseChangedEvent;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of HealthBase data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HealthBase internalHealthBase;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Person> filteredCheckedOutPersons;

    /**
     * Initializes a ModelManager with the given healthBase and userPrefs.
     */
    public ModelManager(ReadOnlyHealthBase healthBase, UserPrefs userPrefs) {
        super();
        requireAllNonNull(healthBase, userPrefs);

        logger.fine("Initializing with HealthBase: " + healthBase + " and user prefs " + userPrefs);

        internalHealthBase = new HealthBase(healthBase);
        filteredPersons = new FilteredList<>(internalHealthBase.getPersonList());
        filteredCheckedOutPersons = new FilteredList<>(internalHealthBase.getCheckedOutPersonList());
    }

    public ModelManager() {
        this(new HealthBase(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyHealthBase newData) {
        internalHealthBase.resetData(newData);
        indicateHealthBaseChanged();
    }

    @Override
    public ReadOnlyHealthBase getHealthBase() {
        return internalHealthBase;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateHealthBaseChanged() {
        raise(new HealthBaseChangedEvent(internalHealthBase));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return internalHealthBase.hasPerson(person);
    }

    @Override
    public boolean hasCheckedOutPerson(Person person) {
        requireNonNull(person);
        return internalHealthBase.hasCheckedOutPerson(person);
    }

    @Override
    public void checkOutPerson(Person person) {
        internalHealthBase.checkOutPerson(person);
        indicateHealthBaseChanged();
    }

    @Override
    public void reCheckInPerson(Person person) {
        internalHealthBase.reCheckInPerson(person);
        indicateHealthBaseChanged();
    }

    @Override
    public void deletePerson(Person target) {
        internalHealthBase.removePerson(target);
        indicateHealthBaseChanged();
    }

    @Override
    public void addPerson(Person person) {
        internalHealthBase.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateHealthBaseChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        internalHealthBase.updatePerson(target, editedPerson);
        indicateHealthBaseChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code HealthBase}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    /**
     * Returns an unmodifiable view of the list of {@code checkedOutPerson} backed by the internal list of
     * {@code HealthBase}
     */
    @Override
    public ObservableList<Person> getFilteredCheckedOutPersonList() {
        return FXCollections.unmodifiableObservableList(filteredCheckedOutPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return internalHealthBase.equals(other.internalHealthBase)
                && filteredPersons.equals(other.filteredPersons);
    }

}
