package seedu.address.ui;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.VisitorViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.visitor.Visitor;
import seedu.address.testutil.VisitorBuilder;

/**
 * Test driver class for VisitorView class
 * @author GAO JIAXIN666
 */
public class VisitorViewTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS = FXCollections.observableList(getTypicalPersons());

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private VisitorView visitorView;
    private VisitorViewHandle visitorViewHandle;
    private Visitor visitor;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(ALICE);
        visitor = new VisitorBuilder().build();
        initUi(TYPICAL_PERSONS);
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by
     * {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Person> backingList) {
        visitorView = new VisitorView(backingList);
        uiPartRule.setUiPart(visitorView);
        visitorView.setCurrentSelection(ALICE);
        visitorViewHandle = new VisitorViewHandle(getChildNode(visitorView.getRoot(),
                visitorViewHandle.VISITOR_TABLE_VIEW_ID));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setCurrentSelection_notNull_throwsUnsupportedOperationException() {
        visitorView.setCurrentSelection(ALICE);
    }

    @Test
    public void changeSelection_medicalHistoryChanges() {
        postNow(selectionChangedEventStub);

        assertTrue(visitorViewHandle.getBackingListOfVisitor()
                .equals(ALICE.getVisitorList()
                        .getObservableCopyOfVisitorlist()));
    }
}
