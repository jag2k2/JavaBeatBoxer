import javax.sound.midi.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class MusicPlayerControlPanel {

    private static final int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
    private final Box buttonBox;
    private final MusicPlayer musicPlayer;
    private final CheckBoxGrid checkBoxGrid;

    public MusicPlayerControlPanel(CheckBoxGrid checkBoxGrid, MusicPlayer musicPlayer) {
        this.checkBoxGrid = checkBoxGrid;
        this.musicPlayer = musicPlayer;

        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton upTempo = new JButton("Tempo Up");
        JButton downTempo = new JButton("Tempo Down");

        start.addActionListener(new MyStartListener());
        stop.addActionListener(new MyStopListener());
        upTempo.addActionListener(new MyUpTempoListener());
        downTempo.addActionListener(new MyDownTempoListener());

        buttonBox = new Box(BoxLayout.Y_AXIS);
        buttonBox.add(start);
        buttonBox.add(stop);
        buttonBox.add(upTempo);
        buttonBox.add(downTempo);
    }

    public Box getBox() {
        return buttonBox;
    }

    class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Sequence music = buildSequence();
            musicPlayer.start(music);
        }
    }

    private Sequence buildSequence() {
        Sequence seq = null;
        try {
            ArrayList<Boolean> userSelections = checkBoxGrid.getData();
            seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    Boolean selection = userSelections.get(j + 16 * i);
                    if (selection) {
                        track.add(makeEvent(144,9,instruments[i], 100, j));
                        track.add(makeEvent(128,9,instruments[i], 100, j+1));
                    }
                }
                track.add(makeEvent(176, 1, 127, 0, 16));
            }
            track.add(makeEvent(192, 9, 1, 0, 15));
        } catch(Exception e) {e.printStackTrace();}
        return seq;
    }

    private MidiEvent makeEvent (int cmd, int channel, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(cmd, channel, one, two);
            event = new MidiEvent(a, tick);
        } catch(Exception e) {e.printStackTrace();}
        return event;
    }

    class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            musicPlayer.stop();
        }
    }

    class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            musicPlayer.changeTempoByPercent(3);
        }
    }

    class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            musicPlayer.changeTempoByPercent(-3);
        }
    }
}
