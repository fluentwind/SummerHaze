package com.fluentwind.tt.summerhaze.Config;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;


import com.fluentwind.tt.summerhaze.tools.Bitmap_String;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Administrator on 2016/6/2.
 */
public class config {

    public static final String SERVER_URL = "http://192.168.0.104:8080/testserver/api.jsp";
    public static final String PACKAGENAME="com.fluentwind.tt.summerhaze";
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
    public static final String STRING_NULL = "";
    public static final String STRING_NODETAILS = "暂无详情";
    public static final String STRING_CONNECTINGTOSERVER = "正在连接服务器";
    public static final String STRING_GETTINGFROMSERVER = "正在从服务器获取信息";
    public static final String STRING_CONNECTING = "正在连接";
    public static final String STRING_PLEASEWAIT = "请稍候";
    public static final String STRING_LOGOUTING = "正在退出";
    public static final String STRING_LOGINING = "正在登录";
    public static final String STRING_FAILTOLOGIN = "登录失败，请稍后重试";
    public static final String STRING_FAILTOCHANGEINFO = "修改信息失败，请稍后重试";
    public static final String STRING_FAILTOCHANGELOGO = "更新头像失败，请稍后重试";
    public static final String STRING_SUCCESSTOCHANGELOGO = "更新头像成功";
    public static final String STRING_FAILTOOPENLOGO = "打开头像失败";
    public static final String STRING_GETSUCCESS = "获取信息成功";
    public static final String STRING_FAILTOGETINFO = "获取信息失败，请稍后重试";
    public static final String STRING_WRONGINFO = "用户名或密码错误";
    public static final String STRING_REGIST_WRONG = "注册失败";
    public static final String STRING_REGIST_EXISTED = "该用户已经注册过";
    public static final String STRING_LOGO = "logo";
    public static final String STRING_USERNAME = "用户名";
    public static final String STRING_NICKNAME = "昵称";
    public static final String STRING_SEX = "性别";
    public static final String STRING_DATE = "生日";
    public static final String STRING_CITY = "城市";
    public static final String STRING_SEX_MALE = "男";
    public static final String STRING_SEX_FEMALE = "女";
    public static final String STRING_TEXT = "签名";
    public static final String KEY_TOKEN = "token";

    public static final String KEY_USERINFO_NICKNAME = "user_nickname";
    public static final String KEY_USERINFO_SEX = "user_sex";
    public static final String KEY_USERINFO_DATE = "user_date";
    public static final String KEY_USERINFO_CITY = "user_city";
    public static final String KEY_USERINFO_LOGO = "user_logo";
    public static final String KEY_USERINFO_TEXT = "user_text";


    public static final String JSON_MODE = "JSON_MODE";
    public static final String JSON_MODE_RTSPONLY = "MODE_RTSPONLY";
    public static final String JSON_RTSP_PATH = "RTSP_PATH";
    public static final String JSON_RTSP_CAM = "RTSP_CAM";
    public static final String JSON_RTSP_INFO = "RTSP_INFO";

    public static final String KEY_ACTION = "action";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USERNAME_MD5 = "username_md5";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PASSWORD_MD5 = "password_md5";
    public static final String KEY_TOUCHUAN = "FLAG_TOUCHUAN";


    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CODE = "code";
    public static final String KEY_NICKNAME = "nickname";
    public static final String KEY_SEX = "sex";
    public static final String KEY_DATE = "date";
    public static final String KEY_CITY = "city";
    public static final String KEY_TEXT = "text";
    public static final String KEY_STRING_LOGO = "string_logo";
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
    public static final String ACTION_CHANGEINFO = "changeinfo";
    public static final String ACTION_CHANGELOGO_STRING = "changelogo_string";
    public static final String ACTION_UPLOAD_CONTACTS = "upload_contacts";
    public static final String ACTION_TIMELINE = "timeline";
    public static final String ACTION_GET_COMMENT = "get_comment";
    public static final String ACTION_PUB_COMMENT = "pub_comment";
    public static final String ACTION_PUBLISH = "publish";



    public static final String PATH_CACHE_ROOT= Environment.getExternalStorageDirectory()+"/Android/data/"+PACKAGENAME;
    public static final String PATH_CACHE_ROOT_CACHE= PATH_CACHE_ROOT+"/cache";


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
    public static void cacheuserinfo2(Context context,String nickname,String text){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_NICKNAME, nickname);
        e.putString(KEY_TEXT, text);

        e.commit();
    }
    public static String getCachedtext(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TEXT, null);
    }
    public static String getCachednickname(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_NICKNAME, null);
    }
    public static void cachelogo(Context context, Bitmap logo){
        if(logo==null){
            SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
            e.putString(STRING_LOGO, KEY_NULL);
            e.commit();
        }else {
            SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
            e.putString(STRING_LOGO, Bitmap_String.convertIconToString(logo));
            e.commit();
        }
    }

    public static void CacheBitmaptoSD(Bitmap bitmap,String name,Result result){


                try {

                    File file = new File(PATH_CACHE_ROOT);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    file = new File(PATH_CACHE_ROOT_CACHE);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String fileName = PATH_CACHE_ROOT_CACHE + "/" + name;
                    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    result.OnSuccess();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    result.OnFail();
                }



    }

    public static Bitmap getCachedlogo(Context context){

        return Bitmap_String.convertStringToIcon(context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(STRING_LOGO, null));
    }

    public static interface Result{
        void OnSuccess();
        void OnFail();
    }


}
