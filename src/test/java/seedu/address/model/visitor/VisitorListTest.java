package seedu.address.model.visitor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Test class for VisitorList
 */
public class VisitorListTest {
    private String visitorName = "GAO JIAXIN";
    private Visitor visitor = new Visitor(visitorName);

    @Test(expected = NullPointerException.class)
    public void constructor_nullList_throwsNullPointerException() {
        new VisitorList((ArrayList<Visitor>) null);
    }

    @Test
    public void equals_emptyVisitorListAndNull_returnsFalse() {
        assertFalse(new VisitorList().equals(null));
    }

    @Test
    public void equals_objectEqualsItself_returnsTrue() {
        VisitorList visitorList = new VisitorList();
        assertTrue(visitorList.equals(visitorList));
    }

    @Test
    public void add() {
        VisitorList visitorList1 = new VisitorList();
        VisitorList visitorList2 = new VisitorList(Arrays.asList(new Visitor[] {visitor}));

        assertFalse(visitorList1.equals(visitorList2));

        visitorList1.add(visitor);
        assertTrue(visitorList1.equals(visitorList2));
    }

    @Test
    public void contains() {
        VisitorList visitorList = new VisitorList();
        assertFalse(visitorList.contains(visitor));

        visitorList.add(visitor);
        assertTrue(visitorList.contains(visitor));
    }

    @Test
    public void equals_noArgsConstructorAndListConstructor_returnsTrue() {
        assertTrue(new VisitorList().equals(new VisitorList(new ArrayList<Visitor>())));
    }
}
