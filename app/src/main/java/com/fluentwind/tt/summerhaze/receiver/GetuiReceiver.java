package com.fluentwind.tt.summerhaze.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fluentwind.tt.summerhaze.Activity.Activity_videoplayer;
import com.igexin.sdk.PushConsts;

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

                    Intent i = new Intent(context ,Activity_videoplayer.class);
                    i.putExtra("rtsp", data);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                    String host="http://sdk.open.api.igexin.com/apiex.htm";



                }
                break;
            default:
                break;
        }
    }
}
