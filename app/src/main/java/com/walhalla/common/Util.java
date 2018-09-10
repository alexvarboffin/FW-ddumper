package com.walhalla.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.walhalla.fwdumper.R;

import java.io.File;

public class Util {


    /**
     * /system/bin/toolbox - NOX emulator bin location
     *
     *
     * В эмуляторе вместо BusyBox стоит урезаный toolbox без netcat
     *
     *
     *
     *
     * nc linked to /system/xbin/busybox
     */


    private static final String BB_LOCATION = "/system/xbin/busybox";
    public static final String[] BB_PACKAGES = {
            "stericson.busybox"
    };


    public static String getNetcatLocation() {

//        String[] arr = {
//                "nc",
//                "/system/xbin/nc",
//                "/su/xbin/nc"
//        };
//        for (String s : arr) {
//            boolean isExist = new File(s).exists();
//            if(isExist){
//                return s;
//            }
//        }

        return "nc";
    }


    private static final String TAG = "@@@";

    /**
     * echo $PATH
     * /sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin
     *
     * @return
     */

    public static boolean ibBbInstalled(Context context) {
        for (String name : BB_PACKAGES) {
            try {
                context.getPackageManager().getPackageInfo(name, 0);
                return true;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        return false;
    }


    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }


        Log.i(TAG, ">>>: " + command + "|" + executedSuccesfully);

        return executedSuccesfully;
    }


    /**
     * Dialogs
     *
     * @param message
     * @param result
     */


    public static void resultPopUp(Context context, String message, boolean result) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, null);

        if (result) {
            builder.setNeutralButton(R.string.action_open_fm, (dialog, which) -> {
                //Toast.makeText(context, "@@@", Toast.LENGTH_SHORT).show();
                openFolder(context);
            });
        }
        builder.create();
        builder.show();
    }

    private static void openFolder(Context context) {


//        Toast.makeText(context, FileUtil.EXT_STORAGE_OUT, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri file = Uri.parse(FileUtil.EXT_STORAGE_OUT);
        intent.setDataAndType(file, "*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivity(Intent.createChooser(intent, "Open folder"));
    }


    public static void installPopUp(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                //.setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.title_attention)
                .setMessage("Для работы данного приложения необходимо установить BusyBox\nУстановить BusyBox?")

                .setCancelable(true)
                .setPositiveButton(R.string.play_dialog_yes_msg, (dialog1, which) -> {

                    try {
                        String s = String.format(context.getString(R.string.market_url), BB_PACKAGES[0]);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s))
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent);
                    } catch (Exception ignored) {
                        Toast.makeText(context, ignored.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                }).setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.show();
    }


    public static void openFile(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(file), "*/*");
        context.startActivity(intent);
    }
}
