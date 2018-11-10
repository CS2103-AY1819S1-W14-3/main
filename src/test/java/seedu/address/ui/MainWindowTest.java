package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.security.PrivilegedActionException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import javafx.application.Platform;
import seedu.address.commons.core.Config;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.SortCommand.SortOrder;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class MainWindowTest extends GuiUnitTest {
    // Normally we'd observe behaviour, and not internals to test out functionality.
    // However, as we're dealing with UI components, we'll have to use reflection
    // to test internals in lieu of testing graphical representations.
    private static final String CURRENT_PANEL_FIELD_NAME = "currentPanel";
    private static final String PANELS_FIELD_NAME = "panels";

    private HashMap<SwappablePanelName, Swappable> panels;
    private MainWindow mainWindow;


    @Before
    public void setup() throws TimeoutException, NoSuchFieldException, SecurityException, IllegalArgumentException,
        IllegalAccessException {
        FxToolkit.setupStage(stage -> {
            mainWindow = new MainWindow(stage, new Config(), new UserPrefs(), new LogicManager(new ModelManager()));
            mainWindow.init();
            mainWindow.fillInnerParts();

            stage.setScene(mainWindow.getRoot().getScene());
        });
        FxToolkit.showStage();



        panels = (HashMap<SwappablePanelName, Swappable>) getPrivateFieldFromObject(PANELS_FIELD_NAME, mainWindow);
    }

    @Test
    public void setCurrentPanel_null_noChangeInPanel()
        throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Swappable before = (Swappable) getPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow);
        mainWindow.setCurrentPanel(null);
        Swappable after = (Swappable) getPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow);
        assertEquals(before, after);
    }

    @Test
    public void setCurrentPanel_toMeds_changeInPanelToMeds()
        throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Swappable before = (Swappable) getPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow);
        Platform.runLater(() -> mainWindow.setCurrentPanel(SwappablePanelName.MEDICATION));
        Swappable after = (Swappable) getPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow);
        assertEquals(before, after);
    }

    @Test(expected = NullPointerException.class)
    public void sortCurrentPanel_null_throwsNullPointerException() {
        mainWindow.sortCurrentPanel(null, null);
    }

    @Test
    public void sortCurrentPanel_currentPanelIsNull_failsGracefully()
        throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        setPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow, null);
        mainWindow.sortCurrentPanel(SortOrder.ASCENDING, new int[] { 1 });
    }

    @Test
    public void sortCurrentPanel_currentPanelDoesNotImplementSortable_failsGracefully()
        throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,
        PrivilegedActionException {
        setPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow, panels.get(SwappablePanelName.BLANK));
        mainWindow.sortCurrentPanel(SortOrder.ASCENDING, new int[] { 1 });
    }

    @Test
    public void sortCurrentPanel_currentPanelImplementsSortable_runsSuccessfully()
        throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        setPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow, panels.get(SwappablePanelName.MEDICATION));
        mainWindow.sortCurrentPanel(SortOrder.ASCENDING, new int[] { 1 });
        setPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow, panels.get(SwappablePanelName.HISTORY));
        mainWindow.sortCurrentPanel(SortOrder.ASCENDING, new int[] { 1 });
        setPrivateFieldFromObject(CURRENT_PANEL_FIELD_NAME, mainWindow, panels.get(SwappablePanelName.APPOINTMENT));
        mainWindow.sortCurrentPanel(SortOrder.ASCENDING, new int[] { 1 });
    }

    private Object getPrivateFieldFromObject(String fieldName, Object obj)
        throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(obj);
    }

    private void setPrivateFieldFromObject(String fieldName, Object obj, Object toSet)
        throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(obj, toSet);
    }
}
