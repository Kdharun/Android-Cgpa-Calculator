package com.example.academictracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.IOException;

public class insertdbservice extends Service
{
  //  SQLiteDatabase mydatabase;
    public insertdbservice()
    {
    }
    public void onCreate()
    {
        super.onCreate();
    }

    public int onStartCommand(Intent intent,int flags,int startID)
    {
       // String s = intent.getExtras().getString("namedb");
    //    mydatabase = openOrCreateDatabase("personal_details.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
       DataBaseHelper dbhelp = new DataBaseHelper(this);
       // boolean b = dbhelp.addOne(s);
        boolean b = false;
        try
        {
            b = dbhelp.addrowpd(this,intent);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
       // Toast.makeText(this,"Success : "+b,Toast.LENGTH_SHORT).show();
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}