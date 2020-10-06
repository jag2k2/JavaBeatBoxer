import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatCommunication {
    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    public ChatCommunication() {
        try {
            Socket sock = new Socket("127.0.0.1", 4242);
            reader = new ObjectInputStream(sock.getInputStream());
            writer = new ObjectOutputStream(sock.getOutputStream());
            System.out.println("Network Established");
        } catch (IOException ex) {ex.printStackTrace();}
    }

    public void sendPattern(String comment, CheckBoxGrid checkBoxGrid){
        try {
            writer.writeObject(comment);
            writer.writeObject(checkBoxGrid.getData());
            writer.flush();
        } catch (Exception ex) {ex.printStackTrace();}
    }

    public String readIncomingComment() {
        String message = null;
        try {
            message = (String) reader.readObject();
        } catch (Exception e) {e.printStackTrace();}
        return message;
    }

    public ArrayList<Boolean> readIncomingPattern() {
        ArrayList<Boolean> pattern = new ArrayList<>();
        try {
            pattern = (ArrayList<Boolean>) reader.readObject();
        } catch (Exception e) {e.printStackTrace();}
        return pattern;
    }
}
