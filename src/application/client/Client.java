package application.client;

import application.ClientDriver;
import application.server.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

/** This class is responsible for creating a connection to the chat server and
 *  exchanging messages with it.
 *
 *  @author zozzy on 08.12.19
 */
public class Client {

    ClientDriver mainApp;
    private Socket clientSocket;
    private ClientThread clientThread;

    public Client(ClientDriver mainApp) {
        this.mainApp = mainApp;
    }

    public void start() throws IOException {
        String host = mainApp.getUser().getHost();
        int port = Integer.valueOf(mainApp.getUser().getPort());
        clientSocket = new Socket(host, port);

        // Get streams
        BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(
                clientSocket.getOutputStream(), "UTF-8"));
        outStream.flush(); // Flush directly after creating
        BufferedReader inStream = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream(), "UTF-8"));

        // Create a new thread
        clientThread = new ClientThread(this, outStream, inStream);

        Thread thread = new Thread(clientThread);
        thread.start();
    }

    /**
     * Sends a message to the server.
     *
     * @param message
     *            Message to send.
     */
    public void sendMessage(String message) {
        try {
            clientThread.getWriter().write(message);
            clientThread.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns a reference to the main application.
     *
     * @return reference to the main application
     */
    protected ClientDriver getMainApp() {
        return mainApp;
    }



    /**
     * This inner class is responsible for receiving input from the server and displaying
     * it in the ChatView.
     *
     * It will also notify the user if the connection to the server is lost and
     * change back to the ConfigView.
     */
   /* public static class ClientThread implements Runnable {
        private Client client;
        private BufferedWriter outStream;
        private BufferedReader inStream;

        private Server server;

        final private Logger LOG = Logger.getLogger(Client.class.getName());

        public ClientThread(Client client, BufferedWriter outStream, BufferedReader inStream, Server server) {
            this.client = client;
            this.outStream = outStream;
            this.inStream = inStream;
            this.server = server;
        }

        @Override
        public void run() {
            try {
                while (true) {

                    server.sendToClients(inStream.read(), this);
                }
            } catch (SocketException e) {
                // If we end up in here, the client has lost the connection
                // And we need to close everything gracefully
                server.removeClient(this);
                LOG.info("Client was lost.");
            }


        }
    }*/

}

