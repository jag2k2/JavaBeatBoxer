import java.awt.*;
import javax.swing.*;

public class BeatBox {

    public static void main(String[] args) {
        BeatBox beatBox = new BeatBox();
        beatBox.buildGUI();
    }

    public void buildGUI() {
        CheckBoxGrid checkBoxGrid = new CheckBoxGrid();
        MusicPlayer musicPlayer = new MusicPlayer();
        MusicPlayerControlUI musicPlayerControlUI = new MusicPlayerControlUI(checkBoxGrid, musicPlayer);
        FileStorageUI fileStorageUI = new FileStorageUI(checkBoxGrid, musicPlayer);

        ChatUI chatUI = new ChatUI(checkBoxGrid, musicPlayer);
        chatUI.startReaderThread();

        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.add(musicPlayerControlUI.getBox());
        buttonBox.add(fileStorageUI.getBox());

        BorderLayout controlLayout = new BorderLayout();
        JPanel controlPane = new JPanel(controlLayout);
        controlPane.add(BorderLayout.NORTH, buttonBox);
        controlPane.add(BorderLayout.CENTER, chatUI.getScrollerPane());
        controlPane.add(BorderLayout.SOUTH, chatUI.getCommentPane());

        String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
                "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
                "Low-mid Tom", "High Agogo", "Open Hi Conga"};

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (String inst : instrumentNames) {
            nameBox.add(new Label(inst));
        }

        BorderLayout backgroundLayout = new BorderLayout();
        JPanel backgroundPane = new JPanel(backgroundLayout);
        backgroundPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        backgroundPane.add(BorderLayout.EAST, controlPane);
        backgroundPane.add(BorderLayout.WEST, nameBox);
        backgroundPane.add(BorderLayout.CENTER, checkBoxGrid.getGridPane());

        JFrame theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.getContentPane().add(backgroundPane);
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
}
