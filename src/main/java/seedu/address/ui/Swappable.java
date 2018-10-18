package seedu.address.ui;

//@@author snajef
/**
 * Interface used for swapping out panel views in the UI.
 * Every class that implements the Swappable interface should
 * also extend the UiPart class (parameterised with the Region class) to allow for this functionality to work.
 *
 * A possible better implementation would involve the UiPart class as an interface,
 * and the Swappable interface extending it.
 * TODO?
 *
 * When creating a Swappable implementation, the following should be done:
 * 1. Your UI part should implement the Swappable interface
 * 2. Your UI part should extend the UiPart class, parameterised with the Region class.
 * 3. Add a new entry in SwappablePanelName for the name of your panel, along with an appropriate short form.
 * 4. Add a new entry in the HashMap<> of entries, under the MainWindow::init() method.
 * @author Darien Chong
 *
 */
public interface Swappable {

    /**
     * Method to refresh the panel view.
     * Called once upon swapping to the panel view.
     */
    public void refreshView();
}
