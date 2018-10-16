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

    @Before
    public void setUp() {
        allergyOne = "Egg";
        allergyTwo = "Milk";
        culturalRequirement = "Halal";
    }

    @Test
    public void constructor_detail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Diet(null, DietType.ALLERGY));
    }

    @Test
    public void constructor_type_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Diet(allergyOne, null));
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