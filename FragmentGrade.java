package com.example.academictracker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academictracker.DataBaseHelper2;
import com.example.academictracker.GradeAdapter;
import com.example.academictracker.GradeModel;
import com.example.academictracker.MainActivity;
import com.example.academictracker.R;
import com.example.academictracker.cgpafrag;

import java.util.List;

public class FragmentGrade extends Fragment {

    TextView tvSem;
    RecyclerView recyclerView;
    GradeAdapter gradeAdapter;
    DataBaseHelper2 dataBaseHelper2;
    Button update,back;
    List<GradeModel> updatedList;
    CheckBox chkHonour,chkOpen;
    DividerItemDecoration itemDecoration;
    boolean checkOpen,checkHonour;
    int sem;
    cgpafrag ca;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_grade, container, false);
        dataBaseHelper2 = new DataBaseHelper2(getActivity());
        back =  view.findViewById(R.id.btngradeback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removing fragment
                FragmentManager man = getFragmentManager();
                FragmentGrade fragGrade = (FragmentGrade) man.findFragmentByTag("fragmentGrade");
                FragmentTransaction tran = man.beginTransaction();
                tran.remove(fragGrade);
                tran.commit();
            }
        });
        sem = getArguments().getInt("Semester");
        tvSem = view.findViewById(R.id.textviewsem);
        tvSem.setText("Semester "+sem);

        chkHonour = view.findViewById(R.id.checkBoxHonour);
        chkOpen = view.findViewById(R.id.checkBoxOpen);

        if(sem>2 && sem<8)
        {
            chkOpen.setVisibility(View.VISIBLE);
            chkHonour.setVisibility(View.VISIBLE);
        }
        else
        {
            chkOpen.setVisibility(View.GONE);
            chkHonour.setVisibility(View.GONE);
        }
        checkOpen = dataBaseHelper2.checkOpen(sem);
        if (checkOpen == true)
        {
            chkOpen.setChecked(true);
        }
        checkHonour = dataBaseHelper2.checkHonour(sem);
        if (checkHonour == true)
        {
            chkHonour.setChecked(true);
        }
        chkOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked == true)
                {
                    dataBaseHelper2.inOpen(sem);
                    showGrade();
                }
                if(isChecked == false)
                {
                    dataBaseHelper2.remOpen(sem);
                    showGrade();
                }

            }
        });
        chkHonour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked == true)
                {
                    dataBaseHelper2.inHonour(sem);
                    showGrade();
                }
                if(isChecked == false)
                {
                    dataBaseHelper2.remHonour(sem);
                    showGrade();
                }
            }
        });


        recyclerView = view.findViewById(R.id.grade_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Drawable divider = ContextCompat.getDrawable(recyclerView.getContext(),R.drawable.divider);
        itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        //itemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(),R.drawable.divider));
        //itemDecoration.setDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
        recyclerView.addItemDecoration(itemDecoration);


        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        showGrade();
        update = view.findViewById(R.id.btngradeupdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper2.updateGrade(updatedList,sem);
                cgpafraglistener cfl = (cgpafraglistener) getActivity();
                cfl.showsemcaller();
            }
        });

        return view;
    }
    public void showGrade()
    {
        List<GradeModel> studentGrade = dataBaseHelper2.showGrade(sem);
        gradeAdapter = new GradeAdapter(studentGrade,getActivity());
        recyclerView.setAdapter(gradeAdapter);
        updatedList = gradeAdapter.updatedSemGradeList;
    }
    public void gradeview()
    {

    }
}