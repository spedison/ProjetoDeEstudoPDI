package br.com.spedison.pds;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundPlayerUsingClip implements LineListener {
    protected boolean isPlaybackCompleted;

    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }
}
