package seedu.address.ui;

import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Person;

//@@author snajef
/**
 * The Medication Panel (maybe more in the future?) of the App.
 */
public class BrowserPanel extends UiPart<Region> {
    private static final String FXML = "BrowserPanel.fxml";
    private static final String MESSAGE_CURRENT_SELECTION_NOT_NULL = "There was an attempt "
        + "to set the current selection, but it is not null.";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final String loggingPrefix = "[" + getClass().getName() + "]: ";

    // Remember to set the fx:id of the elements in the .fxml file!
    @javafx.fxml.FXML
    private TableView<Prescription> prescriptionTableView;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> drugNameCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> dosageCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> dosageUnitCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> dosesPerDayCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> startDateCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> endDateCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> durationCol;

    @javafx.fxml.FXML
    private TableColumn<Prescription, String> activePrescriptionCol;

    private Person currentSelection;
    private ObservableList<Person> persons;

    /**
     * C'tor for the BrowserPanel.
     * We need the MainWindow to supply us with a view of the list of {@code Person}s so that we can
     * update our pointer whenever a {@code Person} is updated.
     */
    public BrowserPanel(ObservableList<Person> persons) {
        super(FXML);
        this.persons = persons;
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));
        currentSelection = event.getNewSelection();
        refreshTableView(currentSelection);
    }

    /**
     * Current strategy is to refresh the medication panel every time a new result is available.
     * This might potentially burden the app for every new result available.
     * Might want to consider creating a new type of event for submitting medication and subscribing only to that.
     * TODO?
     */
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));

        // If we're not looking at anything, there's no need to update.
        if (currentSelection == null) {
            return;
        }

        currentSelection = getNewReferenceToPerson(currentSelection);
        refreshTableView(currentSelection);
    }

    /**
     * Refreshes the table view given a {@code Person}.
     */
    private void refreshTableView(Person person) {
        refreshTableView(person.getPrescriptionList());
    }

    /**
     * Refreshes the table view given a {@code PrescriptionList}.
     * Overloaded method for convenience.
     */
    private void refreshTableView(PrescriptionList prescriptionList) {
        setDataSourceForTable(prescriptionList.getObservableCopyOfPrescriptionList());
        setDataSourcesForTableColumns();
    }

    /**
     * The AddressBook {@code Person} object is immutable, hence any changes
     * results in a wholly new {@code Person} object being created. This method
     * helps to refresh our reference to the currently selected {@code Person} object
     * to point to the new {@code Person} object.
     */
    private Person getNewReferenceToPerson(Person p) {
        return persons.filtered(person -> currentSelection.isSamePerson(person)).get(0);
    }
    /**
     * Sets the data source for the {@code TableView}.
     * @param source to use for the table.
     */
    private void setDataSourceForTable(ObservableList<Prescription> source) {
        prescriptionTableView.setItems(source);
    }

    /**
     * Helper method to set all the data sources for each column.
     */
    private void setDataSourcesForTableColumns() {
        drugNameCol.setCellValueFactory(param -> getDrugNameAsSimpleStringProperty(param));
        dosageCol.setCellValueFactory(param -> getDosageAsSimpleStringProperty(param));
        dosageUnitCol.setCellValueFactory(param -> getDosageUnitAsSimpleStringProperty(param));
        dosesPerDayCol.setCellValueFactory(param -> getDosesPerDayAsSimpleStringProperty(param));
        startDateCol.setCellValueFactory(param -> getStartDateAsSimpleStringProperty(param));
        endDateCol.setCellValueFactory(param -> getEndDateAsSimpleStringProperty(param));
        durationCol.setCellValueFactory(param -> getDurationAsSimpleStringProperty(param));
        activePrescriptionCol.setCellValueFactory(param -> getActiveStatusAsSimpleStringProperty(param));
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
     * Helper method to help extract the drug name in the required format for
     * the cell value factory for the Drug Name column.
     */
    private SimpleStringProperty getDrugNameAsSimpleStringProperty(CellDataFeatures<Prescription, String> param) {
        return new SimpleStringProperty(param.getValue().getDrugName().toString());
    }

    /**
     * Helper method to help extract the dosage in the required format for
     * the cell value factory for the Drug Name column.
     */
    private SimpleStringProperty getDosageAsSimpleStringProperty(CellDataFeatures<Prescription, String> param) {
        return new SimpleStringProperty(Double.toString(param.getValue().getDose().getDose()));
    }

    /**
     * Helper method to help extract the dosage unit in the required format for
     * the cell value factory for the Drug Name column.
     */
    private SimpleStringProperty getDosageUnitAsSimpleStringProperty(CellDataFeatures<Prescription, String> param) {
        return new SimpleStringProperty(param.getValue().getDose().getDoseUnit());
    }

    /**
     * Helper method to help extract the doses per day in the required format for
     * the cell value factory for the Drug Name column.
     */
    private SimpleStringProperty getDosesPerDayAsSimpleStringProperty(CellDataFeatures<Prescription, String> param) {
        return new SimpleStringProperty(Integer.toString(param.getValue().getDose().getDosesPerDay()));
    }

    /**
     * Helper method to help extract the start date in the required format for
     * the cell value factory for the Drug Name column.
     */
    private SimpleStringProperty getStartDateAsSimpleStringProperty(CellDataFeatures<Prescription, String> param) {
        return new SimpleStringProperty(param.getValue().getDuration().getStartDateAsString());
    }

    /**
     * Helper method to help extract the end date in the required format for
     * the cell value factory for the Drug Name column.
     */
    private SimpleStringProperty getEndDateAsSimpleStringProperty(CellDataFeatures<Prescription, String> param) {
        return new SimpleStringProperty(param.getValue().getDuration().getEndDateAsString());
    }

    /**
     * Helper method to help extract the duration in months/weeks/days in the required format for
     * the cell value factory for the Drug Name column.
     */
    private SimpleStringProperty getDurationAsSimpleStringProperty(CellDataFeatures<Prescription, String> param) {
        return new SimpleStringProperty(param.getValue().getDuration().getDurationAsString());
    }

    /**
     * Helper method to help extract the active status of the prescription in the required format for
     * the cell value factory for the Drug Name column.
     */
    private SimpleStringProperty getActiveStatusAsSimpleStringProperty(CellDataFeatures<Prescription, String> param) {
        Calendar today = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        try {
            endDate.setTime(Duration.DATE_FORMAT.parse(param.getValue().getDuration().getEndDateAsString()));
        } catch (ParseException e) {
            logger.log(Level.SEVERE, loggingPrefix + "An exception has occurred: ", e);
            return new SimpleStringProperty("An error has occurred.");
        }

        return new SimpleStringProperty((isAfter(today, endDate) ? "No" : "Yes"));
    }

    /**
     * Helper method to check if a given {@code Calendar} is after another given {@code Calendar}.
     * @return true iff {@code firstDate} is strictly after {@code secondDate}..
     */
    private boolean isAfter(Calendar firstDate, Calendar secondDate) {
        if (firstDate.get(Calendar.YEAR) < secondDate.get(Calendar.YEAR)) {
            return false;
        } else if (firstDate.get(Calendar.YEAR) > secondDate.get(Calendar.YEAR)) {
            return true;
        }

        if (firstDate.get(Calendar.DAY_OF_YEAR) <= secondDate.get(Calendar.DAY_OF_YEAR)) {
            return false;
        }

        return true;
    }
}
