package com.fluentwind.tt.summerhaze.Config;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;


/**
 * Created by Administrator on 2016/6/3.
 */
public class commontools {

    public void dialog(Context  context, final Callback callback ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callback.ok();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });
        builder.create().show();
    }
    public static interface Callback{

        void ok();
    }


}
