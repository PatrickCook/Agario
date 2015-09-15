package networking;

import agario.Player;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Client  {
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public Client(int port) throws UnknownHostException, IOException{
        /*
        String serverAddress = JOptionPane.showInputDialog(
            null,
            "Enter IP Address of the Server:",
            "Welcome to the Agar.io",
            JOptionPane.QUESTION_MESSAGE);
                */
        //this.ID = id;
        connect("localhost", port);
    }
    
    public void send(Object message) throws IOException {
        if(out == null) throw new IOException();
        out.writeObject(message);
        out.reset();
        out.flush();
    }
    
    public Object receive() throws IOException{
        try {
            ArrayList<Player> p = (ArrayList<Player>) in.readObject();
            System.out.println(p);
            return p;
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }
    }

    public void connect(String ip, int port) throws UnknownHostException, IOException {
        Socket socket = new Socket(ip, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        InputStream is = socket.getInputStream();
        in = new ObjectInputStream(is);
    }
    public void reconnect(String ip, int port)  {
        try {
            Socket socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            InputStream is = socket.getInputStream();
            in = new ObjectInputStream(is);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Couldn't reconnect. Now closing.");
            System.exit(1);
        }
    }
}