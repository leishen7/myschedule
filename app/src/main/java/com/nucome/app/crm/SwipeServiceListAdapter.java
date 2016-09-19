package com.nucome.app.crm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SwipeServiceListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ServiceInfo> serviceList;
    private String[] bgColors;

    public SwipeServiceListAdapter(Activity activity, List<ServiceInfo> serviceList) {
        this.activity = activity;
        this.serviceList = serviceList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.rate_bg);
    }

    @Override
    public int getCount() {
        return serviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.service_list_row, null);
        }
        TextView serviceName = (TextView) convertView.findViewById(R.id.serviceName);
        TextView newsDescription = (TextView) convertView.findViewById(R.id.serviceDescription);
        TextView provider = (TextView) convertView.findViewById(R.id.provider);
        TextView category = (TextView) convertView.findViewById((R.id.category));

        serviceName.setText(serviceList.get(position).getServiceName());
        newsDescription.setText(serviceList.get(position).getDescription());
        provider.setText(String.valueOf(serviceList.get(position).getProviderName()));
        category.setText(String.valueOf(serviceList.get(position).getCategory()));
        String color = bgColors[position % bgColors.length];
        serviceName.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }
}
