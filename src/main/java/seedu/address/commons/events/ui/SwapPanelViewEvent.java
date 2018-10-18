package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.SwappablePanelName;

/**
 * Indicates a request swap out the panel view
 */
public class SwapPanelViewEvent extends BaseEvent {

    public final SwappablePanelName panelName;

    public SwapPanelViewEvent(SwappablePanelName spn) {
        this.panelName = spn;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public SwappablePanelName getSwappablePanelName() {
        return panelName;
    }

}
