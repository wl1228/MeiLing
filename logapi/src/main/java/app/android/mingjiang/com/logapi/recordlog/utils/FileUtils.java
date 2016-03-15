package app.android.mingjiang.com.logapi.recordlog.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：wangzs on 2016/1/11 16:06
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public final class FileUtils {

    public static void delete(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                delete(f);
            } else {
                f.delete();
            }
        }
        file.delete();
    }

    /**
     * apk文件拷贝。
     * @param context
     */
    public static void createFile(Context context) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = context.getAssets().open("logshow.apk");
            File file = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/logshow_temp.apk");
            if(file.exists()){
                return;
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

