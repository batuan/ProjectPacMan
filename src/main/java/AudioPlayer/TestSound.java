package AudioPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

public class TestSound {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        File audioFile = new File("/Users/batuan/Documents/Master1Luminy/Genie Logiciel/TP3Pacman/Pacman/ProjectPacMan/src/main/resources/Sounds/CreditsTheme.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat audioFormat = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
        Clip audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioInputStream);
        audioClip.start();
        Thread.sleep(30000);
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[65536];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
}
