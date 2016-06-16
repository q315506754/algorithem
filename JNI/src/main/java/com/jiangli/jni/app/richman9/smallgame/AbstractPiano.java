package com.jiangli.jni.app.richman9.smallgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Jiangli on 2016/6/16.
 */
public class AbstractPiano implements Musicial {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final String filePath;
    protected  AudioStream as;

    public AbstractPiano(String filePath) throws Exception {
        this.filePath = filePath;

        openMusicFile();
    }

    private void openMusicFile() throws IOException {
        as = new AudioStream(new FileInputStream(this.filePath));
    }

    @Override
    public void playMusic() {
        AudioPlayer.player.start(as);

    }

    @Override
    public void stopMusic() {
        AudioPlayer.player.stop(as);

        try {
            as.close();

            openMusicFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
