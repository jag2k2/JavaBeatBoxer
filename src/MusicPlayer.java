import javax.sound.midi.*;

public class MusicPlayer {
    private Sequencer sequencer;

    public MusicPlayer() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setTempoInBPM(120);
        } catch(Exception e) {e.printStackTrace();}
    }

    public void start(Sequence seq) {
        try {
            sequencer.setSequence(seq);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch(Exception err) {err.printStackTrace();}
    }

    public void stop() {
        sequencer.stop();
    }

    public void changeTempoByPercent(int percentagePoints){
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor(tempoFactor * (1 + ((float) percentagePoints)/100));
    }
}
