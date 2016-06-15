package com.jiangli.jni.test;

import com.jiangli.jni.common.Config;
import org.junit.Test;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;

/**
 * Created by Jiangli on 2016/6/15.
 */
public class PlayMusicTest {

    @Test
    public void func() throws Exception {
        System.out.println(Config.continueModule_voice_alarm);

        FileInputStream inputStream = new FileInputStream(Config.continueModule_voice_alarm);
        AudioStream as = new AudioStream(inputStream);
        System.out.println("as:"+as);
        AudioPlayer.player.start(as);
//        Thread.sleep(999999);
        Thread.sleep(3999);
        AudioPlayer.player.stop(as);
        Thread.sleep(999999);
    }
}
