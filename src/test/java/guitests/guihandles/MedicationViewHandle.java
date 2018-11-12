package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import seedu.address.model.medicine.Prescription;

/**
 * Provides a handle for {@code MedicationView}.
 */
public class MedicationViewHandle extends NodeHandle<TableView<Prescription>> {
    public static final String PRESCRIPTION_TABLE_VIEW_ID = "#prescriptionTableView";

    public MedicationViewHandle(TableView<Prescription> browserPanelHandleNode) {
        super(browserPanelHandleNode);
    }

    public ObservableList<Prescription> getBackingListOfPrescriptions() {
        return getRootNode().getItems();
    }
}
