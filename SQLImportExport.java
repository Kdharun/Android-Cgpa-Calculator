package com.example.academictracker;

import android.content.Context;
import android.os.Environment;
import android.os.FileUtils;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SQLImportExport {

    DataBaseHelper dataBaseHelper;
    DataBaseHelper2 dataBaseHelper2;

    public SQLImportExport(Context c) {
        this.c = c;
        dataBaseHelper2 = new DataBaseHelper2(c);
        dataBaseHelper = new DataBaseHelper(c);
    }

    Context c;

    protected void exportDB() {

        File dir = new File(Environment.getExternalStorageDirectory() + "/" + "SQLiteBackup");
        for(File tempFile : dir.listFiles()) {
            tempFile.delete();
        }

        File folder = new File(Environment.getExternalStorageDirectory() + "/" + "SQLiteBackup");
        if (!folder.exists()){
            folder.mkdir();
        }
        //Cgpa table
        String csvFileCgpa = "Sqlite_Backup_cgpa";
        String filePathAndNameCgpa = folder.toString() + "/" + csvFileCgpa;
        List<CgpaModel> cgpa = dataBaseHelper2.showSemList();

        //semester table
        String csvFileGrade = "Sqlite_Backup_Grade";
        String filePathAndNameGrade = folder.toString() + "/" + csvFileGrade;
        List<GradeModel> grade = dataBaseHelper2.showGradeTable();

        //dept table
        String csvFileDept = "Sqlite_Backup_dept";
        String filePathAndNameDept = folder.toString() + "/" + csvFileDept;
        String dept = dataBaseHelper2.getDept();

        //Tenth table
        String csvFileTenth = "Sqlite_Backup_Tenth";
        String filePathAndNameTenth = folder.toString() + "/" + csvFileTenth;
        TenthModel ten = dataBaseHelper.getTableTenth();

        //Twelfth Table
        String csvFileTwelfth = "Sqlite_Backup_Twelfth";
        String filePathAndNameTwelfth = folder.toString() + "/" + csvFileTwelfth;
        TwelfthModel twelve = dataBaseHelper.getTableTwelfth();

        //Personal Table
        String csvFilePersonal = "Sqlite_Backup_Personal";
        String filePathAndNamePersonal = folder.toString() + "/" + csvFilePersonal;
        PersonalModel personal = dataBaseHelper.GetTablePersonal();

        try{
            //Cgpa table
            FileWriter fw1 = new FileWriter(filePathAndNameCgpa);
            for (int i=0 ; i<cgpa.size();i++)
            {
                fw1.append(""+cgpa.get(i).getSemester());
                fw1.append(",");
                fw1.append(""+cgpa.get(i).getTotal_credits());
                fw1.append(",");
                fw1.append(""+cgpa.get(i).getGpa());
                fw1.append(",");
                fw1.append(""+cgpa.get(i).getCgpa());
                fw1.append("\n");
            }
            fw1.flush();
            fw1.close();

            //Grade Table
            FileWriter fw2 = new FileWriter(filePathAndNameGrade);
            for (int i=0 ; i<grade.size();i++)
            {
                fw2.append(""+grade.get(i).getSemester());
                fw2.append(",");
                fw2.append(""+grade.get(i).getSubCode());
                fw2.append(",");
                fw2.append(""+grade.get(i).getSubName());
                fw2.append(",");
                fw2.append(""+grade.get(i).getSubCredit());
                fw2.append(",");
                fw2.append(""+grade.get(i).getGrade());
                fw2.append("\n");
            }
            fw2.flush();
            fw2.close();

            //Dept Table
            FileWriter fw3 = new FileWriter(filePathAndNameDept);
            fw3.append(dept);
            fw3.flush();
            fw3.close();

            //Tenth Table

            FileWriter fw4 = new FileWriter(filePathAndNameTenth);
            fw4.append(""+ten.getId());
            fw4.append(",");
            fw4.append(""+ten.getRegNo());
            fw4.append(",");
            fw4.append(""+ten.getPassedout());
            fw4.append(",");
            fw4.append(""+ten.getLang());
            fw4.append(",");
            fw4.append(""+ten.getEng());
            fw4.append(",");
            fw4.append(""+ten.getMaths());
            fw4.append(",");
            fw4.append(""+ten.getSci());
            fw4.append(",");
            fw4.append(""+ten.getSoc());
            fw4.append(",");
            fw4.append(""+ten.getTotal());
            fw4.append(",");
            fw4.append(""+ten.getPercentage());
            fw4.flush();
            fw4.close();

            //twelfth Table

            FileWriter fw5 = new FileWriter(filePathAndNameTwelfth);
            fw5.append(""+twelve.getId());
            fw5.append(",");
            fw5.append(""+twelve.getRegNo());
            fw5.append(",");
            fw5.append(""+twelve.getPassedout());
            fw5.append(",");
            fw5.append(""+twelve.getLang());
            fw5.append(",");
            fw5.append(""+twelve.getEng());
            fw5.append(",");
            fw5.append(""+twelve.getMaths());
            fw5.append(",");
            fw5.append(""+twelve.getPhy());
            fw5.append(",");
            fw5.append(""+twelve.getChem());
            fw5.append(",");
            fw5.append(""+twelve.getBiocs());
            fw5.append(",");
            fw5.append(""+twelve.getTotal());
            fw5.append(",");
            fw5.append(""+twelve.getPercentage());
            fw5.flush();
            fw5.close();

            //Personal Table

            //String image = new String(personal.getImage());
            FileWriter fw6 = new FileWriter(filePathAndNamePersonal);
            fw6.append(""+personal.getId());
            fw6.append(",");
            fw6.append(""+personal.getName());
            fw6.append(",");
            fw6.append(""+personal.getDob());
            fw6.append(",");
            fw6.append(""+personal.getGender());
            fw6.append(",");
            fw6.append(""+personal.getEmail());
            fw6.append(",");
            fw6.append(""+personal.getPhone());
            fw6.append(",");
            fw6.append(""+personal.getAddress());
            //fw6.append(",");
            //fw6.append(""+image);
            fw6.flush();
            fw6.close();

            Toast.makeText(c,"State Stored", Toast.LENGTH_SHORT).show();
        }

        catch (Exception e)
        {
            Toast.makeText(c,""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    protected void importDB() {

        //delete table
        dataBaseHelper2.destroy();
        dataBaseHelper.destroy();

        String fileNameAndPathCgpa = Environment.getExternalStorageDirectory() + "/" + "SQLiteBackup" + "/" +"Sqlite_Backup_cgpa";
        File csvCgpa = new File(fileNameAndPathCgpa);

        if(csvCgpa.exists())
        {
            try{
                CSVReader csvReader = new CSVReader(new FileReader(csvCgpa.getAbsolutePath()));

                String[] nextLine;
                while((nextLine = csvReader.readNext())!= null){
                    String sem = nextLine[0];
                    String totalCredit = nextLine[1];
                    String gpa = nextLine[2];
                    String cgpa = nextLine[3];

                    CgpaModel value = new CgpaModel(Integer.parseInt(sem),Double.parseDouble(totalCredit),Double.parseDouble(gpa),Double.parseDouble(cgpa));
                    dataBaseHelper2.insertCgpa(value);
                };
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else{
            Toast.makeText(c,"No Backup Found for Cgpa", Toast.LENGTH_SHORT).show();
        }

        String fileNameAndPathGrade = Environment.getExternalStorageDirectory() + "/" + "SQLiteBackup" + "/" + "Sqlite_Backup_Grade";
        File csvGrade = new File(fileNameAndPathGrade);


        if(csvGrade.exists())
        {
            try{
                CSVReader csvReader = new CSVReader(new FileReader(csvGrade.getAbsolutePath()));

                String[] nextLine;
                while((nextLine = csvReader.readNext())!= null){
                    String sem = nextLine[0];
                    String subcode = nextLine[1];
                    String subname = nextLine[2];
                    String subcredit = nextLine[3];
                    String grade = nextLine[4];

                    GradeModel value = new GradeModel(Integer.parseInt(sem), subcode,subname,Double.parseDouble(subcredit),grade);
                    dataBaseHelper2.insertGrade(value);
                };
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else{
            Toast.makeText(c,"No Backup Found for Grade", Toast.LENGTH_SHORT).show();
        }

        String fileNameAndPathDept = Environment.getExternalStorageDirectory() + "/" + "SQLiteBackup" + "/" + "Sqlite_Backup_dept";
        File csvDept = new File(fileNameAndPathDept);

        if(csvDept.exists())
        {
            try{
                CSVReader csvReader = new CSVReader(new FileReader(csvDept.getAbsolutePath()));

                String[] nextLine;
                nextLine = csvReader.readNext();
                String dept = nextLine[0];
                dataBaseHelper2.setDept(dept);

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else{
            Toast.makeText(c,"No Backup Found for Dept", Toast.LENGTH_SHORT).show();
        }

        String fileNameAndPathTenth = Environment.getExternalStorageDirectory() + "/" + "SQLiteBackup" + "/" + "Sqlite_Backup_Tenth";
        File csvTenth = new File(fileNameAndPathTenth);


        if(csvTenth.exists())
        {
            try{
                CSVReader csvReader = new CSVReader(new FileReader(csvTenth.getAbsolutePath()));

                String[] nextLine;
                nextLine = csvReader.readNext();

                String id = nextLine[0];
                String regno = nextLine[1];
                String pas = nextLine[2];
                String lang = nextLine[3];
                String eng = nextLine[4];
                String maths = nextLine[5];
                String sci = nextLine[6];
                String soc = nextLine[7];
                String tot = nextLine[8];
                String per = nextLine[9];

                TenthModel ten = new TenthModel(Integer.parseInt(id),regno,pas,Integer.parseInt(lang),Integer.parseInt(eng),Integer.parseInt(maths),Integer.parseInt(sci),Integer.parseInt(soc),Integer.parseInt(tot),Double.parseDouble(per));
                dataBaseHelper.insertTableTenth(ten);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else{
            Toast.makeText(c,"No Backup Found for Tenth", Toast.LENGTH_SHORT).show();
        }

        String fileNameAndPathTwelfth = Environment.getExternalStorageDirectory() + "/" + "SQLiteBackup" + "/" + "Sqlite_Backup_Twelfth";
        File csvTwelfth = new File(fileNameAndPathTwelfth);

        if(csvTwelfth.exists())
        {
            try{
                CSVReader csvReader = new CSVReader(new FileReader(csvTwelfth.getAbsolutePath()));

                String[] nextLine;
                nextLine = csvReader.readNext();

                String id = nextLine[0];
                String regno = nextLine[1];
                String pas = nextLine[2];
                String lang = nextLine[3];
                String eng = nextLine[4];
                String maths = nextLine[5];
                String phy = nextLine[6];
                String chem = nextLine[7];
                String biocs = nextLine[8];
                String tot = nextLine[9];
                String per = nextLine[10];

                TwelfthModel twelve = new TwelfthModel(Integer.parseInt(id) ,regno,pas,Integer.parseInt(lang),Integer.parseInt(eng),Integer.parseInt(maths),Integer.parseInt(phy),Integer.parseInt(chem),Integer.parseInt(biocs),Integer.parseInt(tot),Double.parseDouble(per));
                dataBaseHelper.insertTableTwelfth(twelve);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else{
            Toast.makeText(c,"No Backup Found for Twelfth", Toast.LENGTH_SHORT).show();
        }

        String fileNameAndPathPersonal = Environment.getExternalStorageDirectory() + "/" + "SQLiteBackup" + "/" + "Sqlite_Backup_Personal";
        File csvPersonal = new File(fileNameAndPathPersonal);

        if(csvPersonal.exists())
        {
            try{
                CSVReader csvReader = new CSVReader(new FileReader(csvPersonal.getAbsolutePath()));

                String[] nextLine;
                nextLine = csvReader.readNext();

                String id = nextLine[0];
                String name = nextLine[1];
                String dob = nextLine[2];
                String gender = nextLine[3];
                String email = nextLine[4];
                String phone = nextLine[5];
                String address = nextLine[6];
                //String image = nextLine[7];

                PersonalModel personal = new PersonalModel(Integer.parseInt(id),name,dob,gender,email,phone,address);
                dataBaseHelper.insertTablePersonal(personal);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else{
            Toast.makeText(c,"No Backup Found for Twelfth", Toast.LENGTH_SHORT).show();
        }
    }
}
