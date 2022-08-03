package com.example.academictracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    public List<GradeModel> semGradeList;
    public List<GradeModel> updatedSemGradeList = new ArrayList<>();
    Context context;
    View view;

    public GradeAdapter(List<GradeModel> semGradeList, Context context) {
        this.semGradeList = semGradeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.grade_rv_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(semGradeList != null && semGradeList.size()>0)
        {
            GradeModel model = semGradeList.get(position);
            holder.subCode.setText(model.getSubCode());
            holder.subName.setText(model.getSubName());
            holder.subCredit.setText(String.format("%.1f",model.getSubCredit()));

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.grade, R.layout.dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.grade.setAdapter(adapter);
            String g = model.getGrade();
            if (g != null && g !="Grade") {
                int spinnerPosition = adapter.getPosition(g);
                holder.grade.setSelection(spinnerPosition);
            }

            holder.grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position != 0)
                    {
                        String subCode = holder.subCode.getText().toString();
                        String subName = holder.subName.getText().toString();
                        double subCredit = Double.parseDouble(holder.subCredit.getText().toString());
                        String grade = holder.grade.getSelectedItem().toString();
                        GradeModel newGradeModel = new GradeModel(subCode, subName, subCredit, grade);
                        updatedSemGradeList.add(newGradeModel);
                        int no = updatedSemGradeList.size();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }
    }

    @Override
    public int getItemCount()
    {
        return semGradeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView subCode,subName,subCredit;
        Spinner grade;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCode = view.findViewById(R.id.subCode);
            subName = view.findViewById(R.id.subName);
            subCredit = view.findViewById(R.id.subCredit);
            grade = view.findViewById(R.id.spinGrade);
        }
    }
}
