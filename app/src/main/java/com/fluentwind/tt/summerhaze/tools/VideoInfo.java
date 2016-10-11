package com.fluentwind.tt.summerhaze.tools;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/7/11.
 */
public class VideoInfo {
    private Bitmap bitmap;
    private String info;

    public VideoInfo (Bitmap bitmap,String info){
        setBitmap(bitmap);
        setInfo(info);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getInfo() {
        return info;
    }
}
