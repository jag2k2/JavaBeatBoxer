import java.io.*;
import java.net.*;
import java.util.*;

public class MusicServer {

    private ArrayList<ObjectOutputStream> clientOutputStreams;

    public MusicServer() {
        clientOutputStreams = new ArrayList<>();
    }

    public static void main(String[] args) {
        new MusicServer().go();
    }
    public void go() {
        try {
            ServerSocket serverSock = new ServerSocket(4242);

            while(true){
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStreams.add(out);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("Got a connection");
            }
        } catch (Exception ex) {ex.printStackTrace();}
    }
    public class ClientHandler implements Runnable {
        ObjectInputStream in;
        Socket clientSocket;

        public ClientHandler(Socket socket) {
            try {
                clientSocket = socket;
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            Object o2 = null;
            Object o1 = null;
            try {
                while((o1 = in.readObject()) != null) {
                    o2 = in.readObject();
                    tellEveryone(o1,o2);
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public void tellEveryone(Object one, Object two) {
        for(ObjectOutputStream out : clientOutputStreams) {
            try {
                out.writeObject(one);
                out.writeObject(two);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

}