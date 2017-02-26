package com.fluentwind.tt.summerhaze.net;

import android.os.AsyncTask;

import com.fluentwind.tt.summerhaze.Config.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/2/24.
 */

public class Tuling {
    public  Tuling(final String msg, final Callback callback){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {


                String strUrl = "key="+config.MSG_APIKEY+"&info=+"+msg;
                try {
                    HttpURLConnection urlConnection= (HttpURLConnection) new URL(config.MSG_URL).openConnection();
                    urlConnection.setConnectTimeout(2000);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(),config.CHARSET));

                    bufferedWriter.write(strUrl);
                    bufferedWriter.flush();

                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),config.CHARSET));
                    String line=null;
                    StringBuffer result=new StringBuffer();
                    while((line=bufferedReader.readLine())!=null){
                        result.append(line);
                    }
                    System.out.println(config.LF);
                    System.out.println("Result:"+result);
                    callback.SuccessCallback(Result(result.toString()));

                } catch (IOException e) {
                    e.printStackTrace();
                    callback.FailCallback();
                }
                return null;
            }
        }.execute();
    }
    private String Result(String string){
        try {
            JSONObject js=new JSONObject(string);
            int code=js.getInt("code");
            switch (code){
                case 100000:

                    return js.getString("text");

                case 200000:

                    break;
                default:
                    return "说些我听得懂的？";

            }

        } catch (JSONException e) {
            e.printStackTrace();
            return "出错啦！";
        }

        return "";
    }
}

