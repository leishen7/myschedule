package com.nucome.app.weekview;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nucome.app.cal.DateUtil;
import com.nucome.app.cal.Utility;
import com.nucome.app.crm.MainActivity;
import com.nucome.app.crm.R;
import com.nucome.app.crm.SearchServiceActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class AddEventActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText locationEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private EditText descriptionEditText;
    private EditText providerEditText;

    private Button searchServiceButton;

    private Button createButton;

    private String serviceId;
    private String startTime;
    private String endTime;

    private Calendar startTimeCal;
    private Calendar endTimeCal;

    ImageButton imageButtonStartTime;
    ImageButton imageButtonEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        nameEditText=(EditText) findViewById(R.id.nameEditText);
        locationEditText=(EditText) findViewById(R.id.locationEditText);
        startTimeEditText=(EditText) findViewById(R.id.startTimeEditText);
        endTimeEditText=(EditText) findViewById(R.id.endTimeEditText);
        descriptionEditText=(EditText) findViewById(R.id.descriptionEditText);
        providerEditText=(EditText) findViewById(R.id.providerEditText);

        final Intent intent = getIntent();
        if (intent != null ) {
            nameEditText.setText(intent.getStringExtra(getString(R.string.EVENT_NAME)));
            locationEditText.setText(intent.getStringExtra(getString(R.string.EVENT_LOCATION)));
            serviceId= intent.getStringExtra(getString(R.string.SERVICE_ID));
            startTime=intent.getStringExtra(getString(R.string.EVENT_START_TIME));
            startTimeEditText.setText(startTime);
            endTime=intent.getStringExtra(getString(R.string.EVENT_END_TIME));
            endTimeEditText.setText(endTime);
            descriptionEditText.setText(intent.getStringExtra(getString(R.string.EVENT_DESCRIPTION)));
            providerEditText.setText(intent.getStringExtra(getString(R.string.SERVICE_PROVIDER)));
        }

        createButton = (Button) findViewById(R.id.submitAdd);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startTimeEditText.getText().equals(intent.getStringExtra(getString(R.string.EVENT_START_TIME))) &&
                        endTimeEditText.getText().equals(intent.getStringExtra(getString(R.string.EVENT_END_TIME)))   ){
                    Toast.makeText(getApplicationContext(), "时间没有改变", Toast.LENGTH_SHORT).show();
                }else{
                    ContentResolver cr = getContentResolver();
                    ContentValues values = new ContentValues();

                    Uri calenderContentUri;
                    if(Build.VERSION.SDK_INT >= 8)
                    {
                        calenderContentUri = Uri.parse("content://com.android.calendar/events");
                    }
                    else
                    {
                        calenderContentUri = Uri.parse("content://calendar/events");
                    }


                    Uri eventUri = ContentUris.withAppendedId(calenderContentUri, 1);
                    values.put(CalendarContract.Events.CALENDAR_ID, 1);
                    values.put (CalendarContract.Events.TITLE, nameEditText.getText().toString());
                    values.put (CalendarContract.Events.DESCRIPTION, descriptionEditText.getText().toString());
                    values.put(CalendarContract.Events.EVENT_LOCATION,locationEditText.getText().toString());
                    values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
                    try{
                        startTimeCal=DateUtil.parseDateTime(startTimeEditText.getText().toString());
                        endTimeCal=DateUtil.parseDateTime(endTimeEditText.getText().toString());

                        long startMillis= startTimeCal.getTimeInMillis();
                        System.out.println(startTimeEditText.getText().toString()+"---->"+startMillis);
                        long endMillis= endTimeCal.getTimeInMillis();
                        System.out.println(endTimeEditText.getText().toString()+"---->"+endMillis);
                        values.put (CalendarContract.Events.DTSTART, startMillis);//startTimeEditText.getText().toString());
                        values.put (CalendarContract.Events.DTEND,endMillis);// endTimeEditText.getText().toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        //  int count = cr.update(CalendarContract.Events.CONTENT_URI, values, CalendarContract.Events._ID + " = 1", null);
                        // int count =getContentResolver().update(eventUri, values, null,         null);
                        //  eventUri=CalendarContract.Events.CONTENT_URI;
                        //eventUri = ContentUris.withAppendedId(calenderContentUri, 1);
                        //    Uri insertedUri =getContentResolver().insert(eventUri, values);
                        Uri insertedUri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                        System.out.println(insertedUri);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }catch(SecurityException se){
                        se.printStackTrace();
                    }
                }
            }

        });






        try {
            startTimeCal = DateUtil.parseDateTime(startTimeEditText.getText().toString());
            endTimeCal = DateUtil.parseDateTime(endTimeEditText.getText().toString());
        }catch(Exception e) {
            e.printStackTrace();
        }

        imageButtonStartTime=(ImageButton) findViewById(R.id.imageButtonStartTime);
        imageButtonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                try {
                    mHour = startTimeCal.get(Calendar.HOUR_OF_DAY);
                    mMinute = startTimeCal.get(Calendar.MINUTE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TimePickerDialog tpd = new TimePickerDialog(AddEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                startTimeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                startTimeCal.set(Calendar.MINUTE, minute);
                                startTimeEditText.setText(DateUtil.formatCalDate(startTimeCal));
                                // Toast.makeText(WeekviewMainActivity.this, "hour:"+hourOfDay+" ;minute:"+minute, Toast.LENGTH_LONG).show();
                            }
                        }, mHour, mMinute, false);
                tpd.show();
            }

        });

        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                try {
                    mHour = startTimeCal.get(Calendar.HOUR_OF_DAY);
                    mMinute = startTimeCal.get(Calendar.MINUTE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TimePickerDialog tpd = new TimePickerDialog(AddEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                startTimeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                startTimeCal.set(Calendar.MINUTE, minute);
                                startTimeEditText.setText(DateUtil.formatCalDate(startTimeCal));
                                // Toast.makeText(WeekviewMainActivity.this, "hour:"+hourOfDay+" ;minute:"+minute, Toast.LENGTH_LONG).show();
                            }
                        }, mHour, mMinute, false);
                tpd.show();
            }

        });





        imageButtonEndTime=(ImageButton) findViewById(R.id.imageButtonEndTime);

        imageButtonEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                try {
                    mHour = endTimeCal.get(Calendar.HOUR_OF_DAY);
                    mMinute = endTimeCal.get(Calendar.MINUTE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TimePickerDialog tpd = new TimePickerDialog(AddEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                endTimeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                endTimeCal.set(Calendar.MINUTE, minute);
                                endTimeEditText.setText(DateUtil.formatCalDate(endTimeCal));
                                // Toast.makeText(WeekviewMainActivity.this, "hour:"+hourOfDay+" ;minute:"+minute, Toast.LENGTH_LONG).show();
                            }
                        }, mHour, mMinute, false);




                tpd.show();
            }

        });

        endTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                try {
                    mHour = endTimeCal.get(Calendar.HOUR_OF_DAY);
                    mMinute = endTimeCal.get(Calendar.MINUTE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TimePickerDialog tpd = new TimePickerDialog(AddEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                endTimeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                endTimeCal.set(Calendar.MINUTE, minute);
                                endTimeEditText.setText(DateUtil.formatCalDate(endTimeCal));
                                // Toast.makeText(WeekviewMainActivity.this, "hour:"+hourOfDay+" ;minute:"+minute, Toast.LENGTH_LONG).show();
                            }
                        }, mHour, mMinute, false);




                tpd.show();
            }

        });


        searchServiceButton = (Button) findViewById(R.id.searchServiceButton);
        searchServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SearchServiceActivity.class);
                intent.putExtra(getString(R.string.EVENT_START_TIME),startTime);
                intent.putExtra(getString(R.string.EVENT_END_TIME),endTime);
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
        switch (id){
            case R.id.home:
                Intent intent = new Intent(this, BasicActivity.class);
                startActivity(intent);
                return true;
            case R.id.service:
                Intent intent1 = new Intent(this, AddEventActivity.class);
                startActivity(intent1);
                return true;
            case R.id.setting:
                Intent intent2 = new Intent(this, AddEventActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}