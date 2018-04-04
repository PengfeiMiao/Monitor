package com.example.a20182.monitor;

/**
 * Created by 20182 on 2018/3/30.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SelectAdapter extends BaseAdapter {
    private List<Application> apps;
    private LayoutInflater inflater;

    public SelectAdapter(Context context, List<Application> infos) {
        this.apps = infos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.select_item, null);
            holder.icon = convertView.findViewById(R.id.app_image);
            holder.name = convertView.findViewById(R.id.app_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setImageDrawable(apps.get(position).getIcon());
        holder.name.setText(apps.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
    }
}
