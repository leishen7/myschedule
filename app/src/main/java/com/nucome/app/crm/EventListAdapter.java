package com.nucome.app.crm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nucome.app.cal.DateUtil;
import com.nucome.app.weekview.WeekViewEvent;

import java.util.List;


public class EventListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<WeekViewEvent> eventList;
    private String[] bgColors;

    public EventListAdapter(Activity activity, List<WeekViewEvent> eventList) {
        this.activity = activity;
        this.eventList = eventList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.rate_bg);
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
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
            convertView = inflater.inflate(R.layout.event_list_row, null);
        }
        TextView eventName = (TextView) convertView.findViewById(R.id.eventName);
        TextView eventDescription = (TextView) convertView.findViewById(R.id.eventDescription);
        TextView eventLocation = (TextView) convertView.findViewById(R.id.eventLocation);
        TextView dateTime = (TextView) convertView.findViewById((R.id.dateTime));

        if(DateUtil.isToday(eventList.get(position).getStartTime())){
            dateTime.setText("今天 "+String.valueOf(DateUtil.formatTime(eventList.get(position).getStartTime()))+" 到 "+ String.valueOf(DateUtil.formatTime(eventList.get(position).getEndTime())));
        dateTime.setTextColor(Color.RED);

        }else if(DateUtil.isWithinDaysFuture(eventList.get(position).getStartTime(),1)){
            dateTime.setText("明天 "+String.valueOf(DateUtil.formatTime(eventList.get(position).getStartTime()))+" 到 "+ String.valueOf(DateUtil.formatTime(eventList.get(position).getEndTime())));
            dateTime.setTextColor(Color.BLUE);
            //dateTime.setBackgroundColor(activity.getApplicationContext().getResources().getColor(R.color.event_color_02));
        }else{
            dateTime.setText(DateUtil.formatDate(eventList.get(position).getStartTime())+" "+String.valueOf(DateUtil.formatTime(eventList.get(position).getStartTime()))+" 到 "+ String.valueOf(DateUtil.formatTime(eventList.get(position).getEndTime())));

        }
        eventName.setText(String.valueOf(eventList.get(position).getName()));
        if(eventList.get(position).getLocation()!=null) {
            eventLocation.setText(String.valueOf("地点："+eventList.get(position).getLocation()));
        }

        if(eventList.get(position).getDescription()!=null) {
            eventDescription.setText("备注："+String.valueOf(eventList.get(position).getDescription()));
        }
       // String color = bgColors[position % bgColors.length];
       // eventName.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }
}