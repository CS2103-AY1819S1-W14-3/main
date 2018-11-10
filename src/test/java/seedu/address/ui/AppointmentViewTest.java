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

import guitests.guihandles.AppointmentViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Test driver class for AppointmentView
 * @author Jefferson Sie
 */
public class AppointmentViewTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS = FXCollections.observableList(getTypicalPersons());
    private static final int PERFORMANCE_TEST_TIMEOUT = 2500;

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private NewResultAvailableEvent addapptCommandSuccessEventStub;
    private AppointmentView apptView;
    private AppointmentViewHandle apptViewHandle;
    private Appointment appt;

    @Before
    public void setUp() throws IllegalValueException {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(ALICE);
        addapptCommandSuccessEventStub = new NewResultAvailableEvent("Appointment added for patient: "
                + ALICE.getNric().toString());
        appt = new AppointmentBuilder().build();
        initUi(TYPICAL_PERSONS);
    }

    /**
     * Initialises {@code personListPanelHandle} with a {@code PersonListPanel} backed by
     * {@code backingList}
     */
    private void initUi(ObservableList<Person> backingList) {
        apptView = new AppointmentView(backingList);
        uiPartRule.setUiPart(apptView);
        apptView.setCurrentSelection(ALICE);
        apptViewHandle = new AppointmentViewHandle(getChildNode(apptView.getRoot(),
                AppointmentViewHandle.APPOINTMENT_TABLE_VIEW_ID));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setCurrentSelection_notNull_throwsUnsupportedOperationException() {
        apptView.setCurrentSelection(ALICE);
    }

    @Test
    public void changeSelection_appointmentListChanges() {
        postNow(selectionChangedEventStub);
        assertTrue(apptViewHandle.getBackingListOfAppointments()
                                    .equals(ALICE.getAppointmentsList()
                                                    .getObservableCopyOfAppointmentsList()));
    }

    @Test
    public void addappt_displayUpdates() {
        ObservableList<Appointment> toCompareWith = FXCollections.observableArrayList();
        toCompareWith.addAll(ALICE.getAppointmentsList()
                                    .getObservableCopyOfAppointmentsList());
        toCompareWith.add(appt);
        ALICE.getAppointmentsList()
                .add(appt);
        postNow(addapptCommandSuccessEventStub);
        assertTrue(apptViewHandle.getBackingListOfAppointments()
                                            .equals(toCompareWith));
    }

    @Test
    public void performanceTest() {
        ObservableList<Appointment> backingList = createBackingList(10000);
        Person aliceAlter = new PersonBuilder(ALICE).withAppointmentsList(backingList)
                                                        .build();
        ObservableList<Person> initUiBackingList = FXCollections.observableArrayList(
                Arrays.asList(new Person[] { aliceAlter }));
        assertTimeoutPreemptively(ofMillis(PERFORMANCE_TEST_TIMEOUT), () -> {
            initUi(initUiBackingList);
            postNow(new PersonPanelSelectionChangedEvent(aliceAlter));
        }, "Displayt of appointments records exceeded time limit.");
    }

    private ObservableList<Appointment> createBackingList(int num) {
        return FXCollections.observableArrayList(Stream.generate(() -> appt)
                                                        .limit(num)
                                                        .collect(Collectors.toList()));
    }
}
