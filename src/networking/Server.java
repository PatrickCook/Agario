package networking;

import agario.Log;
import agario.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * This server handles all connections with clients
 *
 * @author patrickcook
 */
public class Server {

    List<ObjectOutputStream> clientstreams;
    ArrayList<Player> clients;
    private int numClients = 0;
    private final int PORT = 4444;

    /**
     * Starts server and then calls the method to wait for clients to connect
     */
    public Server() {
        try {
            clientstreams = Collections.synchronizedList(new ArrayList<ObjectOutputStream>());
            clients = new ArrayList<Player>();
            Log.l("Starting server...");
            ServerSocket mySocket = new ServerSocket(PORT);
            waitForClients(mySocket);
        } catch (IOException e) {
            Log.l("Unable to start.");
            e.printStackTrace();
        }
    }

    /**
     * Waits for clients to connect. Upon connection an output stream is created
     * and added to an arraylist of outputstreams. Then a null player is added
     * the clients arraylist and finally
     *
     * @param mySocket
     */
    private void waitForClients(ServerSocket mySocket) {
        while (true) {
            try {
                Log.l("Ready to connect to clients");
                Socket client = mySocket.accept();
                numClients++;
                clientstreams.add(new ObjectOutputStream(client.getOutputStream()));
                //adds a player to client so that the array list isnt empty when setting an object later
                Log.l(client.getInetAddress().getHostAddress() + " connected to the Server");
                Thread t = new Thread(new ClientHandler(client, clientstreams.size() - 1));
                t.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void shareToAll(Object objectToShare) {
        synchronized (clientstreams) {
            for (ObjectOutputStream stream : clientstreams) {
                try {
                    stream.writeObject(objectToShare);
                    stream.reset();
                    stream.flush();

                } catch (IOException e) {
                    Log.l("Couldnt ");
                }
            }
        }
        Log.l("Server sent data.");
    }

    private class ClientHandler implements Runnable {

        Socket clientSocket;
        final int id;
        ObjectInputStream ois;

        public ClientHandler(Socket clientSocket, int id) {
            this.clientSocket = clientSocket;
            this.id = id;
            try {
                ois = new ObjectInputStream(clientSocket.getInputStream());
                clients.add((Player) ois.readObject());
            } catch (IOException ex) {
                Log.e("Failed to recieve client's player.");
            } catch (ClassNotFoundException ex) {
            }

        }

        @Override
        public void run() {
            while (true) {
                try {
                    Player p = (Player) ois.readObject();
                    if (p != null) {
                        clients.set(id, p);
                    } else {
                        Log.e("Client " + id + " is null.");
                    }
                    Log.l("Server recieved data.");
                    shareToAll(clients);
                } catch (ClassNotFoundException e) {
                    Log.e("ClassNotFoundException");
                    break;
                } catch (IOException e) {
                    Log.e("Client "+ id+ " has disconnected.");
                    disconnect();
                    break;
                } 
            }
        }
        public void disconnect(){
            try {
                clientSocket.close();
                clients.remove(clients.get(id));
                clientstreams.remove(clientSocket);
                numClients--;
                
            } catch (IOException ex) {}
        }

    }

    public static void main(String[] args) {
        Server gameServer = new Server();

    }
}
