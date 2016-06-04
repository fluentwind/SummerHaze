package com.fluentwind.tt.summerhaze.Config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/6/2.
 */
public class config {

    public static final String SERVER_URL = "http://192.168.1.66:8080/testserver/api.jsp";

    public static final String CHARSET = "utf-8";
    public static final String ALERT_WRONGUSERNAME = "用户名错误";
    public static final String ALERT_WRONGPASSWORD = "密码错误";
    public static final String ALERT_WRONGINFO = "用户名或密码错误";
    public static final String ALERT_NULLUSERNAME = "用户名不能为空";
    public static final String ALERT_NULLPASSWORD = "密码不能为空";
    public static final String ALERT_NULLPASSWORD2 = "请重复输入密码";
    public static final String ALERT_WRONGPASSWORD2 = "请输入两次相同密码";
    public static final String ALERT_NULLBOTH = "请输入用户名和密码";
    public static final String ALERT_NULL = "";
    public static final String ALERT_EXITONEMORETOUCH = "再点击一次退出";
    public static final String KEY_NULL = "";
    public static final String STRING_NOINFO = "未填写";
    public static final String STRING_CONNECTINGTOSERVER = "正在连接服务器";
    public static final String STRING_GETTINGFROMSERVER = "正在从服务器获取信息";
    public static final String STRING_CONNECTING = "正在连接";
    public static final String STRING_PLEASEWAIT = "请稍候";
    public static final String STRING_LOGOUTING = "正在退出";
    public static final String STRING_LOGINING = "正在登录";
    public static final String STRING_FAILTOLOGIN = "登录失败，请稍后重试";
    public static final String STRING_GETSUCCESS = "获取信息成功";
    public static final String STRING_FAILTOGETINFO = "获取信息失败，请稍后重试";
    public static final String STRING_WRONGINFO = "用户名或密码错误";
    public static final String STRING_REGIST_WRONG = "注册失败";
    public static final String STRING_REGIST_EXISTED = "该用户已经注册过";

    public static final String KEY_TOKEN = "token";

    public static final String KEY_USERINFO_NICKNAME = "user_nickname";
    public static final String KEY_USERINFO_SEX = "user_sex";
    public static final String KEY_USERINFO_DATE = "user_date";
    public static final String KEY_USERINFO_CITY = "user_city";
    public static final String KEY_USERINFO_LOGO = "user_logo";
    public static final String KEY_USERINFO_TEXT = "user_text";

    public static final String KEY_ACTION = "action";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USERNAME_MD5 = "username_md5";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PASSWORD_MD5 = "password_md5";
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CODE = "code";
    public static final String KEY_CONTACTS = "contatcs";
    public static final String KEY_PAGE = "page";
    public static final String KEY_PERPAGE = "perpage";
    public static final String KEY_TIMELINE = "items";
    public static final String KEY_MSG_ID = "msgId";
    public static final String KEY_MSG = "msg";
    public static final String KEY_COMMENTS = "items";
    public static final String KEY_CONTENT = "content";
    public static final String APP_ID = "com.fluentwind.tt.summerhaze";
    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_WRONG = 2;
    public static final int RESULT_STATUS_EXISTED = 3;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;
    public static final String ACTION_GET_CODE = "send_pass";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_REGIST = "regist";
    public static final String ACTION_GETINFO = "getinfo";
    public static final String ACTION_UPLOAD_CONTACTS = "upload_contacts";
    public static final String ACTION_TIMELINE = "timeline";
    public static final String ACTION_GET_COMMENT = "get_comment";
    public static final String ACTION_PUB_COMMENT = "pub_comment";
    public static final String ACTION_PUBLISH = "publish";



    public static String getCachedToken(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void cacheToken(Context context,String token){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN, token);
        e.commit();
    }

    public static String getCachedusername(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_USERNAME, null);
    }

    public static String getCacheduserpassword(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PASSWORD, null);
    }

    public static void cacheuserinfo(Context context,String username,String password){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_USERNAME, username);
        e.putString(KEY_PASSWORD, password);
        e.commit();
    }

}
