import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.FileOutputStream;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class BeatBox {

    private static final String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
            "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
            "Low-mid Tom", "High Agogo", "Open Hi Conga"};

    private final JFrame theFrame;
    private final ArrayList<JCheckBox> checkboxList;
    private final JButton serializeIt;
    private final JButton restoreIt;
    private final JButton sendComment;
    private final JTextField comment;
    private final DefaultListModel<String> discussionElements;
    private final JList<String> discussion;
    private final ArrayList<ArrayList<Boolean>> patternStore;
    private final MusicPlayer musicPlayer;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    public static void main(String[] args) {
        BeatBox beatBox = new BeatBox();
        beatBox.networkSetUp();
        beatBox.startReaderThread();
        beatBox.addListeners();
        beatBox.buildGUI();
    }

    public BeatBox() {
        theFrame = new JFrame("Cyber BeatBox");
        checkboxList = new ArrayList<>();
        musicPlayer = new MusicPlayer();
        serializeIt = new JButton("Save");
        restoreIt = new JButton("Restore");
        sendComment = new JButton("Send");
        comment = new JTextField("Your comment", 20);
        discussionElements = new DefaultListModel<>();
        discussion = new JList<>(discussionElements);
        patternStore = new ArrayList<>();
    }

    public void networkSetUp() {
        try {
            Socket sock = new Socket("127.0.0.1", 4242);
            reader = new ObjectInputStream(sock.getInputStream());
            writer = new ObjectOutputStream(sock.getOutputStream());
            System.out.println("Network Established");
        } catch (IOException ex) {ex.printStackTrace();}
    }

    public void addListeners() {
        serializeIt.addActionListener(new MySerialListener());
        restoreIt.addActionListener(new MyRestoreListener());
        sendComment.addActionListener(new MySendListener());
        discussion.addListSelectionListener(new MySelectionListener());
    }

    public void startReaderThread() {
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    public class IncomingReader implements Runnable {
        public void run() {
            Object message;
            try {
                while ((message = reader.readObject()) != null) {
                    System.out.println("Read: " + message);
                    discussionElements.addElement((String) message);
                    patternStore.add((ArrayList<Boolean>) reader.readObject());
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public class MySerialListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try{
                FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));
                ObjectOutputStream os = new ObjectOutputStream(fileStream);
                os.writeObject(flattenBeatPattern());
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public class MyRestoreListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                FileInputStream fileIn = new FileInputStream(new File("Checkbox.ser"));
                ObjectInputStream is = new ObjectInputStream(fileIn);
                displayBeatPattern((ArrayList<Boolean>) is.readObject());
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public class MySendListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                writer.writeObject(comment.getText());
                writer.writeObject(flattenBeatPattern());
                writer.flush();
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public ArrayList<Boolean> flattenBeatPattern(){
        ArrayList<Boolean> checkboxState = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkboxList.get(i);
            checkboxState.add(check.isSelected());
        }
        return checkboxState;
    }

    public void displayBeatPattern(ArrayList<Boolean> checkBoxState) {
        musicPlayer.stop();
        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkboxList.get(i);
            check.setSelected(checkBoxState.get(i));
        }
    }

    public class MySelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if(e.getValueIsAdjusting()){
                System.out.println(discussion.getSelectedIndex());
                displayBeatPattern(patternStore.get(discussion.getSelectedIndex()));
            }
        }
    }

    public void buildGUI() {
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        Box saveBox = new Box(BoxLayout.Y_AXIS);
        Box buttonBox = new Box(BoxLayout.X_AXIS);
        BorderLayout backgroundLayout = new BorderLayout();
        JPanel backgroundPane = new JPanel(backgroundLayout);
        BorderLayout controlLayout = new BorderLayout();
        JPanel controlPane = new JPanel(controlLayout);
        BorderLayout commentLayout = new BorderLayout();
        JPanel commentPane = new JPanel(commentLayout);
        GridLayout grid = new GridLayout(16, 16);
        JPanel mainPanel = new JPanel(grid);
        JScrollPane dScroller = new JScrollPane(discussion);
        MusicPlayerControlUI musicPlayerControlUI = new MusicPlayerControlUI(checkboxList, musicPlayer);

        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        saveBox.add(serializeIt);
        saveBox.add(restoreIt);
        buttonBox.add(musicPlayerControlUI.getBox());
        buttonBox.add(saveBox);

        dScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        dScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        dScroller.setPreferredSize(new Dimension(50, -1));

        commentPane.add(BorderLayout.CENTER, comment);
        commentPane.add(BorderLayout.EAST, sendComment);

        controlPane.add(BorderLayout.NORTH, buttonBox);
        controlPane.add(BorderLayout.CENTER, dScroller);
        controlPane.add(BorderLayout.SOUTH, commentPane);

        for (String inst : instrumentNames) {
            nameBox.add(new Label(inst));
        }

        backgroundPane.add(BorderLayout.EAST, controlPane);
        backgroundPane.add(BorderLayout.WEST, nameBox);
        backgroundPane.add(BorderLayout.CENTER, mainPanel);
        theFrame.getContentPane().add(backgroundPane);

        grid.setVgap(1);
        grid.setHgap(2);

        for (int i = 0; i< 256; i++){
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
}
