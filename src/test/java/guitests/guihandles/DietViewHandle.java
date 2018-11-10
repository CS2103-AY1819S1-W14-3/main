package guitests.guihandles;

//@@author yuntongzhang

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import seedu.address.model.diet.Diet;

/**
 * Provide a handle for {@code DietCollection} table.
 * One table can only contain one single type of diet.
 */
public class DietViewHandle extends NodeHandle<TableView<Diet>> {
    public static final String ALLERGY_TABLE_VIEW_ID = "#allergyTableView";
    public static final String CULTURAL_TABLE_VIEW_ID = "#culturalRequirementTableView";
    public static final String PHYSICAL_TABLE_VIEW_ID = "#physicalDifficultyTableView";

    public DietViewHandle(TableView<Diet> dietPanelHandleNode) {
        super(dietPanelHandleNode);
    }

    public ObservableList<Diet> getBackingListOfDiet() {
        return getRootNode().getItems();
    }
}
