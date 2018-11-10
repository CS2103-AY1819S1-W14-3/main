package seedu.address.storage;

//@@author yuntongzhang

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Test driver class for XmlAdaptedDietCollection class.
 */
public class XmlAdaptedDietCollectionTest {
    private XmlAdaptedDietCollection xmlAdaptedDietCollection;
    private XmlAdaptedDiet xmlAdaptedDiet;
    private XmlAdaptedDiet otherXmlAdaptedDiet;

    @Before
    public void setUp() {
        xmlAdaptedDietCollection = new XmlAdaptedDietCollection();
        xmlAdaptedDiet = new XmlAdaptedDiet(XmlAdaptedDietTest.VALID_DETAIL, XmlAdaptedDietTest.VALID_TYPE);
        otherXmlAdaptedDiet = new XmlAdaptedDiet(XmlAdaptedDietTest.OTHER_VALID_DETAIL, XmlAdaptedDietTest.VALID_TYPE);
    }

    @Test
    public void setDiet_success() {
        Set<XmlAdaptedDiet> newDietSet = new HashSet<>();
        newDietSet.add(xmlAdaptedDiet);
        xmlAdaptedDietCollection.setDiet(newDietSet);

        assertEquals(xmlAdaptedDietCollection, new XmlAdaptedDietCollection(newDietSet));
    }

    @Test
    public void equals_itself_returnsTrue() {
        assertTrue(xmlAdaptedDietCollection.equals(xmlAdaptedDietCollection));
    }

    @Test
    public void equals_sameContent_returnsTrue() {
        Set<XmlAdaptedDiet> dietSet = new HashSet<>();
        dietSet.add(xmlAdaptedDiet);
        XmlAdaptedDietCollection xmlDietCollection = new XmlAdaptedDietCollection(dietSet);
        XmlAdaptedDietCollection otherXmlDietCollection = new XmlAdaptedDietCollection(dietSet);

        assertTrue(xmlDietCollection.equals(otherXmlDietCollection));
    }

    @Test
    public void equals_differentContent_returnsFalse() {
        Set<XmlAdaptedDiet> dietSetOne = new HashSet<>();
        dietSetOne.add(xmlAdaptedDiet);
        XmlAdaptedDietCollection xmlDietCollection = new XmlAdaptedDietCollection(dietSetOne);

        Set<XmlAdaptedDiet> dietSetTwo = new HashSet<>();
        dietSetTwo.add(otherXmlAdaptedDiet);
        XmlAdaptedDietCollection otherXmlDietCollection = new XmlAdaptedDietCollection(dietSetTwo);

        assertFalse(xmlDietCollection.equals(otherXmlDietCollection));

    }

    @Test
    public void equals_differentClass_returnsFalse() {
        assertFalse(xmlAdaptedDietCollection.equals(1));
    }
}
