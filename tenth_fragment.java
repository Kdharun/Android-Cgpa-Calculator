package com.example.academictracker;

import static android.content.Context.BIND_AUTO_CREATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;

import android.app.Fragment;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class tenth_fragment extends Fragment implements View.OnClickListener
{
    View rootView;
    Calendar c = Calendar.getInstance();
    EditText tenpassed,regno,lang,eng,maths,social,science,total,percentage,name,dob;
    Button updatetenth;

    public tenth_fragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        rootView = inflater.inflate(R.layout.fragment_tenth, container, false);
        name = rootView.findViewById(R.id.tenth_name);
        dob = rootView.findViewById(R.id.tenth_dob);
        regno= rootView.findViewById(R.id.regnotxtbox);
        lang=  rootView.findViewById(R.id.lang);
        eng = rootView.findViewById(R.id.eng);
        maths = rootView.findViewById(R.id.maths);
        science = rootView.findViewById(R.id.science);
        social = rootView.findViewById(R.id.social);
        total = rootView.findViewById(R.id.total);
        percentage = rootView.findViewById(R.id.percentage);
        tenpassed= rootView.findViewById(R.id.tenpasseddate);
        updatetenth = rootView.findViewById(R.id.updatetenth);
        updatetenth.setOnClickListener(this);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH,monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        updateLabel();
                    }
                }, mYear, mMonth, mDay);
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);

        tenpassed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                datePickerDialog.show();
                      }
        });

        showvalues();
        return rootView;
    }
    private void updateLabel()
    {
        String myFormat="MMM yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        tenpassed.setText(dateFormat.format(c.getTime()));
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.updatetenth)
        {
            String passedout = String.valueOf(tenpassed.getText());
            String registerno = String.valueOf(regno.getText());
            int english = Integer.parseInt(String.valueOf(eng.getText()));
            int language = Integer.parseInt(String.valueOf(lang.getText()));
            int mathematics = Integer.parseInt(String.valueOf(maths.getText()));
            int scienceval = Integer.parseInt(String.valueOf(science.getText()));
            int socialval = Integer.parseInt(String.valueOf(social.getText()));
            //calculate total and percentage using bounded service "tenth_calc_service"
            String totalvalue = tcs.total(new int[]{english,language,mathematics,scienceval,socialval});
            String percentagevalue = tcs.percentage(new int[]{english,language,mathematics,scienceval,socialval});
            total.setText(totalvalue);
            percentage.setText(percentagevalue);
            //insert these into tenth details table in database
            Bundle b = new Bundle();
            b.putString("passedout",passedout);
            b.putString("regno",registerno);
            b.putString("total",totalvalue.trim());
            b.putString("percentage",percentagevalue.trim());
            b.putIntArray("subjectsarray",new int[]{english,language,mathematics,scienceval,socialval});
            boolean flagb = insertInto10thtable(b);
            if(!flagb)
                Toast.makeText(getActivity(),"unsuccessful tenth table insertion",Toast.LENGTH_SHORT).show();
        }
    }
    boolean isBound = false;
    private tenth_calc_service tcs;
    private final ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder)
        {
            tenth_calc_service.MyLocalBinder mlb = (tenth_calc_service.MyLocalBinder) iBinder;
            tcs = mlb.getService();
            isBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
    @Override
    public void onStart()
    {
        super.onStart();
        Intent intent = new Intent(getActivity(), tenth_calc_service.class);
        getActivity().bindService(intent, mConnection, BIND_AUTO_CREATE);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (isBound)
        {
            getActivity().unbindService(mConnection);
            isBound = false;
        }
    }

    public void showvalues()
    {
        DataBaseHelper dbhelp = new DataBaseHelper(getActivity());
        String dobvalue = dbhelp.readdob();
        String namevalue = dbhelp.readnamefromdb();
        name.setText(namevalue);
        dob.setText(dobvalue);
        Bundle b = dbhelp.readfromtenth();
        if(b!=null)
        {
          regno.setText(b.getString(DataBaseHelper.TCOL_PERCENT));
          tenpassed.setText(b.getString(DataBaseHelper.TCOL_PASSOUT));
          eng.setText(b.getString(DataBaseHelper.TCOL_ENG));
          lang.setText(b.getString(DataBaseHelper.TCOL_LANG));
          maths.setText(b.getString(DataBaseHelper.TCOL_MATHS));
          science.setText(b.getString(DataBaseHelper.TCOL_SCIENCE));
          social.setText(b.getString(DataBaseHelper.TCOL_SOCIAL));
          total.setText(b.getString(DataBaseHelper.TCOL_TOTAL));
          percentage.setText(b.getString(DataBaseHelper.TCOL_PERCENT));

        }
        dbhelp.close();
    }
    public boolean insertInto10thtable(Bundle b)
    {
        DataBaseHelper dbhelp = new DataBaseHelper(getActivity());
        boolean b2 =  dbhelp.addrowtenth(b);
        dbhelp.close();
        return b2;
    }
}