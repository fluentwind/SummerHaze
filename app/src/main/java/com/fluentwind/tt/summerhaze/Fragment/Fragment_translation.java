package com.fluentwind.tt.summerhaze.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fluentwind.tt.summerhaze.R;
import com.fluentwind.tt.summerhaze.net.Net;
import com.fluentwind.tt.summerhaze.net.result;
import com.fluentwind.tt.summerhaze.tools.JsonGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/4.
 */

public class Fragment_translation extends Fragment {
    final String URL="http://fanyi.youdao.com/openapi.do?keyfrom=netapp&key=1048932508&type=data&doctype=json&version=1.1&q=";
    private EditText editText;
    private Button button;
    private TextView textView;
    private Map<String,String> map=new HashMap<String,String>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translation, container, false);
        button=(Button)view.findViewById(R.id.button_translation);
        editText=(EditText)view.findViewById(R.id.editText_word);
       textView=(TextView)view.findViewById(R.id.textView_translation);

        map.clear();
        map.put("错误代码","errorCode");
        map.put("查词","query");
        map.put("翻译","translation");




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Net net =new Net(URL + editText.getText().toString(), new result() {
                    @Override
                    public void Onsuccess(String string) {
                        JsonGet jsonGet=new JsonGet(string,"translation");
                        jsonGet.showmap();
                        textView.setText(jsonGet.getSingleresult());
                    }
                });
            }
        });

        return view;


    }
}
