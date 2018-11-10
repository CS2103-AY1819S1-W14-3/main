package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import seedu.address.model.appointment.Appointment;

/**
 * Provides an Appointment view handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */

public class AppointmentViewHandle extends NodeHandle<TableView<Appointment>> {
    public static final String APPOINTMENT_TABLE_VIEW_ID = "#appointmentTableView";

    public AppointmentViewHandle(TableView<Appointment> browserPanelHandleNode) {
        super(browserPanelHandleNode);
    }

    public ObservableList<Appointment> getBackingListOfAppointments() {
        return getRootNode().getItems();
    }
}
