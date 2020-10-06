import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CheckBoxGrid {
    private final ArrayList<JCheckBox> userBeatPattern;
    private final JPanel gridPane;

    public CheckBoxGrid() {
        userBeatPattern = new ArrayList<>();
        GridLayout gridLayout = new GridLayout(16, 16);
        gridLayout.setVgap(1);
        gridLayout.setHgap(2);
        gridPane = new JPanel(gridLayout);

        for (int i = 0; i< 256; i++){
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            userBeatPattern.add(c);
            gridPane.add(c);
        }
    }

    public JPanel getPanel() {
        return gridPane;
    }

    public ArrayList<Boolean> getData(){
        ArrayList<Boolean> checkboxState = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            JCheckBox check = userBeatPattern.get(i);
            checkboxState.add(check.isSelected());
        }
        return checkboxState;
    }

    public void updateUI(ArrayList<Boolean> checkBoxState) {
        for (int i = 0; i < 256; i++) {
            JCheckBox check = userBeatPattern.get(i);
            check.setSelected(checkBoxState.get(i));
        }
    }
}
