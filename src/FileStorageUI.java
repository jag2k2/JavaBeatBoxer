import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FileStorageUI {
    private final FileStorage fileStorage;
    private final BeatPatternConverter beatPatternConverter;
    private final Box buttonBox;
    private final JButton serializeButton;
    private final JButton restoreButton;

    public FileStorageUI(ArrayList<JCheckBox> userPatternSelection) {
        fileStorage = new FileStorage("Checkbox.ser");
        beatPatternConverter = new BeatPatternConverter(userPatternSelection);
        buttonBox = new Box(BoxLayout.X_AXIS);

        serializeButton = new JButton("Save");
        serializeButton.addActionListener(new MySerialListener());
        buttonBox.add(serializeButton);

        restoreButton = new JButton("Restore");
        restoreButton.addActionListener(new MyRestoreListener());
        buttonBox.add(restoreButton);
    }

    public Box getBox(){
        return buttonBox;
    }

    public class MySerialListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fileStorage.saveToFile(beatPatternConverter.flattenBeatPattern());
        }
    }

    public class MyRestoreListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            beatPatternConverter.displayBeatPattern(fileStorage.readFromFile());
        }
    }

}
