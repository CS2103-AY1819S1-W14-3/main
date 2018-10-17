package seedu.address.model.appointment;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test driver class for TypeFields enum functionality.
 *
 * @author Jefferson Sie
 *
 */

public class TypeFieldsTest {
    private TypeFields typeFields;

    @Before
    public void setUp() {
        typeFields = new TypeFields();
    }

    @After
    public void tearDown() {
        typeFields = null;
    }

    @Test
    public void typeFieldsTest() {
        ArrayList<String> result = typeFields.typeFields();
        Iterator itr = result.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        assertTrue(result.contains("PROP"));
    }
}
