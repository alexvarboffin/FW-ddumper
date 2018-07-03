package com.walhalla.common;

import android.content.Context;
import android.os.Environment;

import com.walhalla.LocalStorage;
import com.walhalla.domen.item.MyObj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by combo on 12/19/2017.
 *
 * rm -rf /mnt/media_rw/sdcard0/FWddumper
 *
 */

public class FileUtil {

    private static final String FILE_BACKUP_FOLDER = "FWddumper";//android.os.Build.MODEL;

    public static final String LOC_STORAGE_IN = "/storage/emulated/0" + File.separator + FILE_BACKUP_FOLDER + "/input";
    private static final String LOC_STORAGE_OUT = "/storage/emulated/0" + File.separator + FILE_BACKUP_FOLDER + "/output";


    /**
     * No root
     */
    public final static String EXTERNAL_STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String EXT_STORAGE_IN = EXTERNAL_STORAGE_DIRECTORY + File.separator + FILE_BACKUP_FOLDER + "/input";
    public static final String EXT_STORAGE_OUT = EXTERNAL_STORAGE_DIRECTORY + File.separator + FILE_BACKUP_FOLDER + "/output";


    /**
     *
     * */
    public final static String EXTERNAL_STORAGE_DIRECTORY_ROOT = "/mnt/media_rw/sdcard0";
    //Environment.getDownloadCacheDirectory().getPath(); - ROOT CACHE

    public static final String EXT_STORAGE_IN_ROOT = EXTERNAL_STORAGE_DIRECTORY_ROOT + File.separator + FILE_BACKUP_FOLDER + "/input";


    public static String extOutRoot(Context context) {
        return
                LocalStorage.getInstance(context).saveSdCardLocation()
                        + File.separator + FILE_BACKUP_FOLDER + "/output/";
    }

    public static void removeFileFromInt2Ext(String fileName) throws Exception {
        moveFile(LOC_STORAGE_OUT, fileName, EXT_STORAGE_OUT);
    }

    public static boolean makeDir(String dir) {
        File file = new File(dir);
        return /*!file.isDirectory() &&*/ file.mkdirs();
    }

    public static boolean moveFile(String inputPath, String inputFile, String outputPath)
            throws Exception {

        boolean b;
        InputStream in;
        OutputStream out;

        //create output directory if it doesn't exist
        File dir = new File(outputPath);
        if (!dir.exists()) {
            b = dir.mkdirs();
        }


        in = new FileInputStream(inputPath + "/" + inputFile);
        out = new FileOutputStream(outputPath + "/" + inputFile);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        in.close();
        in = null;

        // write the output file
        out.flush();
        out.close();
        out = null;

        // delete the original file
        b = new File(inputPath + inputFile).delete();
        return true;
    }

    public static String fixName(MyObj block) {
        String fileName = block.getName();
        try {
            fileName += (new File(block.getLocation()).isFile() ? ".bin"
                    : ".img");
        } catch (Exception ignored) {
        }

        return fileName;
    }
}
