import javax.swing.*;
import java.util.ArrayList;

public class BeatPatternConverter {
    private final ArrayList<JCheckBox> userBeatPattern;

    public BeatPatternConverter(ArrayList<JCheckBox> userBeatPattern) {
        this.userBeatPattern = userBeatPattern;
    }

    public ArrayList<Boolean> flattenBeatPattern(){
        ArrayList<Boolean> checkboxState = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            JCheckBox check = userBeatPattern.get(i);
            checkboxState.add(check.isSelected());
        }
        return checkboxState;
    }

    public void displayBeatPattern(ArrayList<Boolean> checkBoxState) {
        for (int i = 0; i < 256; i++) {
            JCheckBox check = userBeatPattern.get(i);
            check.setSelected(checkBoxState.get(i));
        }
    }
}
