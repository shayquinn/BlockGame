package blockgame;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.omg.CORBA.portable.InputStream;
/*
public class Sound {

    final CountDownLatch clipDone = new CountDownLatch(1);

    void playSound(String filepath) {
        InputStream sound;
        try {
            File filePath = new File(filepath);
            if (filePath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(filePath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);

                final CountDownLatch clipDone = new CountDownLatch(1);
                clip.addLineListener((LineEvent event) -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                        clipDone.countDown();
                    }
                });
                clip.start();
                clipDone.await();
            } else {
                System.out.println("Can't find File!");
            }
        } catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException e) {
        }

    }
}
*/

public class Sound extends Thread{
    private String filepath;
    public Sound(String filepath) {
        this.filepath = filepath;
    }
    public void run() {
        InputStream sound;
        try {
            File filePath = new File(filepath);
            if (filePath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(filePath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);

                final CountDownLatch clipDone = new CountDownLatch(1);
                clip.addLineListener((LineEvent event) -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                        clipDone.countDown();
                    }
                });
                clip.start();
                clipDone.await();
            } else {
                System.out.println("Can't find File!");
            }
        } catch (IOException | InterruptedException | LineUnavailableException | UnsupportedAudioFileException e) {
        }
    }
}
