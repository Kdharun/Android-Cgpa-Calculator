package com.example.academictracker;


import static com.example.academictracker.R.*;

import android.app.DatePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;


public class Personal_fragment extends Fragment implements View.OnClickListener
{

    Button imgupld,updateprof;
    Uri simguri;
    final Calendar myCalendar= Calendar.getInstance();
    EditText editTextdob,editTextname,editTextemail,editTextphone,editTextaddr;
    int flag = 0;
    ArrayAdapter<String> dropdown;
    byte[] imgbytearray;
    Toolbar toolb;

    AutoCompleteTextView autoCompleteTextView;
    private static int RESULT_LOAD_IMAGE = 1;

    public Personal_fragment()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);


    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_personal, container,false);
        getActivity().setTitle("Your Title");

        autoCompleteTextView = rootView.findViewById(R.id.autocomtextgender);
        Resources res = getResources();
        String[] gender = res.getStringArray(R.array.gender);

        dropdown = new ArrayAdapter<>(getActivity(),R.layout.dropdown_item,gender);
        autoCompleteTextView.setAdapter(dropdown);
        editTextphone = (EditText) rootView.findViewById(id.texteditphone);
        editTextaddr = (EditText) rootView.findViewById(id.texteditaddress);
        editTextemail = (EditText) rootView.findViewById(R.id.texteditemail);
        editTextname = (EditText) rootView.findViewById(R.id.texteditname);
        editTextdob=(EditText) rootView.findViewById(R.id.texteditdob);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        editTextdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        updateprof = (Button) rootView.findViewById(id.updateprofbtn);
        updateprof.setOnClickListener(this);
        imgupld = (Button) rootView.findViewById(id.imgpickbtn);
        imgupld.setOnClickListener(this);


        readFromDatabase();
        return rootView;

    }

    public void readFromDatabase()
    {
        Intent i = new Intent(getActivity(),readdbservice.class);
        ResultReceiver rr = new MyResultReceiver(new Handler());

        i.putExtra("receiver",rr);
        getActivity().startService(i);
        //To transfer name to main fragment (Inter fragment communication)
        String s = readnameonly();
        persfraglistener pl = (persfraglistener) getActivity();
        pl.passdetails(s);

    }
    public String readnameonly()
    {

        DataBaseHelper dbhelp = new DataBaseHelper(getActivity());
        String mname = dbhelp.readnamefromdb();
        dbhelp.close();
        return mname;
    }
    private void updateLabel()
    {
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editTextdob.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v)
    {


        if(v.getId()== id.imgpickbtn)
        {
            flag = 1;
          //  Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent i = new Intent();
            persfraglistener pl = (persfraglistener) getActivity();
            pl.passintentfromfrag(i, RESULT_LOAD_IMAGE);
        }
        if(v.getId()==id.updateprofbtn)
        {
          /*  String s1 = editTextname.getText().toString();
            persfraglistener pl = (persfraglistener) getActivity();
            pl.passdetails(s1); */

            String s = editTextname.getText().toString();
            Intent i = new Intent(getActivity(), insertdbservice.class);

            i.putExtra("namedb",s);
            i.putExtra("dobdb",editTextdob.getText().toString());
            i.putExtra("genderdb",autoCompleteTextView.getText().toString());
            i.putExtra("emaildb",editTextemail.getText().toString());
            i.putExtra("phonenodb",editTextphone.getText().toString());
            i.putExtra("addressdb",editTextaddr.getText().toString());
            if(flag==1)
             i.putExtra("imagedb",simguri.toString());
            else
                i.putExtra("imagedb","noimgupload");
            getActivity().startService(i);
            flag = 0;
        }
    }

    public void sendimguri(Uri selectedimg) throws IOException
    {
        simguri = selectedimg;
        InputStream iStream = null;
        try {
            iStream = getActivity().getContentResolver().openInputStream(selectedimg);
            imgbytearray = getBytes(iStream);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    public byte[] getBytes(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }




    ShapeableImageView simgbox;
    private class MyResultReceiver extends ResultReceiver
    {
        public MyResultReceiver(Handler handler)
        {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
        {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode == 100 && resultData !=null)
            {
                String sname = resultData.getString("Name");
                String sdob = resultData.getString("DOB");
                String sgender = resultData.getString("Gender");
                String semail = resultData.getString("Email");
                String sphone = resultData.getString("Phone");
                String saddr = resultData.getString("Address");
                Uri imguri =Uri.parse(resultData.getString("image"));
                String sid = resultData.getString("id");
                editTextname.setText(sname);
                editTextdob.setText(sdob);
                autoCompleteTextView.setText(sgender);
                editTextemail.setText(semail);
                editTextphone.setText(sphone);
                editTextaddr.setText(saddr);
             //for image
                simgbox = (ShapeableImageView) rootView.findViewById(R.id.shapeableImageView);
                simgbox.setImageURI(imguri);
                Resources res = getResources();
                String[] gender = res.getStringArray(R.array.gender);
                dropdown = new ArrayAdapter<>(getActivity(),R.layout.dropdown_item,gender);
                autoCompleteTextView.setAdapter(dropdown);

            }
        }
    }

}
