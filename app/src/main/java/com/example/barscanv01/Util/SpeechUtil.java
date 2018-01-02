package com.example.barscanv01.Util;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

/**
 * Created by zhulin on 2017/12/25.
 */

public class SpeechUtil implements SpeechSynthesizerListener {
    protected static final int UI_LOG_TO_VIEW = 0;
    private SpeechSynthesizer speechSynthesizer;
    private Context context;
    private TtsMode ttsMode=TtsMode.MIX;
    private AuthInfo authInfo;


    public SpeechUtil(Context activity){
        this.context = activity;
        init();
    }

    private void init() {
        speechSynthesizer= SpeechSynthesizer.getInstance();
        speechSynthesizer.setContext(context);
        speechSynthesizer.setSpeechSynthesizerListener(this);
        speechSynthesizer.setAppId("10552512");
        speechSynthesizer.setApiKey("DOBhoEy22BIbcAA8LcT4baGC","7883e7dcd9cb0491e1ca13b8d1509a2e");
        authInfo=speechSynthesizer.auth(ttsMode);
        if(authInfo.isSuccess()){
            Toast.makeText(context,"语音合成验证成功",Toast.LENGTH_SHORT).show();
        }
        //speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        setParam();
    }

    private void setParam() {
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "1"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9"); // 设置合成的音量，0-9 ，默认 5
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "4");// 设置合成的语速，0-9 ，默认 5
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
/*        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE,
                SpeechSynthesizer.AUDIO_ENCODE_AMR);//音频格式，支持bv/amr/opus/mp3，取值详见随后常量声明
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE,
                SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);//音频比特率，各音频格*/
    }

    public void speak( String content) {
        speechSynthesizer.initTts(TtsMode.MIX);
        int result= speechSynthesizer.speak(content);
       // Toast.makeText(context,result+"",Toast.LENGTH_LONG).show();
    }



    @Override
    public void onSynthesizeStart(String s) {
        Log.e("Sound","start");
    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        Log.e("Sound","onSynthesizeDataArrived");
    }

    @Override
    public void onSynthesizeFinish(String s) {
        Log.e("Sound","onSynthesizeFinish");
    }

    @Override
    public void onSpeechStart(String s) {
        Log.e("Sound","onSpeechStart");
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        Log.e("Sound","onSpeechProgressChanged");
    }

    @Override
    public void onSpeechFinish(String s) {
        Log.e("Sound","onSpeechFinish");
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        Log.d("Sound123",speechError.code+"");
    }
}
