package com.example.academictracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import androidx.annotation.Nullable;

public class tenth_calc_service extends Service
{
    private final MyLocalBinder mlb = new MyLocalBinder();

    public class MyLocalBinder extends Binder
    {
        tenth_calc_service getService()
        {
            return tenth_calc_service.this;
        }
    }
    public tenth_calc_service()
    {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent i)
    {
        return mlb;
    }
    public String total(int[] a)
    {
        int marks=0;
        for (int j : a)
            marks += j;
        return String.valueOf(marks);
    }
    public String percentage(int[] a)
    {
        int marks=0;
        for (int j : a)
            marks += j;
        double percentageval = (marks/a.length);
        return String.format("%.2f",percentageval);
    }

    public String percentagefor1200(int[] a)
    {
        int marks=0;
        for (int j : a)
            marks += j;
        double percentageval = (marks/(12));
        return String.format("%.2f",percentageval);
    }
}