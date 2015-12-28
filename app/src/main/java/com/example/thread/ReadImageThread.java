package com.example.thread;

import com.example.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kathy on 2015/12/27.
 */

public class ReadImageThread extends Thread{

    private String imageUrl;
    private String imagePath;
    private FileUtils fileUtils;

    public ReadImageThread(String imageUrl, String localImagePath){
        this.imageUrl = imageUrl;
        this.imagePath = localImagePath;
        fileUtils = new FileUtils();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        InputStream in = null;
        try {
            URL contactUrl = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) contactUrl.openConnection();
            in = connection.getInputStream();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            File file = fileUtils.createSDFile(imagePath);
            fileUtils.write2SDFromInput(file, in);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
