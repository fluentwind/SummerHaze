package com.fluentwind.tt.summerhaze.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fluentwind.tt.summerhaze.Activity.Activity_videoplayer;
import com.fluentwind.tt.summerhaze.Adapter.MyAdapter;
import com.fluentwind.tt.summerhaze.Config.config;
import com.fluentwind.tt.summerhaze.R;
import com.fluentwind.tt.summerhaze.tools.VideoInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenuItem;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnSwipeListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.utils.Log;

/**
 * Created by Administrator on 2016/5/22.
 */
public class Fragment_videolist extends Fragment implements IXListViewListener {

    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> dataList;
    private int[]icon={R.drawable.laoding_jietu,R.drawable.laoding_jietu,R.drawable.laoding_jietu,R.drawable.laoding_jietu,R.drawable.laoding_jietu};
    private String[]text={"location:xxx cam:xxxx","location:xxx cam:xxxx","","",""};
    private String[]cam={"","","","",""};
    private String[]info={"","","","",""};
    private String[]path={"rtsp://192.168.1.66:8554/123",
            "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov",
            "rtsp://192.168.1.66:554/h264TestSession",
            "rtsp://192.168.1.4:554/h264TestSession",
            "rtsp://192.168.1.4:8554/123"};
    private   Bitmap bm;
    private MyAdapter myAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;
    private List<VideoInfo> list=new ArrayList<VideoInfo>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videolist, container, false);


        //get data
        /*icon[1]=R.drawable.laoding_jietu;
        text[1]="username:user cam:xxxx";*/

        for (int i=0;i<path.length;i++) {
            cam[i]=path[i].substring(path[i].lastIndexOf("/")+1);
            info[i]=cam[i];
        }



        dataList=new ArrayList<Map<String,Object>>();

        simpleAdapter=new SimpleAdapter(getActivity(),getdata(),R.layout.videolist_grid_item,new String[]{"image","text"},new int[]{R.id.image_video,R.id.text_videoinfo});

        //myAdapter=new MyAdapter(getInfo(),getActivity());

        mListView = (PullToRefreshSwipeMenuListView)view. findViewById(R.id.listView);
        mListView.setAdapter(simpleAdapter);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(this);
        mHandler = new Handler();


        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {

                if((view instanceof ImageView) && (data instanceof Bitmap)) {
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }

                return false;
            }
        });




        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        // open


                        break;
                    case 1:
                        // delete
                        // delete(item);
                        dataList.remove(position);
                        simpleAdapter.notifyDataSetChanged();

                        //myAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // other setting
        // listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });







        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int i=position-1;


                Intent intent = new Intent(view.getContext(),Activity_videoplayer.class);
                //path="http://gslb.miaopai.com/stream/oxX3t3Vm5XPHKUeTS-zbXA__.mp4";
                //path="rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov";

                intent .putExtra("path", path[i]);
                intent.putExtra("cam",cam[i]);
                intent.putExtra("info",info[i]);
                intent .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent );

                getActivity().overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);





            }
        });



        onRefresh();

        return view;
    }
    private List<Map<String,Object>> getdata(){
        for(int j=0;j<1;j++) {
            for (int i = 0; i < icon.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("image", icon[i]);
                map.put("text", cam[i]);
                dataList.add(map);

            }
        }
        return dataList;
    }


    private List<VideoInfo> getInfo(){

        for (int i = 0; i < icon.length; i++) {

            VideoInfo phoneInfo = new VideoInfo(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.laoding_jietu), cam[i]);
            list.add(phoneInfo);

        }
        return list;
    }

    private void onLoad() {
        mListView.setRefreshTime(RefreshTime.getRefreshTime(getActivity().getApplicationContext()));
        mListView.stopRefresh();

        mListView.stopLoadMore();

    }

    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                RefreshTime.setRefreshTime(getActivity().getApplicationContext(), df.format(new Date()));
                onLoad();
            }

        }, 2000);

        dataList.clear();


                for (int i=0;i<path.length;i++) {
                    String name =cam[i]+"_b";

                    File file = new File(config.PATH_CACHE_ROOT_CACHE+ "/" + name);
                    Map<String, Object> map = new HashMap<String, Object>();


                    if (file.exists()) {
                        bm = BitmapFactory.decodeFile(config.PATH_CACHE_ROOT_CACHE+ "/" + name);

                        map.put("image", bm);
                        //System.out.println("yesyes");
                    }else{
                        map.put("image", icon[i]);

                        //System.out.println("nono");
                    }


                    map.put("text", cam[i]);
                    dataList.add(map);
                }

                simpleAdapter.notifyDataSetChanged();

                mListView.stopRefresh();

    }

    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 2000);
    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}
