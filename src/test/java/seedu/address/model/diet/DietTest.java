package seedu.address.model.diet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;

//@@author yuntongzhang

/**
 * Test driver class for Diet POJO class functionality.
 * @author yuntongzhang
 */
public class DietTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String allergyOne;
    private String allergyTwo;
    private String culturalRequirement;
    private String physicalDifficulty;

    @Before
    public void setUp() {
        allergyOne = "Egg";
        allergyTwo = "Milk";
        culturalRequirement = "Halal";
        physicalDifficulty = "Hands cannot move.";
    }

    @Test
    public void constructor_nullDetail_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Diet(null, DietType.ALLERGY));
    }

    @Test
    public void constructor_nullType_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Diet(allergyOne, null));
    }

    @Test
    public void isAllergy_returnsTrue() {
        assertTrue(new Diet(allergyOne, DietType.ALLERGY).isAllergy());
    }

    @Test
    public void isCulturalRequirement_returnsTrue() {
        assertTrue(new Diet(culturalRequirement, DietType.CULTURAL).isCulturalRequirement());
    }

    @Test
    public void isPhysicalDifficulty_returnsTrue() {
        assertTrue(new Diet(physicalDifficulty, DietType.PHYSICAL).isPhysicalDifficulty());
    }

    @Test
    public void equals() {
        Diet eggAllergy = new Diet(allergyOne, DietType.ALLERGY);
        Diet eggAllergyCopy = new Diet(allergyOne, DietType.ALLERGY);
        Diet milkAllergy = new Diet(allergyTwo, DietType.ALLERGY);
        Diet halalCultural = new Diet(culturalRequirement, DietType.CULTURAL);

        // Diet equals to itself
        assertTrue(eggAllergy.equals(eggAllergy));

        // Diet equals to the copy with same detail and type
        assertTrue(eggAllergy.equals(eggAllergyCopy));

        // Diet does not equal to other diet with same type but different detail
        assertFalse(eggAllergy.equals(milkAllergy));

        // Diet does not equal to other diet with different type and different detail
        assertFalse(eggAllergy.equals(halalCultural));
    }
}
