package View;


import application.ClientDriver;
import javafx.scene.Parent;

/**
 * Generic class for all views.
 * <p>
 * <code>BaseView.setMainApp(this)</code> should be called from the main
 * application before any view that inherits from this class is instantiated.
 * Otherwise they won't have a reference back to the main application.
 * 
 * @author Sid
 */
public abstract class BaseView {

    private static ClientDriver mainApp = null;

    /**
     * Each class that inherits from BaseView is expected to implement their own
     * version of this function for returning the view associated with that
     * class.
     * 
     * @return returns a Parent object containing the view to load
     */
    public abstract Parent getView();

    /**
     * Returns a reference to the main application. If no reference exists yet
     * it will throw an exception.
     * 
     * @return reference to the main application
     */
    public static ClientDriver getMainApp() {
        if (mainApp == null) {
            try {
                throw new Exception("No reference to mainApp in BaseView.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mainApp;
    }

    /**
     * Sets a new reference to the main application.
     *
     * @param mainApp
     *            Reference to the main application.
     */
    public static void setMainApp(ClientDriver mainApp) {
        BaseView.mainApp = mainApp;
    }

}
