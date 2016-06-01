package com.fluentwind.tt.summerhaze;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Activity_userinfo extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> dataList;
    private String[]text1={"用户名","性别","生日","签名"};
    private String[]text2={"用户名","性别","生日","签名"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("账户信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);
            }
        });



        listView=(ListView) findViewById(R.id.listView_info);
        dataList=new ArrayList<Map<String,Object>>();

        simpleAdapter=new SimpleAdapter(this,getDataList(),R.layout.listview_item,new String[]{"text1","text2"},new int[] {R.id.textView1,R.id.textView2});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState)
                {
                    case SCROLL_STATE_FLING:


                        break;
                    case SCROLL_STATE_IDLE:

                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:


                        break;

                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        finish();

        overridePendingTransition(R.anim.fragment_slide_in_from_left,
                R.anim.fragment_slide_out_to_right);
    }

    private List<Map<String,Object>> getDataList()
    {
        for (int i=0;i<text1.length;i++)
        {
            Map<String,Object>map=new HashMap<String,Object>();
            map.put("text1",text1[i]);
            map.put("text2",text2[i]);
            dataList.add(map);
        }
        return dataList;
    }
}
