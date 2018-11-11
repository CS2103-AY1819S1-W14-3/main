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
    public static final String VALID_VISITOR1 = "AMY";

    private Visitor visitor;
    private Visitor visitor1;

    @Before
    public void setUp() throws IllegalValueException {
        visitor = new Visitor(VALID_VISITOR);
        visitor1 = new Visitor(VALID_VISITOR1);
    }

    @Test
    public void toModelType_validPersonDetails_returnsPerson() {
        XmlAdaptedVisitor xmlAdaptedVisitor = new XmlAdaptedVisitor(visitor);
        assertEquals(visitor, xmlAdaptedVisitor.toModelType());
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
        XmlAdaptedVisitor xv1 = new XmlAdaptedVisitor(visitor1);
        assertFalse(xv.equals(xv1));
    }
}
