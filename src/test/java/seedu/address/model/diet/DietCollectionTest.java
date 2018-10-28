package seedu.address.model.diet;

//@@author yuntongzhang

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Test driver for DietCollection wrapper class functionality.
 * @author yuntongzhang
 */
public class DietCollectionTest {
    private Set<Diet> dietSetOne;
    private Set<Diet> dietSetTwo;
    private Diet eggAllergy;
    private Diet milkAllergy;
    private Diet halalRequirement;
    private Diet vegeRequirement;
    private Diet handsDifficulty;

    @Before
    public void setUp() {
        dietSetOne = new HashSet<>();
        dietSetTwo = new HashSet<>();
        eggAllergy = new Diet("Egg", DietType.ALLERGY);
        milkAllergy = new Diet("Milk", DietType.ALLERGY);
        halalRequirement = new Diet("Halal", DietType.CULTURAL);
        vegeRequirement = new Diet("Vegetarian", DietType.CULTURAL);
        handsDifficulty = new Diet("Hands cannot move", DietType.PHYSICAL);

        dietSetOne.add(eggAllergy);
        dietSetOne.add(milkAllergy);
        dietSetOne.add(halalRequirement);
        dietSetOne.add(handsDifficulty);

        dietSetTwo.add(vegeRequirement);
    }

    @Test
    public void constructor_nullSet_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DietCollection(null, dietSetOne));
    }

    @Test
    public void getAllergies() {
        List<Diet> expectedAllergies = new LinkedList<>();
        expectedAllergies.add(milkAllergy);
        expectedAllergies.add(eggAllergy);

        List<Diet> actualAllergies = new DietCollection(dietSetOne).getAllergies();

        assertTrue(actualAllergies.containsAll(expectedAllergies)
                   && expectedAllergies.containsAll(actualAllergies));
    }

    @Test
    public void getCulturalRequirements() {
        List<Diet> expectedCulturalRequirement = new LinkedList<>();
        expectedCulturalRequirement.add(halalRequirement);

        assertEquals(new DietCollection(dietSetOne).getCulturalRequirements(), expectedCulturalRequirement);
    }

    @Test
    public void getPhysicalDifficulty() {
        List<Diet> expectedPhysicalDifficulties = new LinkedList<>();
        expectedPhysicalDifficulties.add(handsDifficulty);

        assertEquals(new DietCollection(dietSetOne).getPhysicalDifficulties(), expectedPhysicalDifficulties);
    }

    @Test
    public void add() {
        Set<Diet> dietSet = new HashSet<>(dietSetOne);
        dietSet.add(vegeRequirement);
        DietCollection expectedCollection = new DietCollection(dietSet);

        DietCollection actualCollection = new DietCollection(dietSetOne);
        actualCollection.add(vegeRequirement);

        assertEquals(actualCollection, expectedCollection);
    }

    @Test
    public void addMoreDiets() {
        Set<Diet> dietSet = new HashSet<>(dietSetOne);
        dietSet.addAll(dietSetTwo);
        DietCollection expectedCollection = new DietCollection(dietSet);

        DietCollection actualCollection = new DietCollection(dietSetOne).addMoreDiets(new DietCollection(dietSetTwo));
        assertEquals(actualCollection, expectedCollection);
    }

    @Test
    public void equals() {
        DietCollection collectionOne = new DietCollection(dietSetOne);
        DietCollection collectionOneCopy = new DietCollection(dietSetOne);
        DietCollection collectionTwo = new DietCollection(dietSetTwo);

        // DietCollection equals to itself
        assertTrue(collectionOne.equals(collectionOne));

        // DietCollection equals to a copy with the same content
        assertTrue(collectionOne.equals(collectionOneCopy));

        // DietCollection does not equal to another collection with different content
        assertFalse(collectionOne.equals(collectionTwo));
    }
}
