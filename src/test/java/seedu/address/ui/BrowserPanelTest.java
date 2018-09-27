package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PrescriptionBuilder;

public class BrowserPanelTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS = FXCollections.observableList(getTypicalPersons());
    private static final int PERFORMANCE_TEST_TIMEOUT = 2500;

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private NewResultAvailableEvent addmedsCommandSuccessEventStub;
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;
    private Prescription prescription;

    @Before
    public void setUp() throws IllegalValueException {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(ALICE);
        addmedsCommandSuccessEventStub = new NewResultAvailableEvent("Medication added for patient: "
                + ALICE.getNric()
                       .toString());
        prescription = new PrescriptionBuilder().build();
        initUi(TYPICAL_PERSONS);
    }

    // @@author
    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by
     * {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Person> backingList) {
        browserPanel = new BrowserPanel(backingList);
        uiPartRule.setUiPart(browserPanel);
        browserPanelHandle = new BrowserPanelHandle(getChildNode(browserPanel.getRoot(),
                BrowserPanelHandle.PRESCRIPTION_TABLE_VIEW_ID));
    }

    @Test
    public void changeSelection_prescriptionListChanges() {
        postNow(selectionChangedEventStub);
        // We only need to verify that the ObservableList passed to the BrowserPanel contains
        // the appropriate information, then we are done.

        assertTrue(browserPanelHandle.getBackingListOfPrescriptions()
                                     .equals(ALICE.getPrescriptionList()
                                                  .getReadOnlyList()));
    }

    @Test
    public void addmeds_displayUpdates() {
        ObservableList<Prescription> toCompareWith = FXCollections.observableArrayList();
        toCompareWith.addAll(ALICE.getPrescriptionList()
                                  .getReadOnlyList());
        toCompareWith.add(prescription);

        ALICE.getPrescriptionList()
             .add(prescription);
        browserPanel.setCurrentSelection(ALICE);
        postNow(addmedsCommandSuccessEventStub);

        assertTrue(browserPanelHandle.getBackingListOfPrescriptions()
                                     .equals(toCompareWith));
    }

    @Test
    public void performanceTest() {
        ObservableList<Prescription> backingList = createBackingList(10000);
        Person aliceAlter = new PersonBuilder(ALICE).withPrescriptionList(backingList)
                                                    .build();
        ObservableList<Person> initUiBackingList = FXCollections.observableArrayList(
                Arrays.asList(new Person[] { aliceAlter }));

        assertTimeoutPreemptively(ofMillis(PERFORMANCE_TEST_TIMEOUT), () -> {
            initUi(initUiBackingList);
            postNow(new PersonPanelSelectionChangedEvent(aliceAlter));
        }, "Display of prescription records exceeded time limit.");
    }

    private ObservableList<Prescription> createBackingList(int num) {
        return FXCollections.observableArrayList(Stream.generate(() -> prescription)
                                                       .limit(num)
                                                       .collect(Collectors.toList()));
    }
}
