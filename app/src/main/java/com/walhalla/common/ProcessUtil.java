package com.walhalla.common;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class ProcessUtil {

//    private static final String TAG = "@@@";
//
//    public static void killprocess(){
//        Thread cancelThread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    String killstr = new String("/system/bin/kill -9 "
//                            + ProcessUtil.getppid(ProcessUtil.getpid(scanProcess), "su"));
//                    Log.i(TAG, "Executing kill: " + killstr);
//                    Runtime.getRuntime().exec(killstr);
//                } catch (IOException e) {
//                    Log.e(TAG, "Error killing process");
//                }
//            }
//        };
//        Log.i(TAG, "Starting canceling thread.");
//        cancelThread.start();
//    }
//
//    static public Integer getpid (Process process) {
//        Integer pid = null;
//        try {
//            Field f = process.getClass().getDeclaredField("pid");
//            f.setAccessible(true);
//            pid = f.getInt(process);
//        } catch (Throwable e) {
//        }
//        return pid;
//    }
//
//    static public Integer getppid (Integer pid, String shellToRun) {
//        String cmdline="ps";
//        String pstdout=null;
//        String[] commands = { cmdline };
//        Process psProcess;
//
//        DataOutputStream outputStream;
//        BufferedReader inputStream;
//
//        Integer retPid=null;
//
//        Log.i("NetworkMapper", "PS Finding parent of PID: " + pid);
//
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(shellToRun);
//            processBuilder.redirectErrorStream(true);
//            psProcess = processBuilder.start();
//
//            outputStream = new DataOutputStream(psProcess.getOutputStream());
//            inputStream = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));
//
//            for (String single : commands) {
//                Log.i("NetworkMapper","PS Executing: "+single);
//                outputStream.writeBytes(single + "\n");
//                outputStream.flush();
//            }
//            outputStream.writeBytes("exit\n");
//            outputStream.flush();
//            while (((pstdout = inputStream.readLine()) != null)) {
//                Log.i("NetworkMapper", "PSStdout: " + pstdout);
//                String[] fields = pstdout.split("[ ]+");
//                Log.i("NetworkMapper", "PSStdout: " + fields[0]+":"+fields[1]+":"+fields[2]);
//                try {
//                    Integer candPpid = new Integer(fields[2]);
//                    Log.i("NetworkMapper", "PSStdout: " + candPpid+":"+pid);
//                    if (candPpid.equals(pid)) {
//                        Integer candPid = new Integer(fields[1]);
//                        retPid = candPid;
//                        Log.i("NetworkMapper", "PS Found: " + candPpid + ":" + candPid);
//                        break;
//                    }
//                } catch (NumberFormatException e) {
//                    // ignore
//                }
//            }
//            psProcess.waitFor();
//            psProcess.destroy();
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        return retPid;
//    }
}
