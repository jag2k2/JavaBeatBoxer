import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerCommunication {
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private final BeatPatternConverter beatPatternConverter;

    public ServerCommunication(ArrayList<JCheckBox> userBeatPattern) {
        beatPatternConverter = new BeatPatternConverter(userBeatPattern);
        try {
            Socket sock = new Socket("127.0.0.1", 4242);
            reader = new ObjectInputStream(sock.getInputStream());
            writer = new ObjectOutputStream(sock.getOutputStream());
            System.out.println("Network Established");
        } catch (IOException ex) {ex.printStackTrace();}
    }

    public void sendCurrentPatternWithComment(String comment){
        try {
            writer.writeObject(comment);
            writer.writeObject(beatPatternConverter.flattenBeatPattern());
            writer.flush();
        } catch (Exception ex) {ex.printStackTrace();}
    }

    public String getIncomingComment() {
        String message = null;
        try {
            message = (String) reader.readObject();
        } catch (Exception e) {e.printStackTrace();}
        return message;
    }

    public ArrayList<Boolean> getIncomingPattern() {
        ArrayList<Boolean> pattern = new ArrayList<>();
        try {
            pattern = (ArrayList<Boolean>) reader.readObject();
        } catch (Exception e) {e.printStackTrace();}
        return pattern;
    }
}
