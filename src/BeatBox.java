import java.awt.*;
import javax.swing.*;

public class BeatBox {
    private final CheckBoxGrid checkBoxGrid;
    private final MusicPlayer musicPlayer;
    private final UserChat userChat;

    public static void main(String[] args) {
        BeatBox beatBox = new BeatBox();
        beatBox.buildGUI();
    }

    public BeatBox() {
        checkBoxGrid = new CheckBoxGrid();
        musicPlayer = new MusicPlayer();
        userChat = new UserChat(checkBoxGrid, musicPlayer);
        userChat.startReaderThread();
    }

    public void buildGUI() {
        CheckBoxGridPanel checkBoxGridPanel = new CheckBoxGridPanel(checkBoxGrid);
        MusicPlayerControlPanel musicPlayerControlPanel = new MusicPlayerControlPanel(checkBoxGrid, musicPlayer);
        FileStoragePanel fileStoragePanel = new FileStoragePanel(checkBoxGrid, musicPlayer);
        UserChatPanel userChatPanel = new UserChatPanel(userChat);

        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.add(musicPlayerControlPanel.getBox());
        buttonBox.add(fileStoragePanel.getBox());

        Box controlPane = new Box(BoxLayout.Y_AXIS);
        controlPane.add(buttonBox);
        controlPane.add(userChatPanel.getPanel());

        JPanel backgroundPane = new JPanel(new BorderLayout());
        backgroundPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        backgroundPane.add(BorderLayout.EAST, controlPane);
        backgroundPane.add(BorderLayout.CENTER, checkBoxGridPanel.getPanel());

        JFrame theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.getContentPane().add(backgroundPane);
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
}
