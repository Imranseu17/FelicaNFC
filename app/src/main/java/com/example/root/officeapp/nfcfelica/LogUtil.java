package com.example.root.officeapp.nfcfelica;

import android.os.Environment;
import android.util.Log;

import com.google.common.io.Files;

import org.joda.time.format.DateTimeFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class LogUtil {
    private static final int LOG_LEVEL = 3;
    private static final String TAG = "After";
    private static Queue<String> logDataQueue = new ConcurrentLinkedQueue();
    private static boolean mIsRunWriteThread = false;
    private static boolean mIsShowLog = true;

    public static void setShowLog(boolean isShowLog) {
        mIsShowLog = isShowLog;
    }

    public static void d() {
        outputLog(3, null, null);
    }

    public static void d(String message) {
        outputLog(3, message, null);
    }

    public static void d(String message, Throwable throwable) {
        outputLog(3, message, throwable);
    }

    public static void i(String message) {
        outputLog(4, message, null);
    }

    public static void i(String message, Throwable throwable) {
        outputLog(4, message, throwable);
    }

    public static void w(String message) {
        outputLog(5, message, null);
    }

    public static void w(String message, Throwable throwable) {
        outputLog(5, message, throwable);
    }

    public static void e(String message, Throwable throwable) {
        outputLog(6, message, throwable);
    }

    public static void e(Throwable throwable) {
        outputLog(6, null, throwable);
    }

    public static String Output(StackTraceElement[] ste) {
        String result = "";
        if (ste == null) {
            return result;
        }
        for (StackTraceElement s : ste) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(result);
            stringBuilder.append(s.toString());
            stringBuilder.append("\n");
            result = stringBuilder.toString();
        }
        return result;
    }

    private static void outputLog(int type, String message, Throwable throwable) {
        if (mIsShowLog) {
            if (message == null) {
                message = getStackTraceInfo();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getStackTraceInfo());
                stringBuilder.append(message);
                message = stringBuilder.toString();
            }
            switch (type) {
                case 3:
                    if (throwable != null) {
                        Log.d(TAG, message, throwable);
                        break;
                    } else {
                        Log.d(TAG, message);
                        break;
                    }
                case 4:
                    if (throwable != null) {
                        Log.i(TAG, message, throwable);
                        break;
                    } else {
                        Log.i(TAG, message);
                        break;
                    }
                case 5:
                    if (throwable != null) {
                        Log.w(TAG, message, throwable);
                        break;
                    } else {
                        Log.w(TAG, message);
                        break;
                    }
                case 6:
                    if (throwable != null) {
                        Log.e(TAG, message, throwable);
                        break;
                    } else {
                        Log.e(TAG, message);
                        break;
                    }
            }
            if (type >= 3) {
                addQueue(message, throwable);
                threadQueue();
            }
        }
    }

    private static void addQueue(String message, Throwable throwable) {
        String eMessage = "";
        if (throwable != null) {
            try {
                eMessage = getStackTraceString(throwable);
            } catch (IOException e) {
            }
        }
        String logWriteTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").toString();
        Queue queue = logDataQueue;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(logWriteTime);
        stringBuilder.append("\t");
        stringBuilder.append(message);
        stringBuilder.append("\n");
        stringBuilder.append(eMessage);
        queue.offer(stringBuilder.toString());
    }

    private static synchronized void threadQueue() {
        synchronized (LogUtil.class) {
            try {
                if (!mIsRunWriteThread) {
                    mIsRunWriteThread = true;
                    new Thread(new Runnable() {
                        public void run() {
                            StringBuffer logBuffer = new StringBuffer();
                            while (!LogUtil.logDataQueue.isEmpty()) {
                                String log = (String) LogUtil.logDataQueue.poll();
                                if (log != null) {
                                    logBuffer.append("============================================================\n");
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(LogUtil.getNowDate());
                                    stringBuilder.append("\n");
                                    logBuffer.append(stringBuilder.toString());
                                    logBuffer.append(log);
                                    logBuffer.append("\n============================================================");
                                }
                            }
                            LogUtil.writeLogFile(logBuffer);
                            LogUtil.mIsRunWriteThread = false;
                        }
                    }).start();
                }
            } catch (Exception e) {
                Log.e(TAG, "Log thread error", e);
            }
        }
        return;
    }

    private static String getNowDate() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-S").format(new Date(System.currentTimeMillis()));
    }

    private static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }

    /* JADX WARNING: Missing block: B:31:0x0091, code:
            return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void writeLogFile(StringBuffer logBuffer) {
        synchronized (LogUtil.class) {
            if (logBuffer != null) {
                if (logBuffer.length() != 0) {
                    try {
                        if (SettingData.Log.getOutputLog()) {
                            String pathStr;
                            String logNameStr;
                            StringBuilder sPath = new StringBuilder();
                            sPath.append(Environment.getExternalStorageDirectory().getPath());
                            sPath.append("D:\\kPos\\log");
                            pathStr = sPath.toString();
                            StringBuilder sLogName = new StringBuilder();
                            sLogName.append(getToday());
                            sLogName.append(".txt");
                            logNameStr = sLogName.toString();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(sPath);
                            stringBuilder.append(File.separator);
                            stringBuilder.append(sLogName);
                            File logFile = new File(stringBuilder.toString());
                            Files.createParentDirs(logFile);
                            FileWriter fileWriter = new FileWriter(logFile, true);
                            fileWriter.write(logBuffer.toString());
                            fileWriter.flush();
                            fileWriter.close();
                            logBuffer.charAt(0);
                            return;
                        }
                        logBuffer.charAt(0);
                    } catch (IOException e) {
                        try {
                            Log.e(TAG, "Log Write failed", e);
                        } catch (Throwable th) {
                            logBuffer.charAt(0);
                        }
                    }
                }
            }
        }
    }

    private static String getStackTraceInfo() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[5];
        String fullName = element.getClassName();
        String className = fullName.substring(fullName.lastIndexOf(".") + 1);
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<<");
        stringBuilder.append(className);
        stringBuilder.append("#");
        stringBuilder.append(methodName);
        stringBuilder.append(":");
        stringBuilder.append(lineNumber);
        stringBuilder.append(">> ");
        return stringBuilder.toString();
    }

    private static String getStackTraceString(Throwable e) throws IOException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}