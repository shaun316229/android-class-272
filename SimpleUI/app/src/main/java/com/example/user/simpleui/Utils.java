package com.example.user.simpleui;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by user on 2016/8/16.
 */
public class Utils {

    public static  void writeFile(Context context,String fileName,String content)//寫一個資訊
    {
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_APPEND);
            fos.write(content.getBytes());//寫檔
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  String readFile(Context context,String fileName)//讀檔
    {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            byte[] bytes = new byte[2048];
            fis.read(bytes, 0, bytes.length);//讀到沒有
            fis.close();
            String content = new String(bytes);
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return"";//沒有讀到東西回傳空字串
    }
}
