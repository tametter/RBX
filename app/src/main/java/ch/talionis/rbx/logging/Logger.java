package ch.talionis.rbx.logging;

import android.util.Log;

public class Logger {
    public static void logV(Object object, String message, Object... args) {
        Log.v(object.getClass().getSimpleName(), String.format(message, args));
    }

    public static void logW(Object object, String message, Object... args) {
        Log.w(object.getClass().getSimpleName(), String.format(message, args));
    }

    public static void logE(Object object, String message, Object... args) {
        Log.e(object.getClass().getSimpleName(), String.format(message, args));
    }
}
