package com.szy.javapoemtest;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by zouxipu on 2017/3/29.
 * zouxipu@haohauzhu.com
 */

public class MediaPlayerUtils {

    public static void showVibrator(Context context,int time){
        Vibrator vibrator = (Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator!=null && vibrator.hasVibrator()) {
            vibrator.vibrate(time);
        }
    }

}
