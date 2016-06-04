package com.fluentwind.tt.summerhaze.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.fluentwind.tt.summerhaze.Activity.Activity_videoplayer;
import com.fluentwind.tt.summerhaze.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/22.
 */
public class Fragment_videolist extends Fragment {
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> dataList;
    private int[]icon={R.drawable.laoding_jietu,R.drawable.laoding_jietu};
    private String[]text={"location:xxx cam:xxxx","location:xxx cam:xxxx"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videolist, container, false);


        //get data
        /*icon[1]=R.drawable.laoding_jietu;
        text[1]="username:user cam:xxxx";*/





        dataList=new ArrayList<Map<String,Object>>();
        gridView=(GridView)view.findViewById(R.id.gridView_video);
        simpleAdapter=new SimpleAdapter(getActivity(),getdata(),R.layout.videolist_grid_item,new String[]{"image","text"},new int[]{R.id.image_video,R.id.text_videoinfo});
        gridView.setAdapter(simpleAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int i=position;


                Intent intent = new Intent(view.getContext(),Activity_videoplayer.class);
                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_to_left);



               /* fragment_videoplayer=new Fragment_videoplayer();



                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                // API LEVEL 13
                fragmentTransaction.setCustomAnimations(
                        R.anim.fragment_slide_in_from_right,
                        R.anim.fragment_slide_out_to_right,
                        R.anim.fragment_slide_in_from_left,
                        R.anim.fragment_slide_out_to_left);


                fragmentTransaction.replace(R.id.frame,fragment_videoplayer);


                // 加入到BackStack中
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/


            }
        });





        return view;
    }
    private List<Map<String,Object>> getdata(){
        for(int j=0;j<1;j++) {
            for (int i = 0; i < icon.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("image", icon[i]);
                map.put("text", text[i]);
                dataList.add(map);

            }
        }
        return dataList;
    }







}
