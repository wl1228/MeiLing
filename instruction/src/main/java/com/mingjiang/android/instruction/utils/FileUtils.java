package com.mingjiang.android.instruction.utils;

import android.content.Context;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by caoshuang on 15/7/22.
 */
public class FileUtils {

    /**
     * 判断本地数据库文件是否存在
     *
     * @return
     */
    public static boolean isFileExist(String filepath) {
        File file = new File(filepath);
        return file.exists();
    }

    /**
     * 删除本地数据库文件
     *
     * @return
     */
    public static boolean deleteFile(String filepath) {
        File file = new File(filepath);
        return file.delete();
    }

    /**
     * 根据URL得到输入流
     *
     * @param urlStr
     * @return
     */
    public static InputStream getInputStreamFromURL(String urlStr) {
        HttpURLConnection urlConn = null;
        InputStream inputStream = null;
        URL url = null;
        try {
            url = new URL(urlStr);
            urlConn = (HttpURLConnection) url.openConnection();
            inputStream = urlConn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    /**
     * 在指定文件路径创建文件
     *
     * @return
     * @throws IOException
     */
    public static File createSDFile(String filepath) throws IOException {
        File file = new File(filepath);
        file.createNewFile();
        return file;
    }

    public static String getTextFromAsset(Context context, String fileName) {
        String text = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        return text;
    }

    //将经过Base64编码的图片信息保存到本地文件
    public static void writeBase64(String picContent,String filePath){

        String string = Base64.encodeToString(picContent.getBytes(), Base64.DEFAULT);
        //解码部分string 是服务端发来的信息
        byte[] byteIcon= Base64.decode(string,Base64.DEFAULT);
        for (int i = 0; i < byteIcon.length; ++i) {
            if (byteIcon[i] < 0) {
                byteIcon[i] += 256;
            }
        }

        //建立一个文件对象
        File photoFile = new File(filePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(photoFile);
            if(!photoFile.exists())
            {
                photoFile.createNewFile();
            }
            //把图片数据写入文件形成图片
            fos.write(byteIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean writeFileByByte(byte[] bytes,String filePath){
        boolean retValue = false;
        File photoFile = new File(filePath);
        FileOutputStream fos = null;
        try {
            if(photoFile.isDirectory())
            {
                photoFile.delete();
                photoFile.createNewFile();
            }
            fos = new FileOutputStream(photoFile);
            if(!photoFile.exists())
            {
                photoFile.createNewFile();
            }
            //把图片数据写入文件形成图片
            fos.write(bytes);
            retValue = true;
        } catch (Exception e) {
            retValue = false;
            e.printStackTrace();
        }
        return retValue;
    }

    //Unicode编码转换
    public static String convertUnicode(String ori) {
        char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);

        }
        return outBuffer.toString();
    }

    public static byte[] getBytesFromStream(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while((len = is.read(buf, 0, size)) != -1) {
            bos.write(buf, 0, len);
        }
        buf = bos.toByteArray();

        return buf;
    }
}
