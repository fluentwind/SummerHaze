package com.fluentwind.tt.summerhaze.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.fluentwind.tt.summerhaze.R;
import com.fluentwind.tt.summerhaze.net.Callback;
import com.fluentwind.tt.summerhaze.net.Tuling;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/2/24.
 */

public class Fragment_chat extends Fragment implements View.OnClickListener{
    private Button button_tuling;

    private EditText editText_tiling;
    private ListView listView;
    private TulingAdapter tulingAdapter;
    private List<TulingData> list;
    private android.os.Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuling, container, false);
        list=new ArrayList<TulingData>();

        tulingAdapter=new TulingAdapter(view.getContext(),list);
        editText_tiling= (EditText) view.findViewById(R.id.editText_tuling);
        editText_tiling.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode ==KeyEvent.KEYCODE_ENTER) {

                        button_tuling.callOnClick();

                }


                return false;
            }
        });
        button_tuling= (Button) view.findViewById(R.id.button_tuling);
        listView= (ListView) view.findViewById(R.id.listView_tuling);
        listView.setAdapter(tulingAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm =  (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(),
                            0);
                }
            }
        });
        button_tuling.setOnClickListener(this);
        handler=new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        tulingAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_tuling:
                if(!editText_tiling.getText().toString().equals("")) {
                    TulingData tulingData=new TulingData(editText_tiling.getText().toString(),TulingData.Right);
                    list.add(list.size(),tulingData);

                    tulingAdapter.notifyDataSetChanged();

                    new Tuling(editText_tiling.getText().toString(), new Callback() {
                        @Override
                        public void SuccessCallback(String result) {
                            TulingData tulingData=new TulingData(result,TulingData.Left);
                            //list.add(tulingData);
                            list.add(list.size(),tulingData);
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            //System.out.println(result);


                        }

                        @Override
                        public void FailCallback() {
                            System.out.println("err");
                        }
                    });
                    editText_tiling.setText("");
                }
                break;

        }


    }
}
