package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyHealthBase;

/** Indicates the HealthBase in the model has changed*/
public class HealthBaseChangedEvent extends BaseEvent {

    public final ReadOnlyHealthBase data;

    public HealthBaseChangedEvent(ReadOnlyHealthBase data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
