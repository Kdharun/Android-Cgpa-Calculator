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

public class twelfth_fragment extends Fragment implements View.OnClickListener {
    View rootView;
    EditText twelfthpassed,regno,lang,eng,maths,physics,chemistry,biocsc,total,percentage,name,dob;
    Calendar c = Calendar.getInstance();
    Button updatetwelfth;
    int year = 2019;
    public twelfth_fragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_twelfth, container, false);
        name = rootView.findViewById(R.id.twelfth_name);
        dob = rootView.findViewById(R.id.twelfth_dob);
        regno= rootView.findViewById(R.id.twelfth_regno);
        lang=  rootView.findViewById(R.id.twelfth_lang);
        eng = rootView.findViewById(R.id.twelfth_eng);
        maths = rootView.findViewById(R.id.twelfth_maths);
        physics = rootView.findViewById(R.id.twelfth_physics);
        chemistry = rootView.findViewById(R.id.twelfth_chemistry);
        biocsc = rootView.findViewById(R.id.twelfth_biocsc);
        total = rootView.findViewById(R.id.twelfth_total);
        percentage = rootView.findViewById(R.id.twelfth_percent);
        updatetwelfth = rootView.findViewById(R.id.updatetwelfth);
        updatetwelfth.setOnClickListener(this);
        twelfthpassed = rootView.findViewById(R.id.plus2passeddate);
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

        twelfthpassed.setOnClickListener(new View.OnClickListener() {
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
        twelfthpassed.setText(dateFormat.format(c.getTime()));
        String yrFormat = "yyyy";
        SimpleDateFormat yrformat = new SimpleDateFormat(yrFormat,Locale.getDefault());
        year = Integer.parseInt(yrformat.format(c.getTime()).trim());


    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.updatetwelfth)
        {
            String passedout = String.valueOf(twelfthpassed.getText());
            String registerno = String.valueOf(regno.getText());
            int english = Integer.parseInt(String.valueOf(eng.getText()));
            int language = Integer.parseInt(String.valueOf(lang.getText()));
            int mathematics = Integer.parseInt(String.valueOf(maths.getText()));
            int physicsval = Integer.parseInt(String.valueOf(physics.getText()));
            int chemistryval = Integer.parseInt(String.valueOf(chemistry.getText()));
            int biocscval = Integer.parseInt(String.valueOf(biocsc.getText()));
            //calculate total and percentage using bounded service "tenth_calc_service"
            String totalvalue = tcs.total(new int[]{english, language, mathematics, physicsval, chemistryval, biocscval});
            String percentagevalue;
            if(year>=2019) {
                percentagevalue = tcs.percentage(new int[]{english, language, mathematics, physicsval, chemistryval, biocscval});
            }
            else
            {
                percentagevalue = tcs.percentagefor1200(new int[]{english, language, mathematics, physicsval, chemistryval, biocscval});
            }
            total.setText(totalvalue);
            percentage.setText(percentagevalue);
            Bundle b = new Bundle();
            b.putString("regno",registerno);
            b.putString("passedout",passedout);
            b.putString("total",totalvalue);
            b.putString("percent",percentagevalue);
            b.putIntArray("subjectsarray",new int[]{english,language,mathematics,physicsval,chemistryval,biocscval});
            boolean flagb = insertInto12thtable(b);
            if(flagb)
                Toast.makeText(getActivity(),"inserted into twelfth table",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(),"unsuccessful twelfth table insertion",Toast.LENGTH_SHORT).show();
        }
    }

    boolean isBound = false;
    private tenth_calc_service tcs;
    private ServiceConnection mConnection = new ServiceConnection()
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
        Bundle b = dbhelp.readfromtwelfth();
        if(b!=null)
        {
            regno.setText(b.getString(DataBaseHelper.TWCOL_PERCENT));
            String pass = b.getString(DataBaseHelper.TWCOL_PASSOUT);
            twelfthpassed.setText(pass);
            if(pass!=null)
                year = Integer.parseInt(pass.substring(3).trim());
            eng.setText(b.getString(DataBaseHelper.TWCOL_ENG));
            lang.setText(b.getString(DataBaseHelper.TWCOL_LANG));
            maths.setText(b.getString(DataBaseHelper.TWCOL_MATHS));
            physics.setText(b.getString(DataBaseHelper.TWCOL_PHYSICS));
            chemistry.setText(b.getString(DataBaseHelper.TWCOL_CHEM));
            biocsc.setText(b.getString(DataBaseHelper.TWCOL_BIOCSC));
            total.setText(b.getString(DataBaseHelper.TCOL_TOTAL));
            percentage.setText(b.getString(DataBaseHelper.TCOL_PERCENT));
        }
    }
    public boolean insertInto12thtable(Bundle b)
    {
        DataBaseHelper dbhelp = new DataBaseHelper(getActivity());
        boolean b2 =  dbhelp.addrowtwelfth(b);
        return b2;
    }
}