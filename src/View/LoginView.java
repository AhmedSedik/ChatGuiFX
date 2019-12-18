package View;

import application.ClientDriver;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 *   Here the user will be able to enter a user name, pass, and the IP and port of the
 *   server he wants to connect to.
 *
 * @author zozzy on 11.12.19
 */
public class LoginView extends BaseView{


    private AnchorPane anchorPane = null;
    private VBox vbox;
    private TextField userNameInput;
    private TextField passInput;
    private TextField ipInput;
    private TextField portInput;
    private Button connectBtn;
    private Button registerBtn;

    private ClientDriver mainApp = BaseView.getMainApp();


    /**
     *
     * @return the view
     */
    @Override
    public AnchorPane getView() {
        if (anchorPane == null)
            this.createView();
        return anchorPane;
    }

    public void createView() {

        anchorPane = new AnchorPane();
        vbox = new VBox(10);


        Label label = new Label("Username");
        userNameInput = new TextField();
        //userNameInput.setText("User-" + new Random().nextInt(9000));
        userNameInput.setAlignment(Pos.CENTER);
        vbox.getChildren().add(label);
        vbox.getChildren().add(userNameInput);

        label = new Label("Password");
        passInput = new TextField();
        passInput.setAlignment(Pos.CENTER);
        vbox.getChildren().add(label);
        vbox.getChildren().add(passInput);

        label = new Label("Host");
        ipInput = new TextField();
        ipInput.setText("127.1.0.0");
        ipInput.setAlignment(Pos.CENTER);

        vbox.getChildren().add(label);
        vbox.getChildren().add(ipInput);

        label = new Label("Port");

        portInput = new TextField();
        portInput.setText("4242");
        portInput.setAlignment(Pos.CENTER);

        vbox.getChildren().add(label);
        vbox.getChildren().add(portInput);

        connectBtn = new Button("Connect");
        connectBtn.setPrefWidth(160.0);
        connectBtn.setOnAction(onBtnEvent);
        vbox.getChildren().add(connectBtn);

         label = new Label("Dont Have an Account? ");
        vbox.getChildren().add(label);

        registerBtn = new Button("Register");
        registerBtn.setPrefWidth(160.0);
        registerBtn.setOnAction(onBtnEvent);
        vbox.getChildren().add(registerBtn);


        AnchorPane.setTopAnchor(vbox, 25.0);
        AnchorPane.setLeftAnchor(vbox, 115.0);
        AnchorPane.setRightAnchor(vbox, 100.0);

        anchorPane.getChildren().add(vbox);
    }

    private javafx.event.EventHandler<javafx.event.ActionEvent> onBtnEvent = new EventHandler<>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            if (actionEvent.getSource() == connectBtn) {
                User user = new User();
                user.setUsername(userNameInput.getText());
                user.setPassword(passInput.getText());
                user.setHost(ipInput.getText());
                user.setPort(portInput.getText());

                mainApp.setUser(user);

                try {
                    mainApp.startChatClient();
                } catch (IOException e) {
                    //TODO get reply from server if not able to connect (login or register)
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Could not connect");
                    alert.setHeaderText("Could not connect to the server.");
                    alert.setContentText("Make sure you're using the correct IP and Port, or that\n"
                            + "the server you're trying to connect to is running.");

                    alert.showAndWait();
                    return;
                }
                //go to chat view if login or register successful

                mainApp.setView(mainApp.getChatView());
            } else if (actionEvent.getSource() == registerBtn) {
                //TODO go to register screen
                mainApp.setView(mainApp.getRegisterView());
            }
        }
    };


}
