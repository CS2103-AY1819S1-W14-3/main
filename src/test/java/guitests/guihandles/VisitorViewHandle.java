package guitests.guihandles;

//@@author GAO JIAXIN666

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import seedu.address.model.visitor.Visitor;

/**
 * Provides visitor view handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */
public class VisitorViewHandle extends NodeHandle<TableView<Visitor>> {
    public static final String VISITOR_TABLE_VIEW_ID = "#visitorsTableView";

    public VisitorViewHandle(TableView<Visitor> browserPanelHandleNode) {
        super(browserPanelHandleNode);
    }

    public ObservableList<Visitor> getBackingListOfVisitor() {
        return getRootNode().getItems();
    }
}
