package com.example.academictracker;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,persfraglistener,cgpafraglistener

{
    MainFragment mainFragment;
    tenth_fragment tenthFragment;
    Personal_fragment personalFragment;
    twelfth_fragment twelfthFragment;
    cgpafrag cgpaFrag;
    FragmentGrade fragmentGrade;
    DrawerLayout drawerLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView collapse_img;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ShapeableImageView simgbox;
    int[] images= new int[] {R.drawable.quotes1, R.drawable.quotes2, R.drawable.quotes3,R.drawable.quotes4,R.drawable.quotes5};
    boolean flagy = false;
    boolean b;
    String name1="";
    Mybroadreceiver mbr;
    //for restoring state
    private static final int STORAGE_REQUEST_CODE_EXPORT = 1;
    private static final int STORAGE_REQUEST_CODE_IMPORT = 2;
    private String[] storagePermissions;
    SQLImportExport sqlImportExport;
    ShapeableImageView drawerimg;
    TextView drawertxt;
    String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlImportExport = new SQLImportExport(this);

        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        mbr = new Mybroadreceiver();
        //toolbar setup
        appBarLayout = findViewById(R.id.appbarlay);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = findViewById(R.id.collap_tb);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle("Academic Tracker");


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        fragmentManager = getFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        mainFragment = new MainFragment();
        tenthFragment = new tenth_fragment();
        twelfthFragment = new twelfth_fragment();
        personalFragment = new Personal_fragment();

        cgpaFrag = new cgpafrag();
        fragmentGrade = new FragmentGrade();

        fragmentTransaction.add(R.id.container_fragment,mainFragment,"mainfrag1");
        fragmentTransaction.commit();
        b = true;
        collapse_img = findViewById(R.id.collapseimage);

        int imageId = (int)(Math.random() * images.length);
        collapse_img.setImageResource(images[imageId]);

        View hView = navigationView.getHeaderView(0);

        drawerimg = (ShapeableImageView) hView.findViewById(R.id.drawerImageView);
        drawertxt = (TextView) hView.findViewById(R.id.drawertextView);
        displayfordrawer();

    }

    public void displayfordrawer()
    {
        DataBaseHelper dbhelpkun = new DataBaseHelper(this);
        Bundle b = dbhelpkun.readformainfrag();
        if(b!=null) {
            name = b.getString("name");
        }
        String a = "Welcome\n"+name;
        drawertxt.setText(a);

        //for image
        Intent i1 = dbhelpkun.readfromdb(this);
        String check = i1.getStringExtra("imgchecker");
        if(!check.equals("novalue"))
        {
            Bundle b1 = i1.getExtras();
            if (b1 != null)
            {
                Uri imguri =Uri.parse(b1.getString(DataBaseHelper.COL_IMAGE));
                drawerimg.setImageURI(imguri);
            }
        }
       
        dbhelpkun.close();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if(!b)
            {
                ft.hide(mainFragment);
            }
            else
            {
                if(mainFragment.isVisible())
                    ft.show(mainFragment);
            }
            ft.commit();
        flagy = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putBoolean("mainfragdisplayflag",flagy);
        outState.putBoolean("mainfragflagpls",b);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        this.flagy = savedInstanceState.getBoolean("mainfragdisplayflag");
        this.b =  savedInstanceState.getBoolean("mainfragflagpls");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem)
    {
        drawerLayout.closeDrawer(GravityCompat.START);
        menuitem.setChecked(true);
        collapsingToolbarLayout.setTitle(menuitem.getTitle());
        appBarLayout.setExpanded(true);
        fragmentTransaction= fragmentManager.beginTransaction();

        if((menuitem.getItemId()==R.id.home))
        {
            // main
            int imageId = (int)(Math.random() * images.length);
            collapsingToolbarLayout.setTitle("Academic Tracker");
            collapse_img.setImageResource(images[imageId]);
                fragmentTransaction.remove(mainFragment);
                fragmentTransaction.add(R.id.container_fragment,mainFragment);
                fragmentTransaction.commit();
        }

        if((menuitem.getItemId()==R.id.personal))
        {
            // Personal fragment
            int imageId = (int)(Math.random() * images.length);
            collapse_img.setImageResource(images[imageId]);
            fragmentTransaction.replace(R.id.container_fragment,personalFragment,"personalfrag1");
            fragmentTransaction.addToBackStack(("personb"));
            fragmentTransaction.commit();
            b = false;

        }
        if((menuitem.getItemId()==R.id.tenth))
        {
            int imageId = (int)(Math.random() * images.length);
          //  collapse_img.setBackgroundResource(images[imageId]);
            collapse_img.setImageResource(images[imageId]);
            fragmentTransaction.replace(R.id.container_fragment,tenthFragment,"tenthfrag1");
            fragmentTransaction.addToBackStack(("tenthb"));
            fragmentTransaction.commit();
            b = false;
        }
        if((menuitem.getItemId()==R.id.twelfth))
        {
            int imageId = (int)(Math.random() * images.length);
            collapse_img.setImageResource(images[imageId]);
            fragmentTransaction.replace(R.id.container_fragment,twelfthFragment,"twelfthfrag1");
            fragmentTransaction.addToBackStack(("twelfthb"));
            fragmentTransaction.commit();
            b = false;
        }
        if((menuitem.getItemId()==R.id.cgpa))
        {
            int imageId = (int)(Math.random() * images.length);
            collapse_img.setImageResource(images[imageId]);
            fragmentTransaction.replace(R.id.container_fragment, cgpaFrag, "cgpafrag");
            fragmentTransaction.addToBackStack(("cgpafr"));
            fragmentTransaction.commit();
            b = false;
        }
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(mbr, filter);
    }
    @Override
    public void onBackPressed()
    {
        b = true;
        if(mainFragment.isVisible())
        {
            super.onBackPressed();
        }
        else
        {
            super.onBackPressed();
            fragmentManager.popBackStack("mainfrag1",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        //super.onBackPressed();
        //fragmentManager.popBackStack();
        collapsingToolbarLayout.setTitle("Academic Tracker");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            Uri selectedImage = data.getData();

            simgbox = (ShapeableImageView) findViewById(R.id.shapeableImageView);
            simgbox.setImageURI(selectedImage);

            try
            {
                if(personalFragment.isAdded())
                {
                    personalFragment.sendimguri(selectedImage);
                   // Toast.makeText(this,"Success ",Toast.LENGTH_SHORT).show();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

    }


    @Override
    public void passintentfromfrag(Intent i, int n)

    {
        ImagePicker.Companion.with(MainActivity.this)
                .crop(400,400)
                .galleryOnly()
                //  .maxResultSize(400,400)
                .compress(1024)
                .start();
        //startActivityForResult(i, n);
    }

    @Override
    public void passdetails(String name)
    {
        name1 = name;
        mainFragment.getfromactivity(name);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public void showsemcaller()
    {
        cgpaFrag.showSemester();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.menu_save)
        {
            if(checkStoragePermission()){
                sqlImportExport.exportDB();
            }
            else
                requestStoragePermissionExport();

        }
        if(item.getItemId()==R.id.menu_restore)
        {
            if(checkStoragePermission()){
                sqlImportExport.importDB();
                if(personalFragment.isAdded())
                {
                    personalFragment.readFromDatabase();
                }
                if(tenthFragment.isAdded())
                {
                    tenthFragment.showvalues();
                }
                if(twelfthFragment.isAdded())
                {
                    twelfthFragment.showvalues();
                }
                if(cgpaFrag.isAdded())
                {
                    cgpaFrag.showDept();
                    cgpaFrag.showSemester();
                }
                if(fragmentGrade.isAdded())
                {
                 //   Toast.makeText(this,"fragment grade",Toast.LENGTH_SHORT).show();
                }
                //need to update textfield
            }
            else
                requestStoragePermissionImport();
        }
        if((item.getItemId()==R.id.exit))
        {
            collapsingToolbarLayout.setTitle("Academic Tracker");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        if(item.getItemId()==R.id.about)
        {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            LayoutInflater inflater = this.getLayoutInflater();

            View dialogView= inflater.inflate(R.layout.about_us, null);
            dialogBuilder.setView(dialogView);

            dialogBuilder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean checkStoragePermission()
    {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermissionImport()
    {
        ActivityCompat.requestPermissions(this, storagePermissions,STORAGE_REQUEST_CODE_IMPORT);
    }

    private void requestStoragePermissionExport()
    {
        ActivityCompat.requestPermissions(this, storagePermissions,STORAGE_REQUEST_CODE_EXPORT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case STORAGE_REQUEST_CODE_EXPORT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sqlImportExport.exportDB();
                } else {
                    Toast.makeText(this, "Storage Permission required", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE_IMPORT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sqlImportExport.importDB();
                    //need to update check

                } else {
                    Toast.makeText(this, "Storage Permission required", Toast.LENGTH_SHORT).show();
                }
            }
            break;

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mbr);

}}