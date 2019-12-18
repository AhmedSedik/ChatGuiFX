package application.client;

import View.BaseView;
import View.LoginView;
import application.ClientDriver;
import application.server.Server;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * @author zozzy on 11.12.19
 */
public class ClientThread implements Runnable {
    private Client client;
    private BufferedWriter outStream;
    private BufferedReader inStream;

private ClientDriver mainApp = BaseView.getMainApp();

    final private Logger LOG = Logger.getLogger(Client.class.getName());

    public ClientThread(Client client, BufferedWriter outStream, BufferedReader inStream) {
        this.client = client;
        this.outStream = outStream;
        this.inStream = inStream;
    }

    /**
     * This method will put itself in an endless loop and listen for input from
     * the server.
     * <p>
     * When it receives input, it will append it to the TextArea in the ChatView
     * class.
     */
    @Override
    public void run() {
        try {


            while (true) {
               /* String username = mainApp.getUser().getUsername();
                String pass = mainApp.getUser().getPassword();
                outStream.write(username);
                outStream.write(pass);*/

                String message = inStream.readLine();
                client.getMainApp().appendToChat(message + "\n");
            }
        } catch (SocketException e) {
            // If we end up in here, the client has lost the connection
            // And we need to close everything gracefully

            // We need to call the JavaFX thread like this since we can only
            // perform operations on that thread from within the thread itself.
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Connection Lost");
                    alert.setHeaderText("Lost the connection to the server.");
                    alert.setContentText("The connection to the server was lost.");
                    alert.showAndWait();

                    // Return to the Login view
                    client.getMainApp().setView(new LoginView());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns a reference to the BufferedWriter that belongs to this thread.
     *
     * @return a reference to the BufferedWriter associated with this thread
     */
    public BufferedWriter getWriter() {
        return this.outStream;
    }

    public BufferedWriter getUserData() {
        return this.outStream;
    }
}