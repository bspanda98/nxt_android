package org.nxt.client.util;

import android.util.Log;

public class NxtLog {

     public enum LogLevel {
        QUIET(0), ERROR(1), WARN(2), INFO(3), VERBOSE(4), DEBUG(5);

        private int value;
        private LogLevel(int value) {
            this.value = value;
        }

        public boolean shouldLog(LogLevel level) {
            if (value >= level.value) {
                return true;
            }
            return false;
        }
    }

    protected static LogLevel level = LogLevel.ERROR;

    public static void setLogLevel(String newLevel) {
        LogLevel logLevel = level;
        if (newLevel.equalsIgnoreCase("quiet")) {
            logLevel = NxtLog.LogLevel.QUIET;
        } else if (newLevel.equalsIgnoreCase("error")) {
            logLevel = NxtLog.LogLevel.ERROR;
        } else if (newLevel.equalsIgnoreCase("warn")) {
            logLevel = NxtLog.LogLevel.WARN;
        } else if (newLevel.equalsIgnoreCase("info")) {
            logLevel = NxtLog.LogLevel.INFO;
        } else if (newLevel.equalsIgnoreCase("verbose")) {
            logLevel = NxtLog.LogLevel.VERBOSE;
        } else if (newLevel.equalsIgnoreCase("debug")) {
            logLevel = NxtLog.LogLevel.DEBUG;
        }
        setLogLevel(logLevel);
    }

    public static void setLogLevel(LogLevel level) {
        NxtLog.level = level;
    }

    public static LogLevel getLogLevel() {
        return NxtLog.level;
    }

    public static void v(String tag, String message) {
        if (level.shouldLog(LogLevel.VERBOSE)) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (level.shouldLog(LogLevel.DEBUG)) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (level.shouldLog(LogLevel.INFO)) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (level.shouldLog(LogLevel.WARN)) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (level.shouldLog(LogLevel.ERROR)) {
            Log.e(tag, message);
        }
    }

    public static void v(String tag, String message, Throwable t) {
        if (level.shouldLog(LogLevel.VERBOSE)) {
            Log.d(tag, message, t);
        }
    }

    public static void d(String tag, String message, Throwable t) {
        if (level.shouldLog(LogLevel.DEBUG)) {
            Log.d(tag, message, t);
        }
    }

    public static void i(String tag, String message, Throwable t) {
        if (level.shouldLog(LogLevel.INFO)) {
            Log.i(tag, message, t);
        }
    }

    public static void w(String tag, String message, Throwable t) {
        if (level.shouldLog(LogLevel.WARN)) {
            Log.w(tag, message, t);
        }
    }

    public static void e(String tag, String message, Throwable t) {
        if (level.shouldLog(LogLevel.ERROR)) {
            Log.e(tag, message, t);
        }
    }
}
