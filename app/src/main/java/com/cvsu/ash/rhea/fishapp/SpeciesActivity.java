package com.cvsu.ash.rhea.fishapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SpeciesActivity extends AppCompatActivity {

    ListView listView;

    String items[] = new String [] {"Bangus","Tilapia","Lapu-lapu"};
    String descr[] = new String [] {getResources().getString(R.string.descript),"Nile Tilapia","Grouper"};
    Integer[] imgid = {R.drawable.milkfish,R.drawable.nile_tilapia,R.drawable.grouper};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species);

        listView = findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),milkfish.class);
                intent.putExtra("name",items[i]);
                intent.putExtra("image",imgid[i]);
                intent.putExtra("desc",descr[i]);
                startActivity(intent);
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return imgid.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
         View view1 = getLayoutInflater().inflate(R.layout.row_data,null);

            TextView name = view1.findViewById(R.id.fruits);
            TextView desc = view1.findViewById(R.id.description);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(items[i]);
            desc.setText(descr[i]);
            image.setImageResource(imgid[i]);

            return view1;
        }
    }
}
