package com.jacksonw765.myapplication;

import android.app.Activity;
import android.app.FragmentManager;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jacks on 1/7/2018.
 */

public class DateTimePicker extends Activity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int hour = -1;
    private int minute = -1;

    public DateTimePicker() {

    }

    public void displayDatePicker(FragmentManager fragmentManager) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.show(fragmentManager, "displayDatePicker");
    }

    public void displayTimePicker(FragmentManager fragmentManager) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(fragmentManager, "displayTimePicker");
    }

    private Date getDateTimeScheduled() throws NullPointerException {
        if(year == -1 || month == -1 || day == -1 || hour == -1 || minute == -1)
            throw new NullPointerException("Date/Time variables not set");
        else {
            Calendar scheduledDate = Calendar.getInstance();
            scheduledDate.set(Calendar.YEAR, year);
            scheduledDate.set(Calendar.MONTH, month);
            scheduledDate.set(Calendar.DATE, day);
            scheduledDate.set(Calendar.MINUTE, minute);
            scheduledDate.set(Calendar.HOUR, hour);

            if(hour <= 11) {
                scheduledDate.set(Calendar.HOUR, hour);
                scheduledDate.set(Calendar.AM_PM, Calendar.AM);
            }
            if(hour == 12) {
                scheduledDate.set(Calendar.HOUR, 12);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 13) {
                scheduledDate.set(Calendar.HOUR, 1);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 14) {
                scheduledDate.set(Calendar.HOUR, 2);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 15) {
                scheduledDate.set(Calendar.HOUR, 3);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 16) {
                scheduledDate.set(Calendar.HOUR, 4);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 17) {
                scheduledDate.set(Calendar.HOUR, 5);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 18) {
                scheduledDate.set(Calendar.HOUR, 6);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 19) {
                scheduledDate.set(Calendar.HOUR, 7);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 20) {
                scheduledDate.set(Calendar.HOUR, 8);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 21) {
                scheduledDate.set(Calendar.HOUR, 9);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 22) {
                scheduledDate.set(Calendar.HOUR, 10);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 23) {
                scheduledDate.set(Calendar.HOUR, 11);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }
            if(hour == 24) {  //do to here
                scheduledDate.set(Calendar.HOUR, 11);
                scheduledDate.set(Calendar.AM_PM, Calendar.PM);
            }

            return scheduledDate.getTime();
        }
    }

    public int getCalculatedTime() {
        Date scheduledDate = getDateTimeScheduled();
        Date currentDate = new Date();
        long dif = ((scheduledDate.getTime() - currentDate.getTime())/1000)/60; //gives result in milli, divide my 1000 to get in seconds then 60 for minutes;
        System.out.println(dif);
        return (int)dif;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.day = dayOfMonth;
        this.year = year;
        this.month = monthOfYear;
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        this.hour = hourOfDay;
        this.minute = minute;
    }

}
