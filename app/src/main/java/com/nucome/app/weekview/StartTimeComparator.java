package com.nucome.app.weekview;

import java.util.Comparator;

/**
 * Created by lei on 2016-09-05.
 */
public class StartTimeComparator implements Comparator<WeekViewEvent>
{
    @Override
    public int compare(WeekViewEvent o1, WeekViewEvent o2) {
        if(o1.getStartTime().getTimeInMillis()>o2.getStartTime().getTimeInMillis())
            return 1;
        else if(o1.getStartTime().getTimeInMillis()<o2.getStartTime().getTimeInMillis())
            return -1;

        return 0;
    }
}
