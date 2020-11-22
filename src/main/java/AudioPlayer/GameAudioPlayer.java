package AudioPlayer;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GameAudioPlayer implements AudioPlayer {
//    private Ucigame ucigame;
    private Clip audioClip;

    public GameAudioPlayer(String path){
        this.open(path);
    }

    @Override
    public void start() {
        this.audioClip.setFramePosition(0);
        System.out.println("sound start " + this.audioClip.getFrameLength());
        this.audioClip.start();
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
        this.audioClip.stop();
    }

    /**
     * LOOP_CONTINUOUSLY = -1
     * A value indicating that looping should continue indefinitely
     */
    @Override
    public void loop() {
        this.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Open audio file, javax.sound is not supported mp3 sound file, so we use wav sound.
     * @param path path of the file of sound
     */

    public void open(String path){
        URL url = this.getClass().getClassLoader().getResource(path);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(url);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        assert audioInputStream != null;
        AudioFormat audioFormat = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
        try {
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioInputStream);
        } catch (LineUnavailableException| IOException e) {
            e.printStackTrace();
        }
    }
}
