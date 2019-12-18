package View;

import application.ClientDriver;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * @author zozzy on 13.12.19
 */



public class RegisterView extends BaseView {


    private AnchorPane anchorPane = null;
    private VBox vbox;
    private TextField userNameInput;
    private TextField passInput;
    private Button registerBtn;


    private ClientDriver mainApp = BaseView.getMainApp();


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


        registerBtn = new Button("Register");
        registerBtn.setPrefWidth(160.0);
        registerBtn.setOnAction(onBtnEvent);
        vbox.getChildren().add(registerBtn);

        AnchorPane.setTopAnchor(vbox, 25.0);
        AnchorPane.setLeftAnchor(vbox, 115.0);
        AnchorPane.setRightAnchor(vbox, 100.0);

        anchorPane.getChildren().add(vbox);
    }
    //trying Lambda
    private EventHandler<ActionEvent> onBtnEvent = actionEvent -> {
        if (actionEvent.getSource() == registerBtn) {
            User user = new User();
            user.setUsername(userNameInput.getText());
            user.setPassword(passInput.getText());

            mainApp.setUser(user);

            //mainApp.StartRegister();
           /* try {
                mainApp.getClient().sendUserInfo(user.getUsername() + user.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            try {
                mainApp.StartRegister();
                //mainApp.getClient().sendUserInfo(user);
               //
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainApp.setView(mainApp.getChatView());

            System.out.println("Register User");
        }
    };

}
