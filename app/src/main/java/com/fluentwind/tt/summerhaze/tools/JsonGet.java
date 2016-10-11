package com.fluentwind.tt.summerhaze.tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/4.
 */

public class JsonGet {
    final String STRING_ERRORCODE="errorCode";
    private Map<String,String> MAP=new HashMap<String,String>();

    private String Singleresult;

    public void setSingleresult(String singleresult) {
        Singleresult = singleresult;
    }

    public String getSingleresult() {
        return Singleresult;
    }

    public Map<String, String> getMAP() {
        return MAP;
    }
    public String Map2String(){
        if (!this.MAP.isEmpty()){
            StringBuffer stringbuffer=new StringBuffer();
            for (String v : MAP.values()) {
                stringbuffer.append(v);
                stringbuffer.append("\r\n");

            }
            return stringbuffer.toString();
        }

        return null;
    }
    public void ClearMAP() {
        this.MAP.clear();
    }

    public JsonGet(String json, String key){
        try {
            JSONObject jsonObject=new JSONObject(json);
            if (jsonObject.getString(key)!=null){
                setSingleresult(jsonObject.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void showmap(){
        for (String v : MAP.values()) {
            System.out.println("value= " + v);
        }
    }
    public JsonGet(String json, Map<String,String> mapkey){

        try {
            JSONObject jsonObject=new JSONObject(json);
            String string;
            Map m=new HashMap();
            for (String v : mapkey.values()) {
                string=jsonObject.getString(v);

                if (string!=null) {
                    m.put(v,string);
                }
            }
            if (!m.isEmpty()){
                MAP=m;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
