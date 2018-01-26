package com.yzx.xiaomusic.service;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

/**
 * Created by yzx on 2018/1/25.
 * Description
 */

public class MediaSessionManager {

    private static final long MEDIA_SESSION_ACTIONS = PlaybackStateCompat.ACTION_PLAY
            | PlaybackStateCompat.ACTION_PAUSE
            | PlaybackStateCompat.ACTION_PLAY_PAUSE
            | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            | PlaybackStateCompat.ACTION_STOP
            | PlaybackStateCompat.ACTION_SEEK_TO;
    private static final java.lang.String TAG = "MediaSessionManager";
    private final PlayService playService;
    private MediaSessionCompat mediaSessionCompat;


    public MediaSessionManager(PlayService playService) {
        this.playService = playService;
        initMediaSession();
    }

    private void initMediaSession() {
        mediaSessionCompat = new MediaSessionCompat(playService, TAG);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS|MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS);
        mediaSessionCompat.setCallback(callback);
        mediaSessionCompat.setActive(true);
    }


    private MediaSessionCompat.Callback callback
            = new MediaSessionCompat.Callback(){


    };
}
