package application.server;

import application.client.Client;
import application.client.ClientThread;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author zozzy on 09.12.19
 */
public class Server {

    final private int PORT = 4242;
    final private List<ServerThread> clientList = new ArrayList<ServerThread>();

    final private Logger LOG = Logger.getLogger(Server.class.getName());

    private File users;

    /**
     * Starts the server.
     *
     * @param args
     */
    public static void main(String[] args) {

        Server chatServer = new Server();
        chatServer.start();
    }

    /**
     * This method will open a ServerSocket and then go into an infinite loop
     * while it's waiting for clients to connect.
     * <p>
     * Whenever a client connects, it gives it a new thread and stores a
     * reference to it in the <code>clientList</code> list.
     */
    public void start() {
        try {
            //TODO Registration Logic
            ServerSocket serverSocket = new ServerSocket(PORT);
            LOG.info("Server has started and is listening for clients.");

            // Go into an infinite loop and listen for connection requests
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOG.info("A client has connected.");

                // Get streams
                BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(
                        clientSocket.getOutputStream(), "UTF-8"));
                outStream.flush(); // Flush directly after creating to avoid
                // deadlock
                BufferedReader inStream = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream(), "UTF-8"));
                LOG.info("Streams to client established.");

                // Create a new thread and store it
                ServerThread serverThread = new ServerThread(this, outStream, inStream);
                clientList.add(serverThread);

                Thread thread = new Thread(serverThread);
                thread.start();
                LOG.info("A new client thread started.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a client from the <code>clientList</code>. Usually when the
     * client disconnects from the server.
     *
     * @param client
     *            Client to be removed.
     */
    protected void removeClient(ServerThread serverThread) {
        clientList.remove(clientList.indexOf(serverThread));
    }

    /**
     * Relays a message from a client to all other connected clients.
     *
     * @param message
     *            Message to relay.
     * @param sender
     *            The client that sent the message.
     */
    protected void sendToClients(String message, ServerThread sender) {

        LOG.info("Sending message: " + message);

        for (ServerThread serverThread : clientList) {


            // Don't send messages to yourself
            if (serverThread == sender)
                continue;

            try {
                // The trailing line-break is important for the
                // BufferedReader to work correctly
                serverThread.getWriter().write(message + "\n");
                serverThread.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
