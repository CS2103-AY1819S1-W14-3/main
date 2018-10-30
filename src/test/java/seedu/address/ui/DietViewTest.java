package seedu.address.ui;

//@@author yuntongzhang

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.DietViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.diet.Diet;
import seedu.address.model.person.Person;
import seedu.address.testutil.DietBuilder;

/**
 * Test driver class for DietView class.
 * @author yuntongzhang
 */
public class DietViewTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS = FXCollections.observableList(getTypicalPersons());

    private NewResultAvailableEvent adddietCommandSuccessEvent;
    private DietView dietView;
    private DietViewHandle allergyViewHandle;
    private DietViewHandle culturalRequirementViewHandle;
    private DietViewHandle physicalDifficultyViewHandle;
    private Diet allergy;

    @Before
    public void setUp() {
        adddietCommandSuccessEvent = new NewResultAvailableEvent("Dietary requirements added for patient: "
                                                                 + BENSON.getNric());
        allergy = new DietBuilder().build();
        initUi();
    }

    private void initUi() {
        dietView = new DietView(TYPICAL_PERSONS);
        uiPartRule.setUiPart(dietView);
        dietView.setCurrentSelection(BENSON);
        allergyViewHandle = new DietViewHandle(getChildNode(dietView.getRoot(), DietViewHandle.ALLERGY_TABLE_VIEW_ID));
        culturalRequirementViewHandle = new DietViewHandle(getChildNode(dietView.getRoot(),
            DietViewHandle.CULTURAL_TABLE_VIEW_ID));
        physicalDifficultyViewHandle = new DietViewHandle(getChildNode(dietView.getRoot(),
            DietViewHandle.PHYSICAL_TABLE_VIEW_ID));
    }

    // TODO: add similar test for other two types of diets
    @Test
    public void addAllergy_displayUpdates() {
        ObservableList<Diet> expected = FXCollections.observableArrayList();
        expected.addAll(BENSON.getDietCollection().getObservableAllergies());
        expected.add(allergy);

        BENSON.getDietCollection().add(allergy);
        postNow(adddietCommandSuccessEvent);

        assertEquals(allergyViewHandle.getBackingListOfDiet(), expected);
    }
}
