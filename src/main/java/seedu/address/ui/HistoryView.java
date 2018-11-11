package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
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
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Person;

/**
 * The History Panel of the App.
 */
public class HistoryView extends UiPart<Region> implements Swappable, Sortable {
    private static final String FXML = "HistoryPanel.fxml";
    private static final String MESSAGE_CURRENT_SELECTION_NOT_NULL = "There was an attempt "
            + "to set the current selection, but it is not null.";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final String loggingPrefix = "[" + getClass().getName() + "]: ";

    private HashMap<Integer, TableColumn<Diagnosis, String>> colIdxToCol = new HashMap<>();

    // Remember to set the fx:id of the elements in the .fxml file!
    @javafx.fxml.FXML
    private TableView<Diagnosis> diagnosisTableView;

    @javafx.fxml.FXML
    private TableColumn<Diagnosis, String> diagnosisCol;

    @javafx.fxml.FXML
    private TableColumn<Diagnosis, String> doctorNameCol;

    @javafx.fxml.FXML
    private TableColumn<Diagnosis, String> timestampCol;


    private Person currentSelection;
    private ObservableList<Person> persons;
    private SortType sortType = SortType.ASCENDING;
    private ObservableList<TableColumn<Diagnosis, ?>> sortOrder;

    /**
     * C'tor for the History View.
     * We need the MainWindow to supply us with a view of the list of {@code Person}s so that we can
     * update our pointer whenever a {@code Person} is updated.
     */
    public HistoryView(ObservableList<Person> persons) {
        super(FXML);
        this.persons = persons;
        this.sortOrder = FXCollections.observableArrayList(new ArrayList<>());
        registerAsAnEventHandler(this);

        // For easy reference when sorting later.
        colIdxToCol.put(1, diagnosisCol);
        colIdxToCol.put(2, doctorNameCol);
        colIdxToCol.put(3, timestampCol);
    }

    /**
     * Sorts the table view according to the defined sorting order and type of the table view,
     * defined in sortOrder and sortType.
     */
    private void sortTableView() {
        for (TableColumn<?, ?> tc : sortOrder) {
            tc.setSortType(sortType);
        }

        diagnosisTableView.getSortOrder().setAll(sortOrder);
        diagnosisTableView.sort();
    }

    /**
     * Refreshes the table view given a {@code Person}.
     */
    private void refreshTableView(Person person) {
        refreshTableView(person.getMedicalHistory());
    }

    /**
     * Refreshes the table view given a {@code PrescriptionList}.
     * Overloaded method for convenience.
     */
    private void refreshTableView(MedicalHistory mh) {
        setDataSourceForTable(mh.getObservableCopyOfMedicalHistory());
        setDataSourcesForTableColumns();
    }

    /**
     * The AddressBook {@code Person} object is immutable, hence any changes
     * results in a wholly new {@code Person} object being created. This method
     * helps to refresh our reference to the currently selected {@code Person} object
     * to point to the new {@code Person} object.
     */
    private Person getNewReferenceToPerson() {
        return persons.filtered(person -> currentSelection.isSamePerson(person)).get(0);
    }
    /**
     * Sets the data source for the {@code TableView}.
     * @param source to use for the table.
     */
    private void setDataSourceForTable(ObservableList<Diagnosis> source) {
        diagnosisTableView.setItems(source);
    }

    /**
     * Helper method to set all the data sources for each column.
     */
    private void setDataSourcesForTableColumns() {
        diagnosisCol.setCellValueFactory(param -> getDiagnosisAsSimpleStringProperty(param));
        doctorNameCol.setCellValueFactory(param -> getDoctorNameAsSimpleStringProperty(param));
        timestampCol.setCellValueFactory(param -> getTimestampAsSimpleStringProperty(param));
    }

    /**
     * Setter method to fix the currently selected person.
     * Should only be used for testing purposes.
     * Will only work if the current selection is null.
     */
    public void setCurrentSelection(Person p) throws UnsupportedOperationException {
        if (currentSelection != null) {
            throw new UnsupportedOperationException(MESSAGE_CURRENT_SELECTION_NOT_NULL);
        }

        currentSelection = p;
    }

    /**
     * Helper method to obtain the diagnosis description for the cell value factory at
     * the {@code diagnosisCol}.
     */
    private SimpleStringProperty getDiagnosisAsSimpleStringProperty(CellDataFeatures<Diagnosis, String> param) {
        return new SimpleStringProperty(param.getValue().getDiagnosis());
    }

    /**
     * Helper method to obtain the name of the doctor responsible for a given diagnosis
     * for the cell value factory at the doctor name column, {@code doctorNameCol}.
     */
    private SimpleStringProperty getDoctorNameAsSimpleStringProperty(CellDataFeatures<Diagnosis, String> param) {
        return new SimpleStringProperty(param.getValue().getDoctorInCharge());
    }

    /**
     * Helper method to obtain the timestamp for a given diagnosis, for
     * the cell value factory at the {@code timestampCol}.
     */
    private SimpleStringProperty getTimestampAsSimpleStringProperty(CellDataFeatures<Diagnosis, String> param) {
        return new SimpleStringProperty(param.getValue().getTimestamp().toString());
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

        switch (order) {
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

        for (int i = 0; i < colIdx.length; i++) {
            TableColumn<Diagnosis, String> col = colIdxToCol.get(colIdx[i]);
            if (col == null) {
                // No corresponding column for that column index exists
                continue;
            }
            sortOrder.add(col);
        }

        sortTableView();
    }

    /* ====================== Event handling ====================== */

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));
        currentSelection = event.getNewSelection();
        refreshView();
    }

    /**
     * Current strategy is to refresh every panel every time a new result is available.
     * This might potentially burden the app for every new result available.
     * Might want to consider creating a new type of event for submitting medication and subscribing only to that.
     */
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));

        // If we're not looking at anything, there's no need to update.
        if (currentSelection == null) {
            return;
        }

        currentSelection = getNewReferenceToPerson();
        refreshView();
        sortTableView();
    }
}
