package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.commands.SortCommand.SortOrder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.person.Person;

/**
 * The Appointment Panel of the App
 */
public class AppointmentView extends UiPart<Region> implements Swappable, Sortable {
    private static final String FXML = "ApptBrowserPanel.fxml";
    private static final String MESSAGE_CURRENT_SELECTION_NOT_NULL = "There was an attempt "
            + "to set the current selection, but it is not null.";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final String loggingPrefix = "[" + getClass().getName() + "]: ";

    @javafx.fxml.FXML
    private TableView<Appointment> appointmentTableView;

    @javafx.fxml.FXML
    private TableColumn<Appointment, String> typeCol;

    @javafx.fxml.FXML
    private TableColumn<Appointment, String> procedureCol;

    @javafx.fxml.FXML
    private TableColumn<Appointment, String> dateTimeCol;

    @javafx.fxml.FXML
    private TableColumn<Appointment, String> doctorCol;

    private Person currentSelection;
    private ObservableList<Person> persons;
    private SortType sortType = SortType.ASCENDING;
    private ObservableList<TableColumn<Appointment, ?>> sortOrder;

    /**
     * Constructor for Appointment View
     */
    public AppointmentView(ObservableList<Person> persons) {
        super(FXML);
        this.persons = persons;
        this.sortOrder = FXCollections.observableArrayList(new ArrayList<>());
        registerAsAnEventHandler(this);
    }

    /**
     * Sorts the table view according to the defined sorting order and type of table view
     * defined in sortOrder and sortType
     */
    private void sortTableView() {
        for (TableColumn<?, ?> tc : sortOrder) {
            tc.setSortType(sortType);
        }

        appointmentTableView.getSortOrder().setAll(sortOrder);
        appointmentTableView.sort();
    }

    /**
     * Refreshes the table view given a {@code Person}
     */
    private void refreshTableView(Person person) {
        refreshTableView(person.getAppointmentsList());
    }

    /**
     * Refreshes the table view given a {@code AppointmentsList}
     */
    private void refreshTableView(AppointmentsList appointmentsList) {
        setDataSourceForTable(appointmentsList.getObservableCopyOfAppointmentsList());
        setDataSourcesForTableColumns();
    }

    /**
     * The AddressBook {@code Person} is immutable, hence any changes results in a new
     * {@code Person} object being created. This method helps to refresh our reference
     * to the currently selected {@code Person} object to point to the new {@code Person} object
     */
    private Person getNewReferenceToPerson() {
        return persons.filtered(person -> currentSelection.isSamePerson(person)).get(0);
    }

    /**
     * Sets the data source for the {@code TableView}
     * @param source to use for the table
     */
    private void setDataSourceForTable(ObservableList<Appointment> source) {
        appointmentTableView.setItems(source);
    }

    /**
     * Helper method to set all the data sources for each column
     */
    private void setDataSourcesForTableColumns() {
        typeCol.setCellValueFactory(param -> getTypeAsSimpleStringProperty(param));
        procedureCol.setCellValueFactory(param -> getProcedureAsSimpleStringProperty(param));
        dateTimeCol.setCellValueFactory(param -> getDateTimeAsSimpleStringProperty(param));
        doctorCol.setCellValueFactory(param -> getDoctorAsSimpleStringProperty(param));
    }

    /**
     * Setter method to fix the current selected person
     * Only used for testing purposes
     * Works only if the current selection is null
     */
    public void setCurrentSelection(Person p) throws UnsupportedOperationException {
        if (currentSelection != null) {
            throw new UnsupportedOperationException(MESSAGE_CURRENT_SELECTION_NOT_NULL);
        }
        currentSelection = p;
    }

    /**
     * Helper method to extract the type in the required format
     */
    private SimpleStringProperty getTypeAsSimpleStringProperty(CellDataFeatures<Appointment, String> param) {
        return new SimpleStringProperty(param.getValue().getType().toString());
    }

    /**
     * Helper method to extract the procedure in the required format
     */
    private SimpleStringProperty getProcedureAsSimpleStringProperty(CellDataFeatures<Appointment, String> param) {
        return new SimpleStringProperty(param.getValue().getProcedure_name());
    }

    /**
     * Helper method to extract the date and time in the required format
     */
    private SimpleStringProperty getDateTimeAsSimpleStringProperty(CellDataFeatures<Appointment, String> param) {
        return new SimpleStringProperty(param.getValue().getDate_time());
    }

    /**
     * Helper method to extract the doctor's name in the required format
     */
    private SimpleStringProperty getDoctorAsSimpleStringProperty(CellDataFeatures<Appointment, String> param) {
        return new SimpleStringProperty(param.getValue().getDoc_name());
    }

    @Override
    public void refreshView() {
        if (currentSelection == null) {
            return;
        }
        refreshTableView(currentSelection);
    }

    @Override
    public void sortView(SortOrder order, int... colIdx) {
        switch(order) {
        case ASCENDING:
            sortType = SortType.ASCENDING;
            break;
        case DESCENDING:
            sortType = SortType.DESCENDING;
            break;
        default:
            sortType = SortType.ASCENDING;
            break;
        }

        sortOrder.clear();

        ObservableList<TableColumn<Appointment, ?>> columns = appointmentTableView.getColumns();

        for (int i = 0; i < colIdx.length; i++) {
            if (colIdx[i] < 1 || colIdx[i] > columns.size()) {
                continue;
            }
            TableColumn<Appointment, ?> col = columns.get(colIdx[i] - 1);
            sortOrder.add(col);
        }
        sortTableView();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));
        currentSelection = event.getNewSelection();
        refreshView();
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));

        if (currentSelection == null) {
            return;
        }

        currentSelection = getNewReferenceToPerson();
        refreshView();
        sortTableView();
    }
}
