import java.awt.*;
import javax.swing.*;
import java.util.*;

public class BeatBox {

    public static void main(String[] args) {
        BeatBox beatBox = new BeatBox();
        beatBox.buildGUI();
    }

    public void buildGUI() {
        ArrayList<JCheckBox> checkboxList = new ArrayList<>();
        MusicPlayer musicPlayer = new MusicPlayer();

        MusicPlayerControlUI musicPlayerControlUI = new MusicPlayerControlUI(checkboxList, musicPlayer);
        FileStorageUI fileStorageUI = new FileStorageUI(checkboxList, musicPlayer);

        BorderLayout backgroundLayout = new BorderLayout();
        JPanel backgroundPane = new JPanel(backgroundLayout);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);

        JPanel mainPanel = new JPanel(grid);
        for (int i = 0; i< 256; i++){
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }



        ChatUI chatUI = new ChatUI(checkboxList, musicPlayer);
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

        String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
                "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
                "Low-mid Tom", "High Agogo", "Open Hi Conga"};

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (String inst : instrumentNames) {
            nameBox.add(new Label(inst));
        }

        backgroundPane.add(BorderLayout.EAST, controlPane);
        backgroundPane.add(BorderLayout.WEST, nameBox);
        backgroundPane.add(BorderLayout.CENTER, mainPanel);



        JFrame theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.getContentPane().add(backgroundPane);
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
}
