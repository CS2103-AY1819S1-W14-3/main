package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class XmlAdaptedTimestampTest {
    private static final String VALID_TIMESTAMP_STRING = "7 Nov 2018, 10:19 AM";
    private static final String VALID_TIMESTAMP_DIFFERENT_STRING = "8 Dec 2019, 11:11 PM";
    private static final String INVALID_TIMESTAMP_STRING = "071118";

    private XmlAdaptedTimestamp validXat;

    @Before
    public void setup() throws IllegalValueException {
        this.validXat = new XmlAdaptedTimestamp(VALID_TIMESTAMP_STRING);
    }

    @Test(expected = IllegalValueException.class)
    public void constructorInvalidTimestamp_throwsIllegalValueException() throws IllegalValueException {
        new XmlAdaptedTimestamp(INVALID_TIMESTAMP_STRING);
    }

    @Test
    public void equals_itselfAndItself_returnsTrue() {
        assertTrue(validXat.equals(validXat));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(validXat.equals(1));
    }

    @Test
    public void equals_itselfAndNull_returnsFalse() {
        assertFalse(validXat.equals(null));
    }

    @Test
    public void equals_itselfAndCopyOfModelType_returnsTrue() {
        XmlAdaptedTimestamp anotherXat = new XmlAdaptedTimestamp(validXat.toModelType());
        assertTrue(validXat.equals(anotherXat));
    }

    @Test
    public void equals_differentTimestampStringValues_returnsFalse() throws IllegalValueException {
        XmlAdaptedTimestamp differentXat = new XmlAdaptedTimestamp(VALID_TIMESTAMP_DIFFERENT_STRING);
        assertFalse(validXat.equals(differentXat));
    }
}
