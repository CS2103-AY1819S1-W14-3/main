package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

//@@author snajef
/**
 * A blank panel. The default view to show when the app starts up.
 */
public class BlankPanel extends UiPart<Region> implements Swappable {
    private static final String FXML = "BlankPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final String loggingPrefix = "[" + getClass().getName() + "]: ";

    public BlankPanel() {
        super(FXML);
    }

    @Override
    public void refreshView() {
    }
}
