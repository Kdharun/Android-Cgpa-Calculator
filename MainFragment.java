package com.example.academictracker;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

public class MainFragment extends Fragment //implements persfraglistener
{
        String name="",tenth="",twelfth="",cgpa = "";
        View rootview;
        ShapeableImageView mainimg;
        TextInputEditText nametxt,tenthtxt, twelfthtxt,cgpatxt;
        public MainFragment()
        {  }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            rootview = inflater.inflate(R.layout.fragment_main, container, false);
            nametxt = (TextInputEditText) rootview.findViewById(R.id.mainfragtexteditname);
            tenthtxt = (TextInputEditText) rootview.findViewById(R.id.textedit10th);
            twelfthtxt =(TextInputEditText) rootview.findViewById(R.id.textedit12th);
            cgpatxt = (TextInputEditText) rootview.findViewById(R.id.texteditCGPA);
            mainimg = (ShapeableImageView) rootview.findViewById(R.id.mainfragImageView);
            readndisplay();
            return rootview;
        }

    public void readndisplay()
    {
        DataBaseHelper dbhelpkun = new DataBaseHelper(getActivity());
        Bundle b = dbhelpkun.readformainfrag();
        if(b!=null)
        {
            name = b.getString("name");
            if(b.getString("tenth")==null)
                tenth = "";
            else
             tenth = b.getString("tenth")+" %";
            if(b.getString("twelfth")==null)
                twelfth = "";
            else
                twelfth = b.getString("twelfth")+" %";
        }
        nametxt.setText(name);
        tenthtxt.setText(tenth);
        twelfthtxt.setText(twelfth);

        DataBaseHelper2 dbhelpchan = new DataBaseHelper2(getActivity());
        double d = dbhelpchan.getcgpa();
        if(d!=0)
        {
            cgpa = String.format("%.2f",d);
        }
        cgpatxt.setText(cgpa);
        //for image
        Intent i1 = dbhelpkun.readfromdb(getActivity());
        String check = i1.getStringExtra("imgchecker");
        if(!check.equals("novalue"))
        {
            Bundle b1 = i1.getExtras();
            if (b1 != null)
            {
                Uri imguri =Uri.parse(b1.getString(DataBaseHelper.COL_IMAGE));
                mainimg.setImageURI(imguri);
            }
        }
        dbhelpchan.close();
        dbhelpkun.close();
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        readndisplay();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    public void getfromactivity(String s)
        {
            if(s!=null)
            {
                name = s;
                nametxt.setText(name);
            }
        }
}