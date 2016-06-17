package com.fluentwind.tt.summerhaze.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fluentwind.tt.summerhaze.Activity.Activity_videoplayer;
import com.fluentwind.tt.summerhaze.Activity.MainActivity;
import com.fluentwind.tt.summerhaze.Config.config;
import com.igexin.sdk.PushConsts;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/8.
 */
public class GetuiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:

                String cid = bundle.getString("clientid");
                // TODO:处理cid返回
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
