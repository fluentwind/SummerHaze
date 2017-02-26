package com.fluentwind.tt.summerhaze.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fluentwind.tt.summerhaze.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2017/2/25.
 */

public class TulingAdapter extends BaseAdapter {
    private Context context;
    public TulingAdapter(Context context,List<TulingData> list) {
        this.list=list;
        this.context=context;
    }
    private List<TulingData> list;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;

        System.out.println(list.get(position).getPos());

        if(convertView==null||(viewHolder=(ViewHolder) convertView.getTag()).pos!=list.get(position).getPos()){
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater=LayoutInflater.from(context);

            if(list.get(position).getPos()==TulingData.Left){
                convertView=layoutInflater.inflate(R.layout.item_simple_left,null);
                viewHolder.pos=TulingData.Left;

            }else {
                convertView=layoutInflater.inflate(R.layout.item_simple_right,null);
                viewHolder.pos=TulingData.Right;
            }


            viewHolder.circleImageView= (CircleImageView) convertView.findViewById(R.id.image_user);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.textView_tuling_mes);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        if (null != list)
        {
            viewHolder.textView.setText(list.get(position).getString());
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            if(viewHolder.pos==TulingData.Right){
                viewHolder.circleImageView.setImageResource(R.drawable.user_man_add_512px);
            }else{
                viewHolder.circleImageView.setImageResource(R.drawable.ic_launcher);
            }

        }

        return convertView;
    }


    private static class ViewHolder{
        CircleImageView circleImageView;
        TextView textView;
        int pos;
    }

}

