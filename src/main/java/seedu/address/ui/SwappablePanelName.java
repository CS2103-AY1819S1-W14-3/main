package seedu.address.ui;

//@@author snajef
/**
 * Enumeration of the names for the swappable panels.
 * @author Darien Chong
 *
 */
public enum SwappablePanelName {
    BLANK("default"),
    MEDICATION("meds"),
    HISTORY("mh"),
    DIET("diets"),
    APPOINTMENT("appt");

    private String shortForm;

    SwappablePanelName(String shortForm) {
        this.shortForm = shortForm;
    }

    public String getShortForm() {
        return shortForm;
    }

    /**
     * Returns a SwappablePanelName with the corresponding short form name, or
     * {@code null} if no such SwappablePanelName exists.
     */
    public static SwappablePanelName fromShortForm(String shortForm) {
        for (SwappablePanelName spn : SwappablePanelName.values()) {
            if (spn.getShortForm().equals(shortForm)) {
                return spn;
            }
        }

        return null;
    }
}
