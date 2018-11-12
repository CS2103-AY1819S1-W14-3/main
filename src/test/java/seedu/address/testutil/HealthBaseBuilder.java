package seedu.address.testutil;

import seedu.address.model.HealthBase;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code HealthBase ab = new HealthBaseBuilder().withPerson("John", "Doe").build();}
 */
public class HealthBaseBuilder {

    private HealthBase healthBase;

    public HealthBaseBuilder() {
        healthBase = new HealthBase();
    }

    public HealthBaseBuilder(HealthBase healthBase) {
        this.healthBase = healthBase;
    }

    /**
     * Adds a new {@code Person} to the {@code HealthBase} that we are building.
     */
    public HealthBaseBuilder withPerson(Person person) {
        healthBase.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code checkedOutPerson} to the {@code HealthBase} that we are building.
     */
    public HealthBaseBuilder withCheckedOutPerson(Person person) {
        healthBase.addCheckedOutPerson(person);
        return this;
    }

    public HealthBase build() {
        return healthBase;
    }
}
