package com.cvsu.ash.rhea.fishapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToolsActivity extends AppCompatActivity {
    private static Button btn1;
    private static Button btn2;
    private static Button btn3;
    private static Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        btn1 =(Button)findViewById(R.id.button1);
        btn2 =(Button)findViewById(R.id.button2);
        btn3 =(Button)findViewById(R.id.button3);
        btn4 =(Button)findViewById(R.id.button4);

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked4();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked3();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked2();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked();
            }
        });
    }

    private void onBtnClicked4() {
        Intent intent = new Intent("android.intent.action.Tool4Activity");
        startActivity(intent);
    }

    private void onBtnClicked3() {
        Intent intent = new Intent("android.intent.action.Tool3Activity");
        startActivity(intent);
    }

        private void onBtnClicked2() {
        Intent intent = new Intent("android.intent.action.Tool2Activity");
        startActivity(intent);
    }

    private void onBtnClicked() {
        Intent intent = new Intent("android.intent.action.Tool1Activity");
        startActivity(intent);
    }
}
