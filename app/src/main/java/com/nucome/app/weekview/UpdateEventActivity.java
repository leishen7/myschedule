package com.nucome.app.weekview;

import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nucome.app.cal.DateUtil;
import com.nucome.app.crm.MainActivity;
import com.nucome.app.crm.R;

import java.util.Calendar;
import java.util.TimeZone;


public class UpdateEventActivity extends AppCompatActivity {
    private TextView nameEditText;
    private TextView locationEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private EditText descriptionEditText;

    private String startTime;
    private String endTime;

    private Calendar startTimeCal;
    private Calendar endTimeCal;

    ImageButton imageButtonStartTime;
    ImageButton imageButtonEndTime;
  //  private TimePicker timePicker;
  //  Button buttonStartTime;

    private Button updateButton;
    private Button deleteButton;

    private String eventId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        nameEditText=(TextView) findViewById(R.id.nameEditText);
        locationEditText=(TextView) findViewById(R.id.locationEditText);
        startTimeEditText=(EditText) findViewById(R.id.startTimeEditText);
        endTimeEditText=(EditText) findViewById(R.id.endTimeEditText);

        descriptionEditText=(EditText) findViewById(R.id.descriptionEditText);
   //     buttonStartTime=(Button) findViewById(R.id.buttonStartTime);

        final Intent intent = getIntent();
        if (intent != null ) {
            eventId=intent.getStringExtra(getString(R.string.EVENT_ID));
            nameEditText.setText(intent.getStringExtra(getString(R.string.EVENT_NAME)));
            locationEditText.setText(intent.getStringExtra(getString(R.string.EVENT_LOCATION)));
            startTime=intent.getStringExtra(getString(R.string.EVENT_START_TIME));
            startTimeEditText.setText(startTime);
            endTime=intent.getStringExtra(getString(R.string.EVENT_END_TIME));
            endTimeEditText.setText(endTime);
            descriptionEditText.setText(intent.getStringExtra(getString(R.string.EVENT_DESCRIPTION)));
        }

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

                TimePickerDialog tpd = new TimePickerDialog(UpdateEventActivity.this,
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

                TimePickerDialog tpd = new TimePickerDialog(UpdateEventActivity.this,
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

                TimePickerDialog tpd = new TimePickerDialog(UpdateEventActivity.this,
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

                TimePickerDialog tpd = new TimePickerDialog(UpdateEventActivity.this,
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





        updateButton = (Button) findViewById(R.id.submitUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(startTimeEditText.getText().toString().equals(startTime) &&
                        endTimeEditText.getText().toString().equals(endTime)   ){
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

                   // values.put (CalendarContract.Events.TITLE, nameEditText.getText().toString());
                    values.put (CalendarContract.Events.DESCRIPTION, descriptionEditText.getText().toString());
                  //  values.put(CalendarContract.Events.EVENT_LOCATION,locationEditText.getText().toString());
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
                        int count = cr.update(CalendarContract.Events.CONTENT_URI, values, CalendarContract.Events._ID + " = "+eventId, null);
                       // int count =getContentResolver().update(eventUri, values, null,         null);
                       // int count =getContentResolver().delete(eventUri, null, null);
                        System.out.println(count);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }catch(SecurityException se){
                        se.printStackTrace();
                    }
                }
            }

        });


        deleteButton = (Button) findViewById(R.id.submitDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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


                    Uri eventUri = ContentUris.withAppendedId(calenderContentUri, Long.parseLong(eventId));


                    // values.put (CalendarContract.Events.TITLE, nameEditText.getText().toString());
                    values.put (CalendarContract.Events._ID, eventId);
                    //  values.put(CalendarContract.Events.EVENT_LOCATION,locationEditText.getText().toString());
                    values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

                    try {
                       // int count = cr.update(CalendarContract.Events.CONTENT_URI, values, CalendarContract.Events._ID + " = "+eventId, null);
                        // int count =getContentResolver().update(eventUri, values, null,         null);
                        int count =getContentResolver().delete(eventUri, null, null);
                        System.out.println(count);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }catch(SecurityException se){
                        se.printStackTrace();
                    }
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
                Intent intent1 = new Intent(this, UpdateEventActivity.class);
                startActivity(intent1);
                return true;
            case R.id.setting:
                Intent intent2 = new Intent(this, UpdateEventActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}