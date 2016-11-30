package com.heyi.UniversityNews.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by heyi on 2016/11/25.
 */

public class WriteAndReadUtils {

    private static BufferedInputStream bufferedInputStream;
    private static BufferedOutputStream bufferedOutputStream;

    public static boolean InputStreamToFile(InputStream is, File file){
        try {
            bufferedInputStream = new BufferedInputStream(is);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            int len=0;
            byte[] info=new byte[1024];
            while ((len=bufferedInputStream.read(info))!=-1){
                bufferedOutputStream.write(info,0,len);
            }
            return true;
        } catch (Exception e) {
            return false;
        }finally {
            try {
                bufferedOutputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFile(File file){
        file.delete();
    }
}
