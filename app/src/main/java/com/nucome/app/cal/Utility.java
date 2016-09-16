package com.nucome.app.cal;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;

import com.nucome.app.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lei on 2016-08-25.
 */
public class Utility {

    private static ArrayList<String> nameOfEvent = new ArrayList<String>();
    private static ArrayList<String> startDates = new ArrayList<String>();
    private static ArrayList<String> endDates = new ArrayList<String>();
    private static ArrayList<String> descriptions = new ArrayList<String>();

    private static final String DATE_TIME_FORMAT = "yyyy MMM dd, HH:mm:ss";
    private static String getDateTimeStr(int p_delay_min) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        if (p_delay_min == 0) {
            return sdf.format(cal.getTime());
        } else {
            Date l_time = cal.getTime();
            l_time.setMinutes(l_time.getMinutes() + p_delay_min);
            return sdf.format(l_time);
        }
    }
    public static String getDateTimeStr(String p_time_in_millis) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date l_time = new Date(Long.parseLong(p_time_in_millis));
        return sdf.format(l_time);
    }

    public static List<WeekViewEvent> readCalEvents(Context context) {
        List<WeekViewEvent> eventList=new ArrayList<WeekViewEvent>();
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation", CalendarContract.Calendars._ID,"allDay","deleted","eventTimezone"}, null,
                        //null, null,
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
     //   String CNames[] = new String[cursor.getCount()];
/*
        Calendar currentTime = Calendar.getInstance();

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            System.out.println(cursor.getColumnName(i) + "="
                    + cursor.getString(i));
        }

*/

        for (int i = 0; i < cursor.getCount(); i++) {
            long startTime=Long.parseLong(cursor.getString(3));
            /*
            if(currentTime.getTimeInMillis()- startTime>1000*60*60*24*2){
                continue;//pass two days ago events
            }*/
            WeekViewEvent event=new WeekViewEvent();
            event.setName(cursor.getString(1));
           // nameOfEvent.add(cursor.getString(1));
            if(cursor.getString(3)==null || cursor.getString(4)==null){
                cursor.moveToNext();
                continue;
            }

            event.setStartTime(getCal(Long.parseLong(cursor.getString(3))));
           // startDates.add(getDate(Long.parseLong(cursor.getString(3))));
        //    endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            event.setEndTime(getCal(Long.parseLong(cursor.getString(4))));
            event.setLocation(cursor.getString(5));
            event.setDescription(cursor.getString(2));
            event.setId(Long.parseLong(cursor.getString(6)));
            if(cursor.getString(7).equals("1"))
                event.setAllDay(true);
            else
                event.setAllDay(false);
             eventList.add(event);
          //  descriptions.add(cursor.getString(2));
          //  CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        }
        return eventList;
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static Calendar getCal(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return calendar;
    }

    /**
     * Checks if an event falls into a specific year and month.
     * @param event The event to check for.
     * @param year The year.
     * @param month The month.
     * @return True if the event matches the year and month.
     */
    public static boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }


    public static List<WeekViewEvent> readTodayEvents(Context context) {
        List<WeekViewEvent> eventList=new ArrayList<WeekViewEvent>();
        String[] columns={"calendar_id", "title", "description",
                "dtstart", "dtend", "eventLocation", CalendarContract.Calendars._ID,"allDay"};
        String selection = "((" + CalendarContract.Events.DTSTART
                + " >= ?) AND (" + CalendarContract.Events.DTEND + " <= ?))";
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, 0);
        time.set(Calendar.MINUTE, 0);
        String dtStart = Long.toString(time.getTimeInMillis());
        Calendar endTime = (Calendar) time.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        String dtEnd = Long.toString(endTime.getTimeInMillis());
        String[] selectionArgs = new String[] { dtStart, dtEnd };
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        columns,selection, selectionArgs, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            long startTime=Long.parseLong(cursor.getString(3));
            /*
            if(currentTime.getTimeInMillis()- startTime>1000*60*60*24*2){
                continue;//pass two days ago events
            }*/
            WeekViewEvent event=new WeekViewEvent();
            event.setName(cursor.getString(1));
            // nameOfEvent.add(cursor.getString(1));
            if(cursor.getString(3)==null || cursor.getString(4)==null){
                cursor.moveToNext();
                continue;
            }

            event.setStartTime(getCal(Long.parseLong(cursor.getString(3))));
            // startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            //    endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            event.setEndTime(getCal(Long.parseLong(cursor.getString(4))));
            event.setLocation(cursor.getString(5));
            event.setDescription(cursor.getString(2));
            event.setId(Long.parseLong(cursor.getString(6)));
            if(cursor.getString(7).equals("1"))
                event.setAllDay(true);
            else
                event.setAllDay(false);
            eventList.add(event);
            //  descriptions.add(cursor.getString(2));
            //  CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        }
        return eventList;
    }


    public static List<WeekViewEvent> readOneWeekEvents(Context context) {
        List<WeekViewEvent> eventList=new ArrayList<WeekViewEvent>();
        String[] columns={"calendar_id", "title", "description",
                "dtstart", "dtend", "eventLocation", CalendarContract.Calendars._ID,"allDay"};
        String selection = "((" + CalendarContract.Events.DTSTART
                + " >= ?) AND (" + CalendarContract.Events.DTEND + " <= ?))";
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, 0);
        time.set(Calendar.MINUTE, 0);
        String dtStart = Long.toString(time.getTimeInMillis());
        Calendar endTime = (Calendar) time.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        endTime.add(Calendar.DAY_OF_MONTH, 7);
        String dtEnd = Long.toString(endTime.getTimeInMillis());
        String[] selectionArgs = new String[] { dtStart, dtEnd };
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        columns,selection, selectionArgs, null);
                       // null,selection, selectionArgs, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            long startTime=Long.parseLong(cursor.getString(3));
            /*
            if(currentTime.getTimeInMillis()- startTime>1000*60*60*24*2){
                continue;//pass two days ago events
            }*/
            WeekViewEvent event=new WeekViewEvent();
            event.setName(cursor.getString(1));
            // nameOfEvent.add(cursor.getString(1));
            if(cursor.getString(3)==null || cursor.getString(4)==null){
                cursor.moveToNext();
                continue;
            }

            event.setStartTime(getCal(Long.parseLong(cursor.getString(3))));
            // startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            //    endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            event.setEndTime(getCal(Long.parseLong(cursor.getString(4))));
            event.setLocation(cursor.getString(5));
            event.setDescription(cursor.getString(2));
            event.setId(Long.parseLong(cursor.getString(6)));
            if(cursor.getString(7).equals("1"))
                event.setAllDay(true);
            else
                event.setAllDay(false);
            eventList.add(event);
            //  descriptions.add(cursor.getString(2));
            //  CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        }
        return eventList;
    }

}