import java.io.*;
import java.util.*;

public class FileStorage {
    private final String filePath;

    public FileStorage(String filePath) {
        this.filePath = filePath;
    }

    public void saveToFile(ArrayList<Boolean> data) {
        try{
            FileOutputStream fileStream = new FileOutputStream(new File(filePath));
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(data);
        } catch (Exception ex) {ex.printStackTrace();}
    }

    public ArrayList<Boolean> readFromFile() {
        ArrayList<Boolean> patternRead = null;
        try {
            FileInputStream fileIn = new FileInputStream(new File(filePath));
            ObjectInputStream is = new ObjectInputStream(fileIn);
            patternRead = (ArrayList<Boolean>) is.readObject();
        } catch (Exception ex) {ex.printStackTrace();}
       return patternRead;
    }
}
