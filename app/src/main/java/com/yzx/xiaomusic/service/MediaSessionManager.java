package com.yzx.xiaomusic.service;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.yzx.xiaomusic.entities.MusicMessage;

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
    private static final java.lang.String TAG = "yglMediaSessionManager";
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


    public void updatePlaybackState() {
        int state = playService.isPlaying()? PlaybackStateCompat.STATE_PLAYING : PlaybackStateCompat.STATE_PAUSED;
        mediaSessionCompat.setPlaybackState(
                new PlaybackStateCompat.Builder()
                        .setActions(MEDIA_SESSION_ACTIONS)
                        .setState(state, playService.getProgress(), 1)
                        .build());
    }

    public void updateMetaData(MusicMessage music) {
        if (music == null) {
            mediaSessionCompat.setMetadata(null);
            return;
        }

        MediaMetadataCompat.Builder metaData = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, music.getName())
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, music.getArtist())
//                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, music.getAlbum())
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, music.getArtist())
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, music.getTotalTime());
//                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, CoverLoader.getInstance().loadThumbnail(music));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            metaData.putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, AppCache.get().getMusicList().size());
//        }

        mediaSessionCompat.setMetadata(metaData.build());
    }

    public void release() {
        mediaSessionCompat.setCallback(null);
        mediaSessionCompat.setActive(false);
        mediaSessionCompat.release();
    }

    private MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay()  {
            Log.i(TAG, "onPlay: ");
            if (playService.isPlaying()){
                playService.pause();
            }else {
                playService.play();
            }
        }

        @Override
        public void onPause() {
            Log.i(TAG, "onPause: ");
            if (playService.isPlaying()){
                playService.pause();
            }else {
                playService.play();
            }
        }

        @Override
        public void onSkipToNext() {
            Log.i(TAG, "onSkipToNext: ");
            playService.next();
        }

        @Override
        public void onSkipToPrevious() {
            Log.i(TAG, "onSkipToPrevious: ");
            playService.previous();
        }

        @Override
        public void onStop() {
//            mPlayService.stop();
            Log.i(TAG, "onStop: ");
        }

        @Override
        public void onSeekTo(long pos) {
//            mPlayService.seekTo((int) pos);
            Log.i(TAG, "onSeekTo: "+pos);
            playService.setProgress((int) pos);
            playService.previous();
        }

    };
}
