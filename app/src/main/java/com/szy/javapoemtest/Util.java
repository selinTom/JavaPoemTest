package com.szy.javapoemtest;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by devov on 2021/3/11.
 */

public class Util {
    public static String getErrorInfoFromException(Throwable e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception var3) {
            return "bad getErrorInfoFromException";
        }
    }

    public static void i(String tag,Exception e) {
        int max_str_length = 2001 - tag.length();

        String msg;
        for(msg = getErrorInfoFromException(e); msg.length() > max_str_length; msg = msg.substring(max_str_length)) {
            Log.i(tag, msg.substring(0, max_str_length));
        }

        Log.i(tag, msg);
    }
}
