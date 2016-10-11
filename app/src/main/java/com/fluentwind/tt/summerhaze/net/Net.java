package com.fluentwind.tt.summerhaze.net;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/9/27.
 */

public class Net {
    private String url,result;
    private final String CHARSET="UTF-8";
    public void setUrl(String url) {
        this.url = url;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String getUrl() {
        return url;
    }
    public Net(String Url, final result result) {
        setUrl(Url);
        new AsyncTask<String,Void,String>(){


            @Override
            protected String doInBackground(String... params) {
                try {

                    URLConnection urlConnection=new URL(url).openConnection();
                    System.out.println("Request:"+urlConnection.getURL());
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),CHARSET));

                    String line = null;
                    StringBuffer result = new StringBuffer();
                    while((line=bufferedReader.readLine())!=null){
                        result.append(line);
                    }
                    if (result!=null){
                        setResult(result.toString());
                    }
                    System.out.println(getResult());

                    return getResult();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                result.Onsuccess(s);
            }
        }.execute();


    }

}


