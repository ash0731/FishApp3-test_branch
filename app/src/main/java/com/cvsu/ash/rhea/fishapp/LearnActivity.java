package com.cvsu.ash.rhea.fishapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnActivity extends AppCompatActivity {
    //android:id="@+id/button6
    private static Button btn1;
    private static Button btn2;
    private static Button btn3;
    private static Button btn4;
    private static Button btn5;
    private static Button btn6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        getSupportActionBar().setTitle("Discover");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn1 =(Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 =(Button)findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 =(Button)findViewById(R.id.button6);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked2();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked3();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked4();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked5();
            }
        });


        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartClicked();

    }
});

    }

    private void onBtnClicked5() {
        Intent intent = new Intent("android.intent.action.CMethodActivity");
        startActivity(intent);
    }

    private void onBtnClicked3() {
        Intent intent = new Intent("android.intent.action.HistoryActivity");
        startActivity(intent);
    }

    public void onBtnClicked(){
        Intent intent = new Intent("android.intent.action.SpeciesActivity");
        startActivity(intent);

    }
    public void onBtnClicked2(){
        Intent intent = new Intent("android.intent.action.FeedsActivity");
        startActivity(intent);

    }
    public void onBtnClicked4(){
        Intent intent = new Intent("android.intent.action.ToolsActivity");
        startActivity(intent);

    }

    public void onStartClicked(){
        Intent intent = new Intent("android.intent.action.RulesActivity");
        startActivity(intent);

    }

}
