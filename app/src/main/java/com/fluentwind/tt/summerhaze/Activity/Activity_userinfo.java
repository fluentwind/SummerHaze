package com.fluentwind.tt.summerhaze.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fluentwind.tt.summerhaze.Config.commontools;
import com.fluentwind.tt.summerhaze.Config.config;
import com.fluentwind.tt.summerhaze.R;
import com.fluentwind.tt.summerhaze.net.Getinfo;
import com.fluentwind.tt.summerhaze.net.Login;
import com.fluentwind.tt.summerhaze.tools.MD5Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Activity_userinfo extends AppCompatActivity {
    private Button button_logou;
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> dataList;
    private String[]text1={"用户名","昵称","性别","生日","城市","签名"};
    private String[]text2={"用户名","昵称","性别","生日","城市","签名"};
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);


        Intent i =getIntent();
        username=i.getStringExtra("username");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("账户信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Activity_userinfo.this,MainActivity.class);

                setResult(RESULT_CANCELED, i);

                finish();
                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);
            }
        });


        button_logou=(Button)findViewById(R.id.button_logout);
        button_logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dialog();
                new commontools().dialog(Activity_userinfo.this, new commontools.Callback() {
                    @Override
                    public void ok() {
                        Intent i =new Intent(Activity_userinfo.this,MainActivity.class);
                        setResult(RESULT_OK, i);

                        finish();
                        overridePendingTransition(R.anim.fragment_slide_in_from_left,
                                R.anim.fragment_slide_out_to_right);

                    }

                });
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
        getinfo();
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i =new Intent(Activity_userinfo.this,MainActivity.class);

        setResult(RESULT_CANCELED, i);

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
    public void getinfo(){

        final ProgressDialog pd = ProgressDialog.show(Activity_userinfo.this, config.STRING_CONNECTING,config.STRING_GETTINGFROMSERVER);
        new Getinfo(MD5Tool.md5( username), new Getinfo.SuccessCallback(){
            @Override
            public void onSuccess(String nickname, String date, String sex, String text, String city, String logo) {
                pd.dismiss();

                Map<String,Object>map=new HashMap<String,Object>();
                for (int i=0;i<text1.length;i++)
                {
                    text2[i]=config.STRING_NOINFO;
                }



                if (username!= "null" && username!= "" && username!= null){text2[0]=username;}
                if (nickname!= "null" && nickname!= "" && nickname!= null ){text2[1]=nickname;}
                if (date!= "null" && date!= "" && date!= null){text2[3]=date;}
                if (sex!= "null" && sex!= "" && sex!= null ){text2[2]=sex;}
                if (text!= "null" && text!= "" && text!= null){text2[5]=text;}
                if (city!= "null" && city!= "" && city!= null ){text2[4]=city;}

                dataList.clear();
                for (int i=0;i<text1.length;i++)
                {
                    Map<String,Object>map1=new HashMap<String,Object>();
                    map1.put("text1",text1[i]);
                    map1.put("text2",text2[i]);

                    dataList.add(map1);
                }
                dataList.add(map);
                dataList.remove(text1.length);
                simpleAdapter.notifyDataSetChanged();//刷新界面
                Toast.makeText(Activity_userinfo.this, config.STRING_GETSUCCESS, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onWrong() {
                pd.dismiss();
                Toast.makeText(Activity_userinfo.this, config.STRING_FAILTOGETINFO, Toast.LENGTH_LONG).show();
            }
        },new Getinfo.FailCallback(){
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(Activity_userinfo.this, config.STRING_FAILTOGETINFO, Toast.LENGTH_LONG).show();
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.tool_refresh) {
            getinfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
