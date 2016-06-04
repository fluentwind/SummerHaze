package com.fluentwind.tt.summerhaze.net;

import android.graphics.Bitmap;

import com.fluentwind.tt.summerhaze.Config.config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/3.
 */
public class Getinfo {
    public Getinfo(String username_md5,final SuccessCallback successCallback,final FailCallback failCallback) {
        new NetConnection(config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(config.KEY_STATUS)) {
                        case config.RESULT_STATUS_SUCCESS:
                            if (successCallback!=null) {
                                successCallback.onSuccess(obj.getString(config.KEY_USERINFO_NICKNAME),
                                        obj.getString(config.KEY_USERINFO_DATE),
                                        obj.getString(config.KEY_USERINFO_SEX),
                                        obj.getString(config.KEY_USERINFO_TEXT),
                                        obj.getString(config.KEY_USERINFO_CITY),
                                         obj.getString(config.KEY_USERINFO_LOGO));
                            }
                            break;
                        case config.RESULT_STATUS_WRONG:
                            if (successCallback!=null) {
                                successCallback.onWrong();
                            }
                            break;
                        default:
                            if (failCallback!=null) {
                                failCallback.onFail();
                            }
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    if (failCallback!=null) {
                        failCallback.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallback() {

            @Override
            public void onFail() {
                if (failCallback!=null) {
                    failCallback.onFail();
                }
            }
        }, config.KEY_ACTION,config.ACTION_GETINFO,config.KEY_USERNAME_MD5,username_md5);
    }

    public static interface SuccessCallback{
        void onSuccess(String nickname, String date, String sex, String text, String city, String logo);
        void onWrong();
    }

    public static interface FailCallback{
        void onFail();
    }
}
