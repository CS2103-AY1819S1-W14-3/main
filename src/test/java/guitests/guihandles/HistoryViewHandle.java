package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import seedu.address.model.medicalhistory.Diagnosis;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */
public class HistoryViewHandle extends NodeHandle<TableView<Diagnosis>> {
    public static final String DIAGNOSIS_TABLE_VIEW_ID = "#diagnosisTableView";

    public HistoryViewHandle(TableView<Diagnosis> browserPanelHandleNode) {
        super(browserPanelHandleNode);
    }

    public ObservableList<Diagnosis> getBackingListOfDiagnoses() {
        return getRootNode().getItems();
    }
}
