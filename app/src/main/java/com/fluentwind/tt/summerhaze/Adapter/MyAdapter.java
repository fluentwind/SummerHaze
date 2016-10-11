package com.fluentwind.tt.summerhaze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fluentwind.tt.summerhaze.R;
import com.fluentwind.tt.summerhaze.tools.VideoInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class MyAdapter extends BaseAdapter{

    private List<VideoInfo> lists;
    private Context context;


    public MyAdapter(List<VideoInfo> lists,Context context) {
        this.lists = lists;
        this.context = context;

    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.videolist_grid_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.text_videoinfo);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_video);
            holder.textView.setText(lists.get(position).getInfo());
            holder.imageView.setImageBitmap(lists.get(position).getBitmap());
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
            holder.textView.setText(lists.get(position).getInfo());
            holder.imageView.setImageBitmap(lists.get(position).getBitmap());
        }
        return convertView;
    }
    private static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

}
