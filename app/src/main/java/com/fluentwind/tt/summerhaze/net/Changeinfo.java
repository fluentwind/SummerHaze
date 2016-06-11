package com.fluentwind.tt.summerhaze.net;

import com.fluentwind.tt.summerhaze.Config.config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/9.
 */
public class Changeinfo {

    public Changeinfo(String username_md5,String nickname,String sex,String date,String city,String text,final SuccessCallback successCallback,final FailCallback failCallback) {
        new NetConnection(config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(config.KEY_STATUS)) {
                        case config.RESULT_STATUS_SUCCESS:
                            if (successCallback!=null) {
                                successCallback.onSuccess();
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
        }, config.KEY_ACTION,config.ACTION_CHANGEINFO,config.KEY_USERNAME_MD5,username_md5, config.KEY_NICKNAME,nickname,
                config.KEY_SEX,sex,config.KEY_DATE,date,config.KEY_CITY,city,config.KEY_TEXT,text);
    }

    public static interface SuccessCallback{
        void onSuccess();
        void onWrong();
    }

    public static interface FailCallback{
        void onFail();
    }

}
