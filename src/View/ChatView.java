package View;

import application.ClientDriver;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * @author zozzy on 11.12.19
 */
public class ChatView extends BaseView {
    private AnchorPane anchorPane = null;
    private TextArea conversationText;
    private TextArea inputArea;

    private ClientDriver mainApp = BaseView.getMainApp();

    /**
     * Returns this view to the caller.
     */
    public AnchorPane getView() {
        if (anchorPane == null)
            this.createView();
        return anchorPane;
    }

    /**
     * Event handler for the input-box.
     * <p>
     * This object is responsible for sending the message when the user presses
     * 'ENTER' in the input-box.
     */
    private EventHandler<KeyEvent> onKeyEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {

                String sendingUser = "<" + mainApp.getUser().getUsername() + ">";
                String message = inputArea.getText() + "\n";

                mainApp.getClient().sendMessage(sendingUser + " " + message);
                conversationText.appendText("<You> " + message);

                inputArea.clear(); // Clears the text in the TextArea
                event.consume(); // Removes the line-break created by ENTER
            }
        }
    };

    /**
     * Appends text to the chat.
     *
     * @param message
     *            Message to append.
     */
    public void appendTextToConversation(String message) {
        conversationText.appendText(message);
    }

    /**
     * Creates the view.
     */
    private void createView() {
        anchorPane = new AnchorPane();

        conversationText = new TextArea();
        conversationText.setFocusTraversable(false);
        conversationText.setEditable(false);
        conversationText.setMinHeight(230);
        conversationText.setWrapText(true);

        AnchorPane.setTopAnchor(conversationText, 10.0);
        AnchorPane.setRightAnchor(conversationText, 10.0);
        AnchorPane.setLeftAnchor(conversationText, 10.0);

        anchorPane.getChildren().add(conversationText);

        inputArea = new TextArea();
        inputArea.setMaxHeight(40);
        inputArea.setWrapText(true);

        AnchorPane.setBottomAnchor(inputArea, 10.0);
        AnchorPane.setLeftAnchor(inputArea, 10.0);
        AnchorPane.setRightAnchor(inputArea, 10.0);

        inputArea.addEventHandler(KeyEvent.KEY_PRESSED, onKeyEvent);
        anchorPane.getChildren().add(inputArea);
    }
}
