package seedu.address.model.diet;

//@@author yuntongzhang

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a Collection of dietary requirement for a patient.
 * Guarantees: field values are validated and immutable.
 */
public class DietCollection {
    private final Set<Diet> dietSet;

    public DietCollection() {
        this.dietSet = new HashSet<>();
    }

    public DietCollection(Set<Diet> dietSet) {
        Objects.requireNonNull(dietSet);
        this.dietSet = dietSet;
    }

    public DietCollection(Set<Diet>... dietSets) {
        this();
        for (Set<Diet> diets: dietSets) {
            Objects.requireNonNull(diets);
            this.dietSet.addAll(diets);
        }
    }

    public List<Diet> getAllergies() {
        List<Diet> allergies = new LinkedList<>();
        for (Diet diet: dietSet) {
            if (diet.isAllergy()) {
                allergies.add(diet);
            }
        }
        return allergies;
    }

    public List<Diet> getCulturalRequirements() {
        List<Diet> culturalRequirements = new LinkedList<>();
        for (Diet diet: dietSet) {
            if (diet.isCulturalRequirement()) {
                culturalRequirements.add(diet);
            }
        }
        return culturalRequirements;
    }

    public List<Diet> getPhysicalDifficulties() {
        List<Diet> physicalDifficulties = new LinkedList<>();
        for (Diet diet: dietSet) {
            if (diet.isPhysicalDifficulty()) {
                physicalDifficulties.add(diet);
            }
        }
        return physicalDifficulties;
    }

    /**
     * Add one diet requirement to an existing Diet Collection, without creating new copy.
     * @param newDiet The new diet requirement to be added.
     */
    public void add(Diet newDiet) {
        dietSet.add(newDiet);
    }

    /**
     * Add more diet requirement to a copy of the existing Diet Collection.
     * @param newDietCollection The new diet requirements to be added.
     * @return A new copy of DietCollection which has the updated diet requirements.
     */
    public DietCollection addMoreDiets(DietCollection newDietCollection) {
        Set<Diet> updatedDiets = new HashSet<>(this.dietSet);
        updatedDiets.addAll(newDietCollection.dietSet);
        return new DietCollection(updatedDiets);
    }

    /** Wrapper method for Collection::stream */
    public Stream<Diet> stream() {
        return dietSet.stream();
    }

    /**
     * Get a list of allergies for JavaFX TableView.
     * @return A {@code ObservableList} of allergies.
     */
    public ObservableList<Diet> getObservableAllergies() {
        return FXCollections.observableArrayList(new ArrayList<>(getAllergies()));
    }

    /**
     * Get a list of cultural requirements for JavaFX TableView.
     * @return A {@code ObservableList} of cultural requirements.
     */
    public ObservableList<Diet> getObservableCulturalRequirements() {
        return FXCollections.observableArrayList(new ArrayList<>(getCulturalRequirements()));
    }

    /**
     * Get a list of physical difficulties for JavaFX TableView.
     * @return A {@code ObservableList} of physical difficulties.
     */
    public ObservableList<Diet> getObservablePhysicalDifficulties() {
        return FXCollections.observableArrayList(new ArrayList<>(getPhysicalDifficulties()));
    }

    /**
     * Returns true if the two collections of diet requirements are the same.
     *
     * @param other The other Object to be compared with
     * @return Whether this DietCollection is the same as the other Object
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DietCollection)) {
            return false;
        }

        DietCollection otherDietCollection = (DietCollection) other;
        return otherDietCollection.dietSet.equals(this.dietSet);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        List<Diet> allergies = getAllergies();
        List<Diet> culturalRequirements = getCulturalRequirements();
        List<Diet> physicalDifficulties = getPhysicalDifficulties();

        builder.append(" Allergies: ");
        for (Diet allergy: allergies) {
            builder.append(allergy);
        }

        builder.append(" Cultural Requirements: ");
        for (Diet cr: culturalRequirements) {
            builder.append(cr);
        }

        builder.append(" Physical Difficulties: ");
        for (Diet pd: physicalDifficulties) {
            builder.append(pd);
        }

        return builder.toString();
    }
}
