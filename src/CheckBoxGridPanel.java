import javax.swing.*;
import java.awt.*;

public class CheckBoxGridPanel {
    private final JPanel panel;

    public CheckBoxGridPanel(CheckBoxGrid checkBoxGrid){
        String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
                "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
                "Low-mid Tom", "High Agogo", "Open Hi Conga"};

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (String inst : instrumentNames) {
            nameBox.add(new Label(inst));
        }
        panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, checkBoxGrid.getPanel());
        panel.add(BorderLayout.WEST, nameBox);
    }

    public JPanel getPanel() {
        return panel;
    }
}
