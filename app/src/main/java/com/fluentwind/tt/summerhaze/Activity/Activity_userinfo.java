package com.fluentwind.tt.summerhaze.Activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fluentwind.tt.summerhaze.Config.commontools;
import com.fluentwind.tt.summerhaze.Config.config;
import com.fluentwind.tt.summerhaze.R;
import com.fluentwind.tt.summerhaze.net.Changelogo;
import com.fluentwind.tt.summerhaze.net.Getinfo;
import com.fluentwind.tt.summerhaze.tools.Bitmap_String;
import com.fluentwind.tt.summerhaze.tools.MD5Tool;
import com.fluentwind.tt.summerhaze.tools.Savebitmap;
import com.fluentwind.tt.summerhaze.tools.StringtoHex;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Activity_userinfo extends AppCompatActivity {
    private Button button_logout;
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> dataList;
    private String[]text1={"用户名","昵称","性别","生日","城市","签名"};
    private String[]text2={"用户名","昵称","性别","生日","城市","签名"};
    private String username;
    private PopupWindow mPopWindow;
    private CircleImageView circleImageView_logo;
    private String string_logo;
    private Bitmap bitmap_logo;
    private Boolean getinfook=false;
    private Uri uri_uri;
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

                if(getinfook) {
                    i.putExtra(config.STRING_LOGO, string_logo);
                    i.putExtra(config.KEY_NICKNAME,text2[1].toString());
                    i.putExtra(config.KEY_TEXT,text2[5].toString());
                }

                setResult(RESULT_CANCELED, i);

                finish();
                overridePendingTransition(R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_right);
            }
        });


        button_logout=(Button)findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dialog();
                new commontools().dialog(Activity_userinfo.this, new commontools.Callback() {
                    @Override
                    public void ok() {
                        Intent i =new Intent(Activity_userinfo.this,MainActivity.class);
                        setResult(RESULT_OK, i);
                        config.cachelogo(Activity_userinfo.this,null);
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

                switch (position){
                    case 0:

                        break;
                    default:
                        changeinfo();
                        break;

                }

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
        circleImageView_logo=(CircleImageView)findViewById(R.id.image_user) ;
        circleImageView_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        bitmap_logo=config.getCachedlogo(Activity_userinfo.this);
        if( bitmap_logo!=null){
            circleImageView_logo.setImageBitmap(bitmap_logo);
            string_logo=Bitmap_String.convertIconToString(bitmap_logo);
        }

        getinfo();
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();

        Intent i =new Intent(Activity_userinfo.this,MainActivity.class);

        if(getinfook) {
            i.putExtra(config.STRING_LOGO, string_logo);
            i.putExtra(config.KEY_NICKNAME,text2[1].toString());
            i.putExtra(config.KEY_TEXT,text2[5].toString());
        }

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

                getinfook=true;
                if (logo!=null && !logo.equals("") && !logo.equals("null")) {

                    string_logo=StringtoHex.toStringHex(logo);
                    bitmap_logo=Bitmap_String.convertStringToIcon(string_logo);
                    circleImageView_logo.setImageBitmap(bitmap_logo);
                }
                for (int i=0;i<text1.length;i++)
                {

                    text2[i]=config.STRING_NOINFO;

                }

                if (!username.equals("") && !username.equals("null")){text2[0]=username;}
                if (!nickname.equals("") && !nickname.equals("null") ){text2[1]=nickname;}
                if (!nickname.equals("") && !nickname.equals("null") ){text2[3]=date;}
                if (!sex.equals("") && !sex.equals("null") ){text2[2]=sex;}
                if ( !text.equals("") && !text.equals("null")){text2[5]=text;}
                if (!city.equals("")  && !city.equals("null") ){text2[4]=city;}

                dataList.clear();
                for (int i=0;i<text1.length;i++)
                {
                    Map<String,Object>map=new HashMap<String,Object>();
                    map.put("text1",text1[i]);
                    map.put("text2",text2[i]);

                    dataList.add(map);
                }


                simpleAdapter.notifyDataSetChanged();//刷新界面
                Toast.makeText(Activity_userinfo.this, config.STRING_GETSUCCESS, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onWrong() {
                pd.dismiss();
                getinfook=false;
                Toast.makeText(Activity_userinfo.this, config.STRING_FAILTOGETINFO, Toast.LENGTH_LONG).show();
            }
        },new Getinfo.FailCallback(){
            @Override
            public void onFail() {
                pd.dismiss();
                getinfook=false;
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
    private void changeinfo(){
        Intent intent = new Intent(Activity_userinfo.this,Activity_changeinfo.class);
        for (int i=0;i<text1.length;i++)
        {

            intent.putExtra(text1[i].toString(),text2[i].toString());

        }

        startActivityForResult(intent, 5);

        overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 5:{

                if (resultCode == RESULT_OK) {
                    getinfo();


                } else if (resultCode == RESULT_CANCELED) {

                }
                break;
            }
            case 1:{

                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    /*//Log.e("uri", uri.toString());
                    ContentResolver cr = this.getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                *//* 将Bitmap设定到ImageView *//*
                        circleImageView_logo.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e("Exception", e.getMessage(),e);
                    }*/
                    crop(uri);

                    uri_uri=uri;
            } else if (resultCode == RESULT_CANCELED) {

                }
                break;
            }
            case 2:{
                if (data != null) {
                    String logo;
                    bitmap_logo = data.getParcelableExtra("data");
                    this.circleImageView_logo.setImageBitmap(bitmap_logo);
                    config.CacheBitmaptoSD(bitmap_logo, username+"logo", new config.Result() {
                        @Override
                        public void OnSuccess() {

                        }

                        @Override
                        public void OnFail() {

                        }
                    });
                    string_logo=Bitmap_String.convertIconToString(bitmap_logo);
                    config.cachelogo(Activity_userinfo.this,bitmap_logo);
                    logo=string_logo;
                    changelogo(logo);

                    Savebitmap.savebitmap(bitmap_logo,getCacheDir().getPath() ,"logo.PNG");
                }



                break;
            }


        }
    }
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 2);
    }


    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(Activity_userinfo.this).inflate(R.layout.popupwindow_logo, null);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应

        Button selectphoto=(Button)contentView.findViewById(R.id.button_selectphoto);
        selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });
        Button cancel=(Button)contentView.findViewById(R.id.button_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        Button view=(Button)contentView.findViewById(R.id.button_view);
        if(bitmap_logo==null ||bitmap_logo.equals(config.STRING_NULL))
        {
            view.setVisibility(View.INVISIBLE);
        }else
        {
            view.setVisibility(View.VISIBLE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();


                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile( new File(config.PATH_CACHE_ROOT_CACHE + "/" +username+"logo")), "image/*");
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Activity_userinfo.this, config.STRING_FAILTOOPENLOGO, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(Activity_userinfo.this).inflate(R.layout.popupwindow_logo, null);
        WindowManager.LayoutParams params= getWindow().getAttributes();
        params.alpha=0.5f;
        getWindow().setAttributes(params);
        mPopWindow.setFocusable(true);
        // 设置允许在外点击消失
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setAnimationStyle(R.style.popup_window_bottombar);

        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params= getWindow().getAttributes();
                params.alpha=1.0f;
                getWindow().setAttributes(params);
            }
        });
    }
    public void changelogo(String logo) {

        if (logo != null && logo != config.STRING_NULL && !logo.equals(config.STRING_NULL)) {
            new Changelogo(MD5Tool.md5(username), StringtoHex.str2HexStr(logo), new Changelogo.SuccessCallback() {
                @Override
                public void onSuccess() {

                    Toast.makeText(Activity_userinfo.this, config.STRING_SUCCESSTOCHANGELOGO, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onWrong() {

                    Toast.makeText(Activity_userinfo.this, config.STRING_FAILTOCHANGELOGO, Toast.LENGTH_LONG).show();
                }
            }, new Changelogo.FailCallback() {
                @Override
                public void onFail() {

                    Toast.makeText(Activity_userinfo.this, config.STRING_FAILTOCHANGELOGO, Toast.LENGTH_LONG).show();
                }
            });

        }
        else {
            Toast.makeText(Activity_userinfo.this, config.STRING_FAILTOCHANGELOGO, Toast.LENGTH_LONG).show();
        }
    }
}
