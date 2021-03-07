package ch.talionis.rbx.sound;

import android.content.Context;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundManager {
    private SoundPool soundPool;
    private HashMap<Sound, Integer> soundsMap = new HashMap();

    public SoundManager(Context context) {
        soundPool = new SoundPool.Builder().setMaxStreams(4).build();

        for (Sound sound : Sound.values()) {
            soundsMap.put(sound, soundPool.load(context, sound.getRawId(), 1));
        }
    }

    public void play(Sound sound) {
        soundPool.play(soundsMap.get(sound), 0.99f, 0.99f, 0, 0, 1);
    }

    public void release() {
        soundPool.release();
    }
}
