package seedu.address.storage;

//@@author yuntongzhang

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietType;

/**
 * Test driver for XmlAdaptedDiet class.
 */
public class XmlAdaptedDietTest {
    static final String VALID_DETAIL = "Milk";
    static final DietType VALID_TYPE = DietType.ALLERGY;
    static final String OTHER_VALID_DETAIL = "Egg";
    static final DietType OTHER_VALID_TYPE = DietType.CULTURAL;

    private String detail;
    private DietType type;
    private Diet diet;

    @Before
    public void setUp() {
        this.detail = VALID_DETAIL;
        this.type = VALID_TYPE;
        this.diet = new Diet(detail, type);
    }

    @Test
    public void toModelType_validDetailAndType_returnsDiet() {
        XmlAdaptedDiet xmlDiet = new XmlAdaptedDiet(diet);
        assertEquals(diet, xmlDiet.toModelType());
    }

    @Test
    public void equals_itself_returnsTrue() {
        XmlAdaptedDiet xmlDiet = new XmlAdaptedDiet(VALID_DETAIL, VALID_TYPE);
        assertTrue(xmlDiet.equals(xmlDiet));
    }

    @Test
    public void equals_copyOfSource_returnsTrue() {
        XmlAdaptedDiet xmlDiet = new XmlAdaptedDiet(VALID_DETAIL, VALID_TYPE);
        XmlAdaptedDiet otherXmlDiet = new XmlAdaptedDiet(diet);
        assertTrue(xmlDiet.equals(otherXmlDiet));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        XmlAdaptedDiet xmlDiet = new XmlAdaptedDiet(VALID_DETAIL, VALID_TYPE);
        assertFalse(xmlDiet.equals(1));
    }

    @Test
    public void equals_differntDetail_returnsFalse() {
        XmlAdaptedDiet xmlDiet = new XmlAdaptedDiet(VALID_DETAIL, VALID_TYPE);
        XmlAdaptedDiet otherXmlDiet = new XmlAdaptedDiet(OTHER_VALID_DETAIL, VALID_TYPE);
        assertFalse(xmlDiet.equals(otherXmlDiet));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        XmlAdaptedDiet xmlDiet = new XmlAdaptedDiet(VALID_DETAIL, VALID_TYPE);
        XmlAdaptedDiet otherXmlDiet = new XmlAdaptedDiet(VALID_DETAIL, OTHER_VALID_TYPE);
        assertFalse(xmlDiet.equals(otherXmlDiet));
    }
}
