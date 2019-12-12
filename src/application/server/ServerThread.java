package application.server;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * @author zozzy on 11.12.19
 */
public class ServerThread implements Runnable{
    private BufferedWriter outStream;
    private BufferedReader inStream;
    private Server chatServer;

    final private Logger LOG = Logger.getLogger(ServerThread.class.getName());

    /**
     * Constructor.
     *
     * @param chatServer
     *            Reference back to the ChatServer that owns this thread.
     * @param outStream
     *            Reference to the output stream for this client.
     * @param inStream
     *            Reference to the input stream for this client.
     */
    public ServerThread(Server chatServer, BufferedWriter outStream,
                        BufferedReader inStream) {
        this.chatServer = chatServer;
        this.outStream = outStream;
        this.inStream = inStream;
    }

    /**
     * This method will put itself in an endless loop and listen for input from
     * its client.
     * <p>
     * When input is received it relays it back to the ChatServer who in turn
     * sends it out to all other connected clients.
     */
    @Override
    public void run() {

        try {
            //TODO login logic

            // Stay in this infinite loop and relay messages from this
            // client to other clients
            while (true)

                chatServer.sendToClients(inStream.readLine(), this);


        } catch (SocketException e) {

            // If we end up in here, the client has lost the connection
            // And we need to close everything gracefully
            chatServer.removeClient(this);
            LOG.info("Client was lost.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Returns a reference to the BufferedWriter that belongs to this client.
     *
     * @return a reference to the BufferedWriter associated with this client
     */
    public BufferedWriter getWriter() {
        return this.outStream;
    }


   /* public void registerUser() throws IOException {
        users = new File("users.csv");
        try (
                // create CSVWriter object filewriter object as parameter
                CSVWriter writer = new CSVWriter(new FileWriter(users.getAbsoluteFile(), true));

        ) {
            String readUsername;
            String readPassword;
            String userChoice = in.readLine();
            if (userChoice.equals("/register")) {
                boolean userExists = false;
                while (!userExists) {

                    while (((readUsername = in.readLine()) != null) &&
                            ((readPassword = in.readLine()) != null)) {

                        String[] nextRecord;
                        userExists = false;
                        CSVReader reader = new CSVReader(new FileReader(users));
                        while ((((nextRecord = reader.readNext())) != null) && userExists == false) {
                            if (nextRecord[0].equals(readUsername)) {
                                System.out.println("a client entered an already taken username");
                                out.println("false");
                                out.println("Username Already Taken. \n Please enter Username and Password");
                                userExists = true;
                            }
                        }
                        if (userExists == false) {

                            String[] data = {readUsername, readPassword};
                            System.out.println(socket +"Registered New User");
                            out.println("true");
                            out.println("-----REGISTRATION SUCCESSFUL----");
                            username = readUsername;
                            writer.writeNext(data);
                            userExists = true;
                            break;
                        }
                    }
                }
            } else {
                boolean loginCheck = false;
                while (((readUsername = in.readLine()) != null) &&
                        ((readPassword = in.readLine()) != null)) {
                    //System.gc();
                    String[] nextRecord;
                    CSVReader reader = new CSVReader(new FileReader(users));

                    while ((((nextRecord = reader.readNext())) != null) && loginCheck == false) {
                        if (nextRecord[0].equals(readUsername)) {
                            if (nextRecord[1].equals(readPassword))
                                loginCheck = true;
                        }
                    }
                    if (loginCheck == true) {
                        out.println("true");
                        out.println(readUsername + " Login Accepted!");
                        username = readUsername;
                        System.out.println("Client: " + socket + " logged in with username " + readUsername);
                        break;
                    } else
                        out.println("Login failed. Please try again.");
                }
            }

        } catch (
                CsvValidationException e) {
            e.printStackTrace();
        }*//*finally {
                try {

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }*//*

    }*/
}
