package com.example.academictracker;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper
{
    // Table names
    public static final String PERSONAL_DETAILS = "PERSONAL_DETAILS";
    public static final String TENTH_DETAILS = "TENTH_DETAILS";
    public static final String TWELFTH_DETAILS = "TWELFTH_DETAILS";
    //column for personal details table
    public static final String COL_NAME = "Name";
    public static final String COL_DOB = "DOB";
    public static final String COL_GENDER = "Gender";
    public static final String COL_EMAIL = "Email";
    public static final String COL_PHONE = "Phone";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_IMAGE = "image";

    //column for tenth details table
    public static final String TCOL_REGNO  =  "Register_no";
    public static final String TCOL_PASSOUT = "Passed_out";
    public static final String TCOL_LANG = "Language";
    public static final String TCOL_ENG = "English";
    public static final String TCOL_MATHS = "Maths";
    public static final String TCOL_SCIENCE = "Science";
    public static final String TCOL_SOCIAL = "Social";
    public static final String TCOL_TOTAL = "Total";
    public static final String TCOL_PERCENT = "Percentage";

    //column for twelfth details table
    public static final String TWCOL_REGNO = "Register_no";
    public static final String TWCOL_PASSOUT = "Passed_out";
    public static final String TWCOL_LANG = "Language";
    public static final String TWCOL_ENG = "English";
    public static final String TWCOL_MATHS = "Maths";
    public static final String TWCOL_PHYSICS = "Physics";
    public static final String TWCOL_CHEM = "Chemistry";
    public static final String TWCOL_BIOCSC = "Biocsc";
    public static final String TWCOL_TOTAL = "Total";
    public static final String TWCOL_PERCENT = "Percentage";
    //Common columns for both tables
    private static final String COL_ID = "id";

    int row_no = 1;

    Context gc;

    public DataBaseHelper(@Nullable Context context)
    {
        super(context,"persondet.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createpersstmt = "CREATE TABLE " + PERSONAL_DETAILS + " ("+COL_ID+" INTEGER primary key," + COL_NAME + " TEXT," + COL_DOB + " TEXT," + COL_GENDER + " TEXT," + COL_EMAIL + " TEXT," + COL_PHONE + " TEXT," + COL_ADDRESS + " TEXT," + COL_IMAGE + " BLOB )";
        db.execSQL(createpersstmt);
        String createtenthstmt = "CREATE TABLE " + TENTH_DETAILS + " ("+COL_ID+ " INTEGER primary key," + TCOL_REGNO + " TEXT," + TCOL_PASSOUT + " TEXT," + TCOL_LANG + " INTEGER," + TCOL_ENG + " INTEGER," + TCOL_MATHS + " INTEGER," + TCOL_SCIENCE + " INTEGER," + TCOL_SOCIAL + " INTEGER," + TCOL_TOTAL + " INTEGER," + TCOL_PERCENT + " Real)";
        db.execSQL(createtenthstmt);
        String createtwelfthstmt = "CREATE TABLE " + TWELFTH_DETAILS + " ("+COL_ID+ " INTEGER primary key," + TWCOL_REGNO + " TEXT," + TWCOL_PASSOUT + " TEXT," + TWCOL_LANG + " INTEGER," + TWCOL_ENG + " INTEGER," + TWCOL_MATHS + " INTEGER," + TWCOL_PHYSICS + " INTEGER," + TWCOL_CHEM + " INTEGER," +TWCOL_BIOCSC + " INTEGER," + TWCOL_TOTAL + " INTEGER," + TWCOL_PERCENT + " Real)";
        db.execSQL(createtwelfthstmt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {    }

    byte[] imgbytearray;
    public void imgconversion(Uri selectedimg) throws IOException
    {
        InputStream iStream = null;
        try {
            iStream = gc.getContentResolver().openInputStream(selectedimg);
            imgbytearray = getBytes(iStream);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    //adds a row to personal details table
    public boolean addrowpd(Context c1,Intent i) throws IOException {
        gc = c1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(!(i.getExtras().getString("imagedb").equals("noimgupload")))
        {
            imgconversion(Uri.parse(i.getExtras().getString("imagedb")));
            cv.put(COL_IMAGE,imgbytearray);
        }

        cv.put(COL_ID,1);
        cv.put(COL_NAME,i.getExtras().getString("namedb"));
        cv.put(COL_DOB,i.getExtras().getString("dobdb"));
        cv.put(COL_GENDER,i.getExtras().getString("genderdb"));
        cv.put(COL_EMAIL,i.getExtras().getString("emaildb"));
        cv.put(COL_PHONE,i.getExtras().getString("phonenodb"));
        cv.put(COL_ADDRESS,i.getExtras().getString("addressdb"));


        SQLiteDatabase db1 = this.getReadableDatabase();
        String readquery = "Select id from "+PERSONAL_DETAILS+" where id = 1";
        Cursor c = db1.rawQuery(readquery,null);
        long flag;
        if(c.moveToFirst())
        {
             flag = db.update(PERSONAL_DETAILS,cv,"id = ?",new String[]{String.valueOf(row_no)});
        }
        else
        {
            flag = db.insert(PERSONAL_DETAILS, null, cv);
        }
        c.close();
        close();
        if(flag == -1)
            return false;
        else
            return true;
    }
    //reads data from personal details table to and returns to personal fragment (through service )
    public Intent readfromdb(Context c2)
    {
        gc = c2;
        SQLiteDatabase db1 = this.getReadableDatabase();
        String readquery = "Select "+COL_NAME+","+COL_DOB+","+COL_GENDER+","+COL_EMAIL+","+COL_PHONE+","+COL_ADDRESS+" from "+PERSONAL_DETAILS+" where id = 1";
        Cursor c = db1.rawQuery(readquery,null);
        Intent i = new Intent();
        if(c.moveToFirst())
        {
            i.putExtra(COL_ID,1);
            i.putExtra(COL_NAME,c.getString(c.getColumnIndexOrThrow(COL_NAME)));
            i.putExtra(COL_DOB,c.getString(c.getColumnIndexOrThrow(COL_DOB)));
            i.putExtra(COL_GENDER,c.getString(c.getColumnIndexOrThrow(COL_GENDER)));
            i.putExtra(COL_EMAIL,c.getString(c.getColumnIndexOrThrow(COL_EMAIL)));
            i.putExtra(COL_PHONE,c.getString(c.getColumnIndexOrThrow(COL_PHONE)));
            i.putExtra(COL_ADDRESS,c.getString(c.getColumnIndexOrThrow(COL_ADDRESS)));
           // Toast.makeText(gc,"database is read",Toast.LENGTH_LONG).show();

        }
        else
        {
            i.putExtra(COL_ID,0);
            i.putExtra(COL_NAME,"data entry");
            i.putExtra(COL_DOB,"");
            i.putExtra(COL_GENDER,"");
            i.putExtra(COL_EMAIL,"");
            i.putExtra(COL_PHONE,"");
            i.putExtra(COL_ADDRESS,"");

        }
        c.close();
        String img1query =  "SELECT "+COL_IMAGE+" FROM "+PERSONAL_DETAILS+" WHERE id = 1";
        Cursor c1 = db1.rawQuery(img1query,null);
        if(c1.moveToFirst())
        {
            byte[] b =  c1.getBlob(0);
            Bitmap bitimg = getImage(b);
            Uri u =  getImageUri(gc,bitimg);
             i.putExtra(COL_IMAGE,u.toString());
            i.putExtra("imgchecker","yes");
            //Toast.makeText(gc,"image is read: "+bitimg,Toast.LENGTH_LONG).show();

        }
        else
       {
         //  Toast.makeText(gc,"image is not read",Toast.LENGTH_LONG).show();
           i.putExtra("imgchecker","novalue");
       }
        c1.close();

        return i;
    }

    public String readnamefromdb()
    {
        String name="";
        SQLiteDatabase db1 = this.getReadableDatabase();
        String readnameqry = "SELECT "+COL_NAME+" FROM "+PERSONAL_DETAILS+" WHERE id = 1";
        Cursor c = db1.rawQuery(readnameqry,null);
        if(c.moveToFirst())
        {
           name = c.getString(c.getColumnIndexOrThrow(COL_NAME));
        }
        return name;
    }
    public Bundle readformainfrag()
    {
        Bundle b = new Bundle();
        SQLiteDatabase db1 = this.getReadableDatabase();
        String qry = "SELECT "+COL_NAME+" FROM "+PERSONAL_DETAILS+" WHERE id = 1";
        Cursor c = db1.rawQuery(qry,null);
        if(c.moveToFirst())
            b.putString("name",c.getString(c.getColumnIndexOrThrow(COL_NAME)));
        c.close();
        qry = "Select "+TWCOL_PERCENT+" from "+TWELFTH_DETAILS;
        c = db1.rawQuery(qry,null);
        if(c.moveToFirst())
            b.putString("twelfth",c.getString(c.getColumnIndexOrThrow(TWCOL_PERCENT)));
        c.close();
        qry = "Select "+TCOL_PERCENT+" from "+TENTH_DETAILS;
        c = db1.rawQuery(qry,null);
        if(c.moveToFirst())
            b.putString("tenth",c.getString(c.getColumnIndexOrThrow(TCOL_PERCENT)));
        c.close();
        return b;
    }

    public String readdob()
    {
        String dob ="";
        SQLiteDatabase db1 = this.getReadableDatabase();
        String readdobqry = "Select "+COL_DOB+" FROM "+PERSONAL_DETAILS+" WHERE id = 1";
        Cursor c = db1.rawQuery(readdobqry,null);
        if(c.moveToFirst())
        {
            dob = c.getString(c.getColumnIndexOrThrow(COL_DOB));
        }
        return dob;
    }

    public static Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + System.currentTimeMillis(), null);
       // Toast.makeText(gc,"Path : "+path,Toast.LENGTH_LONG).show();
        Uri u = Uri.parse(path);
      //  Toast.makeText(gc,"image is read: ",Toast.LENGTH_LONG).show();

        return u;
    }

    //From here on, only tenth insert and read methods :)

    public boolean addrowtenth(Bundle b)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID,1);
        cv.put(TCOL_REGNO,b.getString("regno"));
        cv.put(TCOL_PASSOUT,b.getString("passedout"));
        cv.put(TCOL_TOTAL,b.getString("total"));
        cv.put(TCOL_PERCENT,Double.valueOf(b.getString("percentage")));
        int[] a = b.getIntArray("subjectsarray"); // eng,lang,maths,science,social
        cv.put(TCOL_ENG,a[0]);
        cv.put(TCOL_LANG,a[1]);
        cv.put(TCOL_MATHS,a[2]);
        cv.put(TCOL_SCIENCE,a[3]);
        cv.put(TCOL_SOCIAL,a[4]);
        SQLiteDatabase db1 = this.getReadableDatabase();
        String readquery = "Select "+COL_ID+"  from "+TENTH_DETAILS+" where "+COL_ID+" = 1";
        Cursor c = db1.rawQuery(readquery,null);
        long flag;
        if(c.moveToFirst())
        {
            flag = db.update(TENTH_DETAILS,cv,COL_ID+" = ?",new String[]{String.valueOf(row_no)});
        }
        else
        {
            flag = db.insert(TENTH_DETAILS, null, cv);
        }
        if(flag==-1)
            return false;
        else
           return true;
    }
    public Bundle readfromtenth()
    {
        Bundle b = new Bundle();
        SQLiteDatabase db = this.getReadableDatabase();
        String readquery = "Select * from "+TENTH_DETAILS;
        Cursor c = db.rawQuery(readquery,null);
        if(c.moveToFirst())
        {
            b.putString(TCOL_REGNO,c.getString(c.getColumnIndexOrThrow(TCOL_REGNO)));
            b.putString(TCOL_PASSOUT,c.getString(c.getColumnIndexOrThrow(TCOL_PASSOUT)));
            b.putString(TCOL_ENG,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TCOL_ENG))));
            b.putString(TCOL_LANG, String.valueOf(c.getInt(c.getColumnIndexOrThrow(TCOL_LANG))));
            b.putString(TCOL_MATHS,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TCOL_MATHS))));
            b.putString(TCOL_SCIENCE,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TCOL_SCIENCE))));
            b.putString(TCOL_SOCIAL,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TCOL_SOCIAL))));
            b.putString(TCOL_TOTAL,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TCOL_TOTAL))));
            b.putString(TCOL_PERCENT,String.valueOf(c.getDouble(c.getColumnIndexOrThrow(TCOL_PERCENT))));
        }
        return b;
    }

    //From here on, only tenth insert and read methods :)
    public boolean addrowtwelfth(Bundle b)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID,1);
        cv.put(TWCOL_REGNO,b.getString("regno"));
        cv.put(TWCOL_PASSOUT,b.getString("passedout"));
        cv.put(TWCOL_TOTAL,b.getString("total"));
        cv.put(TWCOL_PERCENT,Double.valueOf(b.getString("percent")));
        int[] a = b.getIntArray("subjectsarray"); // eng,lang,maths,science,social
        cv.put(TWCOL_ENG,a[0]);
        cv.put(TWCOL_LANG,a[1]);
        cv.put(TWCOL_MATHS,a[2]);
        cv.put(TWCOL_PHYSICS,a[3]);
        cv.put(TWCOL_CHEM,a[4]);
        cv.put(TWCOL_BIOCSC,a[5]);
        SQLiteDatabase db1 = this.getReadableDatabase();
        String readquery = "Select "+COL_ID+"  from "+TWELFTH_DETAILS+" where "+COL_ID+" = 1";
        Cursor c = db1.rawQuery(readquery,null);
        long flag;
        if(c.moveToFirst())
        {
            flag = db.update(TWELFTH_DETAILS,cv,COL_ID+" = ?",new String[]{String.valueOf(row_no)});
        }
        else
        {
            flag = db.insert(TWELFTH_DETAILS, null, cv);
        }
        if(flag==-1)
            return false;
        else
            return true;
    }
    public Bundle readfromtwelfth()
    {
        Bundle b = new Bundle();
        SQLiteDatabase db = this.getReadableDatabase();
        String readquery = "Select * from "+TWELFTH_DETAILS;
        Cursor c = db.rawQuery(readquery,null);
        if(c.moveToFirst())
        {
            b.putString(TWCOL_REGNO,c.getString(c.getColumnIndexOrThrow(TWCOL_REGNO)));
            b.putString(TWCOL_PASSOUT,c.getString(c.getColumnIndexOrThrow(TWCOL_PASSOUT)));
            b.putString(TWCOL_ENG,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TWCOL_ENG))));
            b.putString(TWCOL_LANG, String.valueOf(c.getInt(c.getColumnIndexOrThrow(TWCOL_LANG))));
            b.putString(TWCOL_MATHS,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TWCOL_MATHS))));
            b.putString(TWCOL_PHYSICS,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TWCOL_PHYSICS))));
            b.putString(TWCOL_CHEM,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TWCOL_CHEM))));
            b.putString(TWCOL_BIOCSC,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TWCOL_BIOCSC))));
            b.putString(TWCOL_TOTAL,String.valueOf(c.getInt(c.getColumnIndexOrThrow(TWCOL_TOTAL))));
            b.putString(TWCOL_PERCENT,String.valueOf(c.getDouble(c.getColumnIndexOrThrow(TWCOL_PERCENT))));
        }
        return b;
    }

    public TenthModel getTableTenth()
    {
        SQLiteDatabase db = getReadableDatabase();
        String q ="SELECT * FROM "+TENTH_DETAILS+" ;";
        Cursor cursor = db.rawQuery(q,null);

       cursor.moveToFirst();
        int id = cursor.getInt(0);
        String regNo = cursor.getString(1);
        String passedOut = cursor.getString(2);
        int lang = cursor.getInt(3);
        int eng = cursor.getInt(4);
        int math = cursor.getInt(5);
        int sci = cursor.getInt(6);
        int soc = cursor.getInt(7);
        int tot = cursor.getInt(8);
        double per = cursor.getDouble(9);

        TenthModel returnVal = new TenthModel(id,regNo,passedOut,lang,eng,math,sci,soc,tot,per);

        return returnVal;
    }

    public void insertTableTenth(TenthModel ten)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",ten.getId());
        cv.put(TCOL_REGNO,ten.getRegNo());
        cv.put(TCOL_PASSOUT,ten.getPassedout());
        cv.put(TCOL_LANG,ten.getLang());
        cv.put(TCOL_ENG,ten.getEng());
        cv.put(TCOL_MATHS,ten.getMaths());
        cv.put(TCOL_SCIENCE,ten.getSci());
        cv.put(TCOL_SOCIAL,ten.getSoc());
        cv.put(TCOL_TOTAL,ten.getTotal());
        cv.put(TCOL_PERCENT,ten.getPercentage());
        db.insert(TENTH_DETAILS,null, cv);
    }

    public TwelfthModel getTableTwelfth()
    {
        SQLiteDatabase db = getReadableDatabase();
        String q ="SELECT * FROM "+TWELFTH_DETAILS+" ;";
        Cursor cursor = db.rawQuery(q,null);

        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String regNo = cursor.getString(1);
        String passedOut = cursor.getString(2);
        int lang = cursor.getInt(3);
        int eng = cursor.getInt(4);
        int math = cursor.getInt(5);
        int phy = cursor.getInt(6);
        int chem = cursor.getInt(7);
        int biocs = cursor.getInt(8);
        int tot = cursor.getInt(9);
        double per = cursor.getDouble(10);

        TwelfthModel returnVal = new TwelfthModel(id,regNo,passedOut,lang,eng,math,phy,chem,biocs,tot,per);

        return returnVal;
    }

    public void insertTableTwelfth(TwelfthModel twelve)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",twelve.getId());
        cv.put(TWCOL_REGNO,twelve.getRegNo());
        cv.put(TWCOL_PASSOUT,twelve.getPassedout());
        cv.put(TWCOL_LANG,twelve.getLang());
        cv.put(TWCOL_ENG,twelve.getEng());
        cv.put(TWCOL_MATHS,twelve.getMaths());
        cv.put(TWCOL_PHYSICS,twelve.getPhy());
        cv.put(TWCOL_CHEM,twelve.getChem());
        cv.put(TWCOL_BIOCSC,twelve.getBiocs());
        cv.put(TWCOL_TOTAL,twelve.getTotal());
        cv.put(TWCOL_PERCENT,twelve.getPercentage());
        db.insert(TWELFTH_DETAILS,null, cv);
    }

    public PersonalModel GetTablePersonal()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String q ="SELECT * FROM "+PERSONAL_DETAILS+" ;";
        Cursor cursor = db.rawQuery(q,null);

        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String dob = cursor.getString(2);
        String gen = cursor.getString(3);
        String email = cursor.getString(4);
        String phone = cursor.getString(5);
        String address = cursor.getString(6);
        //byte[] image = cursor.getBlob(7);
        PersonalModel pm = new PersonalModel(id,name,dob,gen,email, phone,address);

        return pm;
    }

    public void insertTablePersonal(PersonalModel m)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "UPDATE PERSONAL_DETAILS SET Name = '"+m.getName()+"' , DOB = '"+m.getDob()+"' , Gender = '"+m.getGender()+"' , Mail = '"+m.getEmail()+"' , Phone = '"+m.getPhone()+"' , Address = '"+m.getAddress()+"' WHERE id = '"+m.getId()+"' ; ";
        //cv.put(COL_IMAGE,personal.getImage());
    }

    public void destroy()
    {
        SQLiteDatabase db = getWritableDatabase();
        String del2 = "DELETE FROM "+TENTH_DETAILS+" ; ";
        String del3 = "DELETE FROM "+TWELFTH_DETAILS+" ; ";
        db.execSQL(del2);
        db.execSQL(del3);
    }
}