import java.awt.*;
import javax.swing.*;
import java.util.*;

public class BeatBox {

    private static final String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
            "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
            "Low-mid Tom", "High Agogo", "Open Hi Conga"};

    private final ArrayList<JCheckBox> checkboxList;
    private final MusicPlayer musicPlayer;

    public static void main(String[] args) {
        BeatBox beatBox = new BeatBox();
        beatBox.buildGUI();
    }

    public BeatBox() {
        checkboxList = new ArrayList<>();
        musicPlayer = new MusicPlayer();
    }

    public void buildGUI() {
        Box nameBox = new Box(BoxLayout.Y_AXIS);

        BorderLayout backgroundLayout = new BorderLayout();
        JPanel backgroundPane = new JPanel(backgroundLayout);

        GridLayout grid = new GridLayout(16, 16);
        JPanel mainPanel = new JPanel(grid);
        MusicPlayerControlUI musicPlayerControlUI = new MusicPlayerControlUI(checkboxList, musicPlayer);
        FileStorageUI fileStorageUI = new FileStorageUI(checkboxList);

        ChatUI chatUI = new ChatUI(checkboxList);
        chatUI.startReaderThread();

        backgroundPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.add(musicPlayerControlUI.getBox());
        buttonBox.add(fileStorageUI.getBox());

        BorderLayout controlLayout = new BorderLayout();
        JPanel controlPane = new JPanel(controlLayout);
        controlPane.add(BorderLayout.NORTH, buttonBox);
        controlPane.add(BorderLayout.CENTER, chatUI.getScrollerPane());
        controlPane.add(BorderLayout.SOUTH, chatUI.getCommentPane());

        for (String inst : instrumentNames) {
            nameBox.add(new Label(inst));
        }

        backgroundPane.add(BorderLayout.EAST, controlPane);
        backgroundPane.add(BorderLayout.WEST, nameBox);
        backgroundPane.add(BorderLayout.CENTER, mainPanel);

        grid.setVgap(1);
        grid.setHgap(2);

        for (int i = 0; i< 256; i++){
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        JFrame theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.getContentPane().add(backgroundPane);
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
}
