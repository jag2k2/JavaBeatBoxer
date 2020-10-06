import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileStorageUI {
    private final FileStorage fileStorage;
    private final CheckBoxGrid checkBoxGrid;
    private final MusicPlayer musicPlayer;
    private final Box buttonBox;

    public FileStorageUI(CheckBoxGrid checkBoxGrid, MusicPlayer musicPlayer) {
        fileStorage = new FileStorage("Checkbox.ser");
        this.checkBoxGrid = checkBoxGrid;
        this.musicPlayer = musicPlayer;
        buttonBox = new Box(BoxLayout.X_AXIS);

        JButton serializeButton = new JButton("Save");
        serializeButton.addActionListener(new MySerialListener());
        buttonBox.add(serializeButton);

        JButton restoreButton = new JButton("Restore");
        restoreButton.addActionListener(new MyRestoreListener());
        buttonBox.add(restoreButton);
    }

    public Box getBox(){
        return buttonBox;
    }

    public class MySerialListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fileStorage.saveToFile(checkBoxGrid.getData());
        }
    }

    public class MyRestoreListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            musicPlayer.stop();
            checkBoxGrid.updateUI(fileStorage.readFromFile());
        }
    }

}
