package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Person;

//@@author snajef
/**
 * The Medication Panel (maybe more in the future?) of the App.
 */
public class BrowserPanel extends UiPart<Region> {
    private static final String FXML = "BrowserPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

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

    private Person currentSelection;
    private ObservableList<Person> persons;

    public BrowserPanel(ObservableList<Person> persons) {
        super(FXML);
        this.persons = persons;
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info("[seedu.address.ui.BrowserPanel]: " + LogsCenter.getEventHandlingLogMessage(event));
        currentSelection = event.getNewSelection();
        resetTableView(currentSelection);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info("[seedu.address.ui.BrowserPanel]: " + LogsCenter.getEventHandlingLogMessage(event));

        // If we're not looking at anything, there's no need to update.
        if (currentSelection == null) {
            return;
        }

        // Our current pointer is outdated, so we have to find our new Person object and update our table to point
        // to his medication list.
        currentSelection = persons.filtered(person -> currentSelection.isSamePerson(person)).get(0);
        resetTableView(currentSelection);
    }

    /** Resets the table view given a Person */
    private void resetTableView(Person person) {
        resetTable(person.getPrescriptionList());
    }

    /** Resets the table view given a PrescriptionList. */
    public void resetTable(PrescriptionList prescriptionList) {
        ObservableList<Prescription> pxs = prescriptionList.getReadOnlyList();
        prescriptionTableView.setItems(pxs);

        drugNameCol.setCellValueFactory(
                new Callback<CellDataFeatures<Prescription, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Prescription, String> param) {
                        return new SimpleStringProperty(param.getValue()
                                .getDrugName()
                                .toString());
                    }
                });

        dosageCol.setCellValueFactory(new Callback<CellDataFeatures<Prescription, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Prescription, String> param) {
                return new SimpleStringProperty(Double.toString(param.getValue()
                        .getDose()
                        .getDose()));
            }
        });

        dosageUnitCol.setCellValueFactory(
                new Callback<CellDataFeatures<Prescription, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Prescription, String> param) {
                        return new SimpleStringProperty(param.getValue()
                                .getDose()
                                .getDoseUnit());
                    }
                });

        dosesPerDayCol.setCellValueFactory(
                new Callback<CellDataFeatures<Prescription, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Prescription, String> param) {
                        return new SimpleStringProperty(Integer.toString(param.getValue()
                                .getDose()
                                .getDosesPerDay()));
                    }
                });

        startDateCol.setCellValueFactory(
                new Callback<CellDataFeatures<Prescription, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Prescription, String> param) {
                        return new SimpleStringProperty(param.getValue()
                                .getDuration()
                                .getStartDateAsString());
                    }
                });

        endDateCol.setCellValueFactory(new Callback<CellDataFeatures<Prescription, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Prescription, String> param) {
                return new SimpleStringProperty(param.getValue()
                        .getDuration()
                        .getEndDateAsString());
            }
        });

        durationCol.setCellValueFactory(
                new Callback<CellDataFeatures<Prescription, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Prescription, String> param) {
                        return new SimpleStringProperty(param.getValue()
                                .getDuration()
                                .getDurationAsString());
                    }
                });
    }

    /**
     * Setter method to fix the currently selected person.
     * Should only be used for testing purposes.
     * Will only work if the current selection is null.
     */
    public void setCurrentSelection(Person p) {
        currentSelection = (currentSelection == null ? p : currentSelection);
    }

}
