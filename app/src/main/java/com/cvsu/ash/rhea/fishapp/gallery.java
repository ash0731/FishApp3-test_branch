package com.cvsu.ash.rhea.fishapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            onBtnClicked();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked2();

            }
        });

    }
    private void onBtnClicked2() {
        Intent intent = new Intent("android.intent.action.video");
        startActivity(intent);
    }

    private void onBtnClicked() {
        Intent intent = new Intent("android.intent.action.photos");
        startActivity(intent);
    }
}
