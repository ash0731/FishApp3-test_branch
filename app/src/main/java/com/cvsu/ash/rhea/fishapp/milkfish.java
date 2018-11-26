package com.cvsu.ash.rhea.fishapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class milkfish extends AppCompatActivity {
    TextView name;
    TextView description;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milkfish);

        name = findViewById(R.id.listdata);
        description = findViewById(R.id.description);
        image = findViewById(R.id.imageView);

        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        description.setText(intent.getStringExtra("description"));
        image.setImageResource(intent.getIntExtra("image",0));


    }
}
