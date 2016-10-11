package com.fluentwind.tt.summerhaze.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fluentwind.tt.summerhaze.Activity.Activity_videoplayer;
import com.fluentwind.tt.summerhaze.Activity.MainActivity;
import com.fluentwind.tt.summerhaze.Config.config;
import com.igexin.sdk.PushConsts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/8.
 */
public class GetuiReceiver extends BroadcastReceiver {

    private String cid;
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:

                cid = bundle.getString("clientid");
                // TODO:处理cid返回



                        try {
                            String name ="cid.txt";
                            File file=new File( config.PATH_CACHE_ROOT);
                            if(!file.exists()) {
                                file.mkdir();

                            }
                            file=new File( config.PATH_CACHE_ROOT_CACHE);
                            if(!file.exists()) {
                                file.mkdir();

                            }
                            String fileName =config.PATH_CACHE_ROOT_CACHE+ "/" + name;
                            FileOutputStream b = new FileOutputStream(fileName);

                            b.write(cid.getBytes());
                            b.close();

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }









                break;
            case PushConsts.GET_MSG_DATA:

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    // TODO:接收处理透传（payload）数据
                    Log.i("msg",data);


                    try {
                        JSONObject json=new JSONObject(data);

                        String mode=json.getString(config.JSON_MODE);

                        switch (mode){
                            case config.JSON_MODE_RTSPONLY:
                                String path=json.getString(config.JSON_RTSP_PATH);
                                String cam=json.getString(config.JSON_RTSP_CAM);
                                String info=json.getString(config.JSON_RTSP_INFO);
                                Intent i = new Intent(context ,Activity_videoplayer.class);
                                i.putExtra("path", path.toString());
                                i.putExtra("cam", cam.toString());
                                i.putExtra("info", info.toString());
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);

                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                   // String host="http://sdk.open.api.igexin.com/apiex.htm";



                }
                break;
            default:
                break;
        }
    }
}
