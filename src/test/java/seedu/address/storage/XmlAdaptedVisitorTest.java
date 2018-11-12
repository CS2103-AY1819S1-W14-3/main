package seedu.address.storage;

//@@author GAO JIAXIN666

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visitor.Visitor;

public class XmlAdaptedVisitorTest {
    public static final String VALID_VISITOR = "GAO JIAXIN";
    public static final String OTHER_VALID_VISITOR = "AMY";

    private Visitor visitor;
    private Visitor other_visitor;

    @Before
    public void setUp() throws IllegalValueException {
        visitor = new Visitor(VALID_VISITOR);
        other_visitor = new Visitor(OTHER_VALID_VISITOR);
    }

    @Test
    public void toModelType_validPersonDetails_returnsPerson() {
        XmlAdaptedVisitor xmlAdaptedVisitor = new XmlAdaptedVisitor(visitor);
        assertEquals(visitor, xmlAdaptedVisitor.toModelType());
    }

    @Test
    public void equals_copyOfSource_returnsTrue() {
        XmlAdaptedVisitor xv = new XmlAdaptedVisitor(VALID_VISITOR);
        XmlAdaptedVisitor copy_xv = new XmlAdaptedVisitor(visitor);
        assertTrue(xv.equals(copy_xv));
    }

    @Test
    public void equals_itself_returnsTrue() {
        XmlAdaptedVisitor xv = new XmlAdaptedVisitor(visitor);
        assertTrue(xv.equals(xv));
    }

    @Test
    public void equals_noArgConstructedXmlAdaptedDiagnosis_returnsFalse() {
        XmlAdaptedVisitor xv = new XmlAdaptedVisitor(visitor);
        XmlAdaptedVisitor emptyXv = new XmlAdaptedVisitor();
        assertFalse(xv.equals(emptyXv));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        XmlAdaptedVisitor xv = new XmlAdaptedVisitor(visitor);
        assertFalse(xv.equals(0));
    }

    @Test
    public void equals_originalAndFromModelTypeCopy_returnsTrue() {
        XmlAdaptedVisitor xv = new XmlAdaptedVisitor(visitor);
        XmlAdaptedVisitor xvCopy = new XmlAdaptedVisitor (xv.toModelType());

        assertTrue(xv.equals(xvCopy));
    }

    @Test
    public void equals_differntVisitorName_returnsFalse() {
        XmlAdaptedVisitor xv = new XmlAdaptedVisitor(visitor);
        XmlAdaptedVisitor other_xv = new XmlAdaptedVisitor(other_visitor);
        assertFalse(xv.equals(other_xv));
    }
}
