package seedu.address.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;
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
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Person;

//@@author snajef
/**
 * The Medication Panel (maybe more in the future?) of the App.
 */
public class MedicationView extends UiPart<Region> implements Swappable, Sortable {
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
    private SortType sortType = SortType.ASCENDING;
    private ObservableList<TableColumn<Prescription, ?>> sortOrder;

    /**
     * C'tor for the Medication View.
     * We need the MainWindow to supply us with a view of the list of {@code Person}s so that we can
     * update our pointer whenever a {@code Person} is updated.
     */
    public MedicationView(ObservableList<Person> persons) {
        super(FXML);
        this.persons = persons;
        this.sortOrder = FXCollections.observableArrayList(new ArrayList<>());
        registerAsAnEventHandler(this);
    }

    /**
     * Sorts the table view according to the defined sorting order and type of the table view,
     * defined in sortOrder and sortType.
     */
    private void sortTableView() {
        for (TableColumn<?, ?> tc : sortOrder) {
            tc.setSortType(sortType);
        }

        setCustomComparatorsForColumns();

        prescriptionTableView.getSortOrder().setAll(sortOrder);
        prescriptionTableView.sort();
    }

    /**
     * Sets the custom comparators we use.
     */
    private void setCustomComparatorsForColumns() {
        /**
         *  Yes, this is in a sense implicit coupling between the
         *  Duration class and this Ui component. No, I don't have
         *  a better way to do this.
         */
        Function<String[], Integer> durationAsInteger = arr -> {
            int result = 0;
            for (String item : arr) {
                String[] items = item.split(" ");
                switch (items[1]) {
                case "year(s)":
                    result += Integer.parseInt(items[0]) * 365;
                    break;
                case "month(s)":
                    result += Integer.parseInt(items[0]) * 30;
                    break;
                case "week(s)":
                    result += Integer.parseInt(items[0]) * 7;
                    break;
                case "day(s)":
                    result += Integer.parseInt(items[0]);
                    break;
                default:
                    break;
                }
            }

            return result;
        };

        // We need to set a few custom comparators because
        // JavaFX stores our data entries as Strings, so
        // it sorts them by lexicographical order. This is not
        // desired behaviour when working with numbers and the like,
        // so we need a few custom comparators to fix that.
        dosageCol.comparatorProperty().set((d1, d2) -> {
            double doseOne = Double.parseDouble(d1);
            double doseTwo = Double.parseDouble(d2);

            return Double.compare(doseOne, doseTwo);
        });

        dosesPerDayCol.comparatorProperty().set((d1, d2) -> {
            int dosesPerDayOne = Integer.parseInt(d1);
            int dosesPerDayTwo = Integer.parseInt(d2);

            return Double.compare(dosesPerDayOne, dosesPerDayTwo);
        });

        durationCol.comparatorProperty().set((d1, d2) -> {
            // More implicit coupling.
            String[] d1arr = d1.split(", ");
            String[] d2arr = d2.split(", ");

            return Integer.compare(durationAsInteger.apply(d1arr), durationAsInteger.apply(d2arr));
        });
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
        // TODO: Set option to choose ascending or descending sort?
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
        LocalDate today = LocalDate.now();
        LocalDate endDate = LocalDate.parse(param.getValue()
            .getDuration()
            .getEndDateAsString(),
            Duration.DATE_FORMAT);

        boolean isEndDateStrictlyBeforeToday = today.compareTo(endDate) > 0;

        return new SimpleStringProperty(isEndDateStrictlyBeforeToday ? "No" : "Yes");
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

        ObservableList<TableColumn<Prescription, ?>> columns = prescriptionTableView.getColumns();

        for (int i = 0; i < colIdx.length; i++) {
            // Recall that the column indices passed in are 1-indexed.
            if (colIdx[i] < 1 || colIdx[i] > columns.size()) {
                // No corresponding column for that column index exists
                continue;
            }

            TableColumn<Prescription, ?> col = columns.get(colIdx[i] - 1);
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

        currentSelection = getNewReferenceToPerson();
        refreshView();
        sortTableView();
    }
}
