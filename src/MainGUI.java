import javax.swing.*;
import java.awt.*;

public class MainGUI {
    JPanel backgroundPane;
    Box buttonBox;
    Box controlPane;

    public MainGUI() {
        backgroundPane = new JPanel(new BorderLayout());
        backgroundPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        buttonBox = new Box(BoxLayout.X_AXIS);
        controlPane = new Box(BoxLayout.Y_AXIS);
        controlPane.add(buttonBox);
        backgroundPane.add(BorderLayout.EAST, controlPane);
    }

    public void addMusicControlPanel(MusicPlayerControlPanel musicPlayerControlPanel) {
        buttonBox.add(musicPlayerControlPanel.getBox());
    }

    public void addFileStoragePanel(FileStoragePanel fileStoragePanel) {
        buttonBox.add(fileStoragePanel.getBox());
    }

    public void addUserChatPanel(UserChatPanel userChatPanel){
        controlPane.add(userChatPanel.getPanel());
    }

    public void addGridPanel(CheckBoxGridPanel checkBoxGridPanel) {
        backgroundPane.add(BorderLayout.CENTER, checkBoxGridPanel.getPanel());
    }

    public void launch() {
        JFrame theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.getContentPane().add(backgroundPane);
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
}
