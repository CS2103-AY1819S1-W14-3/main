package seedu.address.ui;

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
import seedu.address.model.person.Person;
import seedu.address.model.visitor.Visitor;
import seedu.address.model.visitor.VisitorList;

/**
 * The Visitor Panel of the App.
 * @author GAO JIAXIN666
 */
public class VisitorView extends UiPart<Region> implements Swappable {
    private static final String FXML = "VisitorPanel.fxml";
    private static final String MESSAGE_CURRENT_SELECTION_NOT_NULL = "There was an attempt "
            + "to set the current selection, but it is not null.";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final String loggingPrefix = "[" + getClass().getName() + "]: ";

    @javafx.fxml.FXML
    private TableView<Visitor> visitorsTableView;

    @javafx.fxml.FXML
    private TableColumn<Visitor, String> visitorCol;

    private Person currentSelection;
    private ObservableList<Person> persons;

    public VisitorView(ObservableList<Person> persons) {
        super(FXML);
        this.persons = persons;
        registerAsAnEventHandler(this);
    }

    /**
     * Refreshes the table view given a {@code Person}.
     */
    private void refreshTableView(Person person) {
        refreshTableView(person.getVisitorList());
    }

    /**
     * Refreshes the table view given a {@code VisitorList}.
     * Overloaded method for convenience.
     */
    private void refreshTableView(VisitorList visitorList) {
        setDataSourceForTable(visitorList.getObservableCopyOfVisitorlist());
        setDataSourcesForTableColumn();
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
     * Sets the data source for the {@code TableView}.
     * @param source to use for the table.
     */
    private void setDataSourceForTable(ObservableList<Visitor> source) {
        visitorsTableView.setItems(source);
    }

    /**
     * Helper method to set all the data sources for the column.
     */
    private void setDataSourcesForTableColumn() {
        visitorCol.setCellValueFactory(param -> getVisitorAsSimpleStringProperty(param));
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
     * Helper method to extract the visitor name in the required format
     */
    private SimpleStringProperty getVisitorAsSimpleStringProperty(CellDataFeatures<Visitor, String> param) {
        return new SimpleStringProperty(param.getValue().getVisitorName());
    }

    @Override
    public void refreshView() {
        if (currentSelection == null) {
            return;
        }

        refreshTableView(currentSelection);
    }

    /* ====================== Event handling ====================== */

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));
        currentSelection = event.getNewSelection();
        refreshView();
    }


    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(loggingPrefix + LogsCenter.getEventHandlingLogMessage(event));

        // If we're not looking at anything, there's no need to update.
        if (currentSelection == null) {
            return;
        }

        currentSelection = getNewReferenceToPerson();
        refreshView();
    }
}
