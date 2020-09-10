package com.choicemmed.common;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by Luis on 2015/6/17.
 */
public class AlarmUtils {

    private final int MP3_TO_PLAY=0;
    private static MediaPlayer mMediaPlayer;
    private static AudioManager mAudioManager;

    public static void startAlarm(Context context,int audio) {
        mAudioManager=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMediaPlayer=MediaPlayer.create (context, audio);	//设置音频源
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置流类型
        mMediaPlayer.setLooping(false);	//设置是否循环播放
        mMediaPlayer.start();	//开始播放

    }

    public static void stopAlarm(){
    }

}
