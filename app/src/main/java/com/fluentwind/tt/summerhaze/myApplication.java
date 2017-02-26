package com.fluentwind.tt.summerhaze;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2017/2/24.
 */

public class myApplication extends Application {
    private static volatile myApplication instance;

    public static synchronized myApplication getInstance() {

        if (instance==null){
            synchronized (myApplication.class){
                if(instance==null){
                    instance=new myApplication();
                }
            }
        }



        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //getInstance();
        x.Ext.init(this);
        System.out.println("xxxok");

    }
}
