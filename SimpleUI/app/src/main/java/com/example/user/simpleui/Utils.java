package com.example.user.simpleui;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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

    public static double[] getLatLngFromAddress(String address)
    {
            try {
                address = URLEncoder.encode(address,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        String apiURL="http://maps.google.com/maps/api/geocode/json?address="+address;

        byte[] data= Utils.urlToBytes(apiURL);

        if(data == null)
            return null;
        String result = new String(data);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("status").equals("OK"))
            {
                JSONObject location = jsonObject.getJSONArray("results")
                                                .getJSONObject(0)
                                                .getJSONObject("geometry")
                                                .getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                return new double[]{lat,lng};
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static byte[] urlToBytes(String urlString)
    {
        try {
            URL url= new URL(urlString);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len=0;
            while ((len = inputStream.read(buffer))!=-1)
            {
                byteArrayOutputStream.write(buffer,0,len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
