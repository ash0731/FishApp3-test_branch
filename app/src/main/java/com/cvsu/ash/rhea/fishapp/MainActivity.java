package com.cvsu.ash.rhea.fishapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 20;
    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);

            }
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void Learn(View view) {
                Intent i = new Intent(this, LearnActivity.class);
                startActivity(i);
            }

             public void Map(View view) {
                 Intent a = new Intent(this, MapsActivity.class);
                 startActivity(a);
             }
    public void Map2(View view) {
        Intent b = new Intent(this,MapsActivity.class);
        startActivity(b);
    }



    //The lines after this are for ExitButton or Back Exit
    public void clickExit(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_account) {
            Intent i = new Intent(this, About.class);
            startActivity(i);
        } else if (id == R.id.nav_account2) {
            Intent i = new Intent(this, References.class);
            startActivity(i);
            //Dito yung feedbackactivity

        } else if (id == R.id.nav_account3) {
            Intent i = new Intent(this, contact.class);
            startActivity(i);
            //Dito yung feedbackactivity

        }
        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
//Exit Ends here

}
