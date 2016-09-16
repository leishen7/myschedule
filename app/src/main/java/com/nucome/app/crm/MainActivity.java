package com.nucome.app.crm;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import com.nucome.app.cal.DateUtil;
import com.nucome.app.cal.Utility;
import com.nucome.app.weekview.BasicActivity;
import com.nucome.app.weekview.StartTimeComparator;
import com.nucome.app.weekview.UpdateEventActivity;
import com.nucome.app.weekview.WeekViewEvent;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.this.getClass().getSimpleName();
    List<WeekViewEvent> eventList;
    protected EventListAdapter adapter;
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context=getApplicationContext();
        eventList = Utility.readOneWeekEvents(context);
        Collections.sort(eventList,new StartTimeComparator());

        listView = (ListView) findViewById(R.id.eventListView);

        adapter = new EventListAdapter(this, eventList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                WeekViewEvent event = (WeekViewEvent) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), UpdateEventActivity.class);
                intent.putExtra(getString(R.string.EVENT_ID),Long.toString(event.getId()));
                intent.putExtra(getString(R.string.EVENT_NAME),event.getName());
                intent.putExtra(getString(R.string.EVENT_LOCATION),event.getLocation());
                intent.putExtra(getString(R.string.EVENT_DESCRIPTION),event.getDescription());
                String startTime= DateUtil.formatCalDate(event.getStartTime());
                intent.putExtra(getString(R.string.EVENT_START_TIME),startTime);
                String endTime= DateUtil.formatCalDate(event.getEndTime());
                intent.putExtra(getString(R.string.EVENT_END_TIME),endTime);
                intent.putExtra(getString(R.string.EVENT_IS_ALL_DAY),event.isAllDay());
                startActivity(intent);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.weekview:
                Intent intent1 = new Intent(this, BasicActivity.class);
                startActivity(intent1);
                return true;
            case R.id.setting:
                Intent intent2 = new Intent(this, RegisterActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
