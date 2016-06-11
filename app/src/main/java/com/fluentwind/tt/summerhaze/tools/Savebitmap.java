package com.fluentwind.tt.summerhaze.tools;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/10.
 */
public class Savebitmap {
    public static void savebitmap (Bitmap bitmap,String path, String fileName) {
        try {

            File File = new File(path,fileName);
            if(!File.exists()){
                File.delete();
                //File.createNewFile();
            }


            FileOutputStream out = new FileOutputStream(File);

            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out))
            {
                out.flush();
                out.close();
                System.out.println(File);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
