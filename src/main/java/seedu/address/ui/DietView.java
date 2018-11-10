package seedu.address.ui;

//@@author yuntongzhang

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.person.Person;

/**
 * The Diet Panel of the App.
 */
public class DietView extends UiPart<Region> implements Swappable {
    private static final String FXML = "DietPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final String loggingPrefix = "[" + getClass().getName() + "]: ";

    @javafx.fxml.FXML
    private TableView<Diet> allergyTableView;

    @javafx.fxml.FXML
    private TableColumn<Diet, String> allergyCol;

    @javafx.fxml.FXML
    private TableView<Diet> culturalRequirementTableView;

    @javafx.fxml.FXML
    private TableColumn<Diet, String> culturalRequirementCol;

    @javafx.fxml.FXML
    private TableView<Diet> physicalDifficultyTableView;

    @javafx.fxml.FXML
    private TableColumn<Diet, String> physicalDifficultyCol;

    private Person currentSelection;
    private ObservableList<Person> persons;

    /**
     * Constructor for DietView.
     */
    public DietView(ObservableList<Person> persons) {
        super(FXML);
        this.persons = persons;
        registerAsAnEventHandler(this);
    }

    /**
     * Refreshes all the three table views given a {@code Person}.
     */
    private void refreshAllTables(Person person) {
        DietCollection dietCollection = person.getDietCollection();

        setDataSourceForTable(dietCollection.getObservableAllergies(), allergyTableView);
        setDataSourceForTable(dietCollection.getObservableCulturalRequirements(), culturalRequirementTableView);
        setDataSourceForTable(dietCollection.getObservablePhysicalDifficulties(), physicalDifficultyTableView);

        setDataSourcesForTableColumns(allergyCol, culturalRequirementCol, physicalDifficultyCol);
    }

    private void setDataSourceForTable(ObservableList<Diet> source, TableView<Diet> table) {
        table.setItems(source);
    }

    private void setDataSourcesForTableColumns(TableColumn<Diet, String>... cols) {
        for (TableColumn<Diet, String> col : cols) {
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDetail()));
        }
    }

    private void refreshCurrentSelection() {
        currentSelection = persons.filtered(person -> currentSelection.isSamePerson(person)).get(0);
    }

    /**
     * Set the current selection to the given {@code Person}. Only used for testing purposes.
     * @param p
     */
    // TODO: replace this method with other means as it only helps testing.
    public void setCurrentSelection(Person p) {
        currentSelection = p;
    }

    @Override
    public void refreshView() {
        if (currentSelection == null) {
            return;
        }
        refreshAllTables(currentSelection);
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
     */
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));

        // If we're not looking at anything, there's no need to update.
        if (currentSelection == null) {
            return;
        }

        refreshCurrentSelection();
        refreshView();
    }
}
