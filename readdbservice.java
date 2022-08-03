package com.example.academictracker;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class readdbservice extends Service
{
  //  SQLiteDatabase mydatabase;
    public readdbservice()
    {
      super();
    }

    public void onCreate()
    {
        super.onCreate();
    }

    public int onStartCommand(Intent intent,int flags,int startID)
    {
     //   mydatabase = openOrCreateDatabase("personal_details.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);

        DataBaseHelper dbhelp = new DataBaseHelper(this);

        //using intent to store the values read from database and sending it back using result receiver
        Intent readintent = dbhelp.readfromdb(this);

        String check = readintent.getStringExtra("imgchecker");

        if(!check.equals("novalue"))
        {
            Bundle b = readintent.getExtras();
            ResultReceiver rr = intent.getParcelableExtra("receiver");
            if (b != null)
                rr.send(100, b);
        }
        dbhelp.close();

        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
