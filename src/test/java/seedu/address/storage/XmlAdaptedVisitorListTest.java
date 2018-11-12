package seedu.address.storage;

//@@author GAO JIAXIN666

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XmlAdaptedVisitorListTest {
    private XmlAdaptedVisitorList xmlAdaptedVisitorList;
    private XmlAdaptedVisitorList xmlAdaptedVisitorList1;
    private XmlAdaptedVisitor xmlAdaptedVisitor;
    private XmlAdaptedVisitor xmlAdaptedVisitor1;
    private List<XmlAdaptedVisitor> xmLvL = new ArrayList<>();
    private List<XmlAdaptedVisitor> xmLvL1 = new ArrayList<>();

    @Before
    public void setUp() {
        xmlAdaptedVisitor = new XmlAdaptedVisitor(XmlAdaptedVisitorTest.VALID_VISITOR);
        xmlAdaptedVisitor1 = new XmlAdaptedVisitor(XmlAdaptedVisitorTest.OTHER_VALID_VISITOR);
        xmLvL.add(xmlAdaptedVisitor);
        xmLvL1.add(xmlAdaptedVisitor1);
        xmlAdaptedVisitorList = new XmlAdaptedVisitorList(xmLvL);
        xmlAdaptedVisitorList1 = new XmlAdaptedVisitorList(xmLvL1);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(xmlAdaptedVisitorList.equals(0));
    }

    @Test
    public void equals_itself_returnsTrue() {
        assertTrue(xmlAdaptedVisitorList.equals(xmlAdaptedVisitorList));
    }

    @Test
    public void equals_differentVisitorList_returnsFalse() {
        assertFalse(xmlAdaptedVisitorList.equals(xmlAdaptedVisitorList1));
    }

    @Test
    public void equals_objectAndDefensiveCopy_returnsTrue() {
        assertTrue(xmlAdaptedVisitorList.equals(new XmlAdaptedVisitorList(xmlAdaptedVisitorList)));
    }
}
