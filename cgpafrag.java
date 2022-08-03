package com.example.academictracker;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class cgpafrag extends Fragment
{
    View rootView;
    RecyclerView recyclerView;
    CgpaAdapter cgpaAdapter;
    Button btnAdd,btnremsem;
    DataBaseHelper2 dataBaseHelper2;
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    Resources res;
    String dept,deptName;
    FragmentGrade frag;
    String[] department;
    ArrayAdapter<String> dropdown;
    public cgpafrag()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Activity fa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_cgpafrag, container, false);
        fa = getActivity();
        dataBaseHelper2 = new DataBaseHelper2(fa);
        btnremsem = rootView.findViewById(R.id.btnrem);
        btnremsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remSemester();
            }
        });
        textInputLayout = rootView.findViewById(R.id.textlayoutdept);
        autoCompleteTextView = rootView.findViewById(R.id.autocompletedept);

        res = getResources();
        department = res.getStringArray(R.array.DepartmentName);

        dept = dataBaseHelper2.getDept();
        deptName = dataBaseHelper2.getDeptName(dept);
        dropdown = new ArrayAdapter<>(fa,R.layout.dropdown_item,department);
        autoCompleteTextView.setAdapter(dropdown);

        if(dept.contains("NODEPT"));
        else {
            autoCompleteTextView.setText(deptName,false);
            autoCompleteTextView.dismissDropDown();
        }
        dropdown = new ArrayAdapter<>(fa,R.layout.dropdown_item,department);
        autoCompleteTextView.setAdapter(dropdown);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(dept.contains("NODEPT"))
                {
                    deptName = (String)parent.getItemAtPosition(position);
                    dept = dataBaseHelper2.getDeptfromName(deptName);
                    dataBaseHelper2.setDept(dept);
                    dropdown = new ArrayAdapter<>(fa,R.layout.dropdown_item,department);
                    autoCompleteTextView.setAdapter(dropdown);
                    Toast.makeText(fa,"Department Changed Successfully: "+dept,Toast.LENGTH_SHORT).show();
                }
                else if(deptName.contains((String)parent.getItemAtPosition(position)));
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fa);
                    builder.setMessage("On Changing Department previously entered data will be erased");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataBaseHelper2.destroy();
                            deptName = (String)parent.getItemAtPosition(position);
                            dept = dataBaseHelper2.getDeptfromName(deptName);
                            dataBaseHelper2.setDept(dept);
                            dialog.cancel();
                            Toast.makeText(fa,"Department Changed Successfully: "+dept,Toast.LENGTH_SHORT).show();
                            showSemester();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            autoCompleteTextView.setText(deptName,false);
                            dropdown = new ArrayAdapter<>(fa,R.layout.dropdown_item,department);
                            autoCompleteTextView.setAdapter(dropdown);

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                dropdown = new ArrayAdapter<>(fa,R.layout.dropdown_item,department);
                autoCompleteTextView.setAdapter(dropdown);
            }
        });

        recyclerView = rootView.findViewById(R.id.cgpa_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(fa));

        showSemester();

        btnAdd = rootView.findViewById(R.id.btnadd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dept == "NODEPT")
                    Toast.makeText(getActivity(),"Select a department",Toast.LENGTH_SHORT);
                else {
                    dataBaseHelper2.addSem();
                    showSemester();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(dept.contains("NODEPT"));
        else {
            autoCompleteTextView.setText(deptName,false);
            autoCompleteTextView.dismissDropDown();
        }
        dropdown = new ArrayAdapter<>(fa,R.layout.dropdown_item,department);
        autoCompleteTextView.setAdapter(dropdown);

    }

    public void remSemester()
    {
        dataBaseHelper2 = new DataBaseHelper2(fa);
        dataBaseHelper2.remSem();
        showSemester();
    }
    public  void showSemester()
    {
        dataBaseHelper2 = new DataBaseHelper2(fa);
        List<CgpaModel> semester = dataBaseHelper2.showSemList();
        cgpaAdapter = new CgpaAdapter(fa,semester,this);
        recyclerView.setAdapter(cgpaAdapter);
    }
    public void frag(int sem)
    {
        Bundle bun = new Bundle();
        bun.putInt("Semester",sem);
       frag = new FragmentGrade();
        FragmentManager man = getFragmentManager();
        FragmentTransaction tran = man.beginTransaction();
        tran.add(R.id.fragGrade,frag,"fragmentGrade");
        frag.setArguments(bun);
        tran.commit();
    }
    public void showDept(){
        dept = dataBaseHelper2.getDept();
        deptName = dataBaseHelper2.getDeptName(dept);
        autoCompleteTextView.setText(deptName,false);
        dropdown = new ArrayAdapter<>(fa,R.layout.dropdown_item,department);
        autoCompleteTextView.setAdapter(dropdown);
    }

}