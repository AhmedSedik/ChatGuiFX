package application;

import View.BaseView;
import View.ChatView;
import View.LoginView;
import application.client.Client;
import application.model.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author zozzy on 09.12.19
 */
public class ClientDriver extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private User user;
     private Client client;

     private LoginView loginView;
     private ChatView chatView;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage)  {

        this.primaryStage = stage;
        this.primaryStage.setTitle("Chat Client");
        this.primaryStage.setResizable(false);
        this.primaryStage.setOnCloseRequest(e -> System.exit(0));


        client = new Client(this);
        BaseView.setMainApp(this);// Set a reference back to main

        loginView = new LoginView();
        chatView = new ChatView();

        this.initRoot();

    }
    /**
     * Creates the root layout and sets it on the stage.
     *
     * Also sets the loginView so the user can login and connect to the server.
     */
    private void initRoot() {

        rootLayout = new BorderPane();
        Scene scene = new Scene(rootLayout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


        this.setView(getLoginView());
    }

    /**
     * Appends text to the chat.
     *
     * @param message
     *            The message to append.
     */
    public void appendToChat(String message) {
        //TODO
        //chatView.appendTextToConversation(message);
    }


    public void startChatClient() throws IOException {
        getClient().start();
    }


    /**
     * Sets a new view in the rootLayout.
     *
     * @param newView
     *            The view to set in the rootLayout.
     */
    public void setView(BaseView newView) {
        rootLayout.setCenter(newView.getView());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public ChatView getChatView() {
        return chatView;
    }

    /**
     * Returns a reference to the config view.
     *
     * @return reference to the config view
     */
    public LoginView getLoginView() {
        return loginView;
    }

    public Client getClient () {
        return client;
    }

}
