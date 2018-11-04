package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SwappablePanelNameTest {
    private static final String BLANK_SHORT_FORM = "default";
    private static final String MEDICATION_SHORT_FORM = "meds";
    private static final String DIET_SHORT_FORM = "diets";

    @Test
    public void getShortForm() {
        assertEquals(SwappablePanelName.BLANK.getShortForm(), BLANK_SHORT_FORM);
        assertEquals(SwappablePanelName.MEDICATION.getShortForm(), MEDICATION_SHORT_FORM);
        assertEquals(SwappablePanelName.DIET.getShortForm(), DIET_SHORT_FORM);
    }

    @Test
    public void fromShortForm_shortForm_returnsSwappablePanelName() {
        assertEquals(SwappablePanelName.BLANK,
            SwappablePanelName.fromShortForm(SwappablePanelName.BLANK.getShortForm()));
        assertEquals(SwappablePanelName.MEDICATION,
            SwappablePanelName.fromShortForm(SwappablePanelName.MEDICATION.getShortForm()));
        assertEquals(SwappablePanelName.DIET,
            SwappablePanelName.fromShortForm(SwappablePanelName.DIET.getShortForm()));
    }

    @Test
    public void fromShortForm_notSwappablePanelNameShortForms_returnsNull() {
        assertTrue(SwappablePanelName.fromShortForm("") == null);
    }
}
