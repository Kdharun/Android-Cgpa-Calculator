package com.example.academictracker;

import android.content.Context;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CgpaAdapter extends RecyclerView.Adapter<CgpaAdapter.ViewHolder>
{
    Context context;
    List<CgpaModel> semesterList;
    View view;
    cgpafrag ca;

    public CgpaAdapter(Context context, List<CgpaModel> semesterList) {
        this.context = context;
        this.semesterList = semesterList;
    }
    public CgpaAdapter(Context context, List<CgpaModel> semesterList,cgpafrag ca) {
        this.context = context;
        this.semesterList = semesterList;
        this.ca = ca;
    }

    @NonNull
    @Override
    public CgpaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.cgpa_rv_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CgpaAdapter.ViewHolder holder, int position) {

        if(semesterList != null && semesterList.size() > 0)
        {

            CgpaModel model = semesterList.get(position);
            holder.sem.setText(String.format("%d",model.getSemester()));
            holder.credit.setText(String.format("%.1f",model.getTotal_credits()));
            holder.gpa.setText(String.format("%.2f",model.getGpa()));
            holder.cgpa.setText(String.format("%.2f",model.getCgpa()));

            holder.sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sem = Integer.parseInt(holder.sem.getText().toString());
                  //  MainActivity m = (MainActivity) context;
                    ca.frag(sem);
                }
            });

        }
    }

    @Override
    public int getItemCount()
    {
        return semesterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView credit,gpa,cgpa;
        Button sem;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);


            sem = itemView.findViewById(R.id.cgpa_sem);
            credit = itemView.findViewById(R.id.cgpa_credit);
            gpa = itemView.findViewById(R.id.gpa);
            cgpa = itemView.findViewById(R.id.cgpa);
        }
    }
}
