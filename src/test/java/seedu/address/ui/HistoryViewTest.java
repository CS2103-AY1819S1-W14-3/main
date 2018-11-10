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

import guitests.guihandles.HistoryViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.person.Person;
import seedu.address.testutil.DiagnosisBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Test driver class for HistoryView class
 */
public class HistoryViewTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS = FXCollections.observableList(getTypicalPersons());
    private static final int PERFORMANCE_TEST_TIMEOUT = 2500;

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private NewResultAvailableEvent addmhCommandSuccessEventStub;
    private HistoryView historyView;
    private HistoryViewHandle historyViewHandle;
    private Diagnosis diagnosis;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(ALICE);
        addmhCommandSuccessEventStub = new NewResultAvailableEvent(
                "New medical history/record successfully added: "
                + ALICE.getNric().toString());
        diagnosis = new DiagnosisBuilder().build();
        initUi(TYPICAL_PERSONS);
    }

    //author
    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by
     * {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Person> backingList) {
        historyView = new HistoryView(backingList);
        uiPartRule.setUiPart(historyView);
        historyView.setCurrentSelection(ALICE);
        historyViewHandle = new HistoryViewHandle(getChildNode(historyView.getRoot(),
                historyViewHandle.DIAGNOSIS_TABLE_VIEW_ID));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setCurrentSelection_notNull_throwsUnsupportedOperationException() {
        historyView.setCurrentSelection(ALICE);
    }

    @Test
    public void changeSelection_medicalHistoryChanges() {
        postNow(selectionChangedEventStub);

        assertTrue(historyViewHandle.getBackingListOfDiagnoses()
                                    .equals(ALICE.getMedicalHistory()
                                                 .getObservableCopyOfMedicalHistory()));
    }

    @Test
    public void addmh_displayUpdates() {
        ObservableList<Diagnosis> toCompareWith = FXCollections.observableArrayList();
        toCompareWith.addAll(ALICE.getMedicalHistory()
                .getObservableCopyOfMedicalHistory());
        toCompareWith.add(diagnosis);

        ALICE.getMedicalHistory()
                .add(diagnosis);
        postNow(addmhCommandSuccessEventStub);

        assertTrue(historyViewHandle.getBackingListOfDiagnoses()
                                    .equals(toCompareWith));
    }

    @Test
    public void performanceTest() {
        ObservableList<Diagnosis> backingList = createBackingList(10000);
        Person aliceAlter = new PersonBuilder(ALICE).withMedicalHistory(backingList).build();

        ObservableList<Person> initUiBackingList = FXCollections.observableArrayList(
                Arrays.asList(new Person[] {aliceAlter}));

        assertTimeoutPreemptively(ofMillis(PERFORMANCE_TEST_TIMEOUT), () -> {
            initUi(initUiBackingList);
            postNow(new PersonPanelSelectionChangedEvent(aliceAlter));
        }, "Display of diagnoses records exceeded time limit.");
    }

    private ObservableList<Diagnosis> createBackingList(int num) {
        return FXCollections.observableArrayList(Stream.generate(() -> diagnosis)
                                                       .limit(num)
                                                       .collect(Collectors.toList()));
    }
}
