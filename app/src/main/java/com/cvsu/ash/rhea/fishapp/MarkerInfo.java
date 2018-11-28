package com.cvsu.ash.rhea.fishapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MarkerInfo extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private TextView tv_SourceOfWater, tv_CulturedOfFarm, tv_NoOfFarm, tv_Address, tv_TypeOfFarm;
    private Button btn_setPin;
    private IntentFilter networkChangeFilter;
    private NetworkChangeStateReceiver networkChangeReceiver;
    public static final String NETWORK_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    MarkerInfoModel MarkerInfoModel;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);

        networkChangeFilter = new IntentFilter(NETWORK_CHANGE_ACTION);
        networkChangeReceiver = new NetworkChangeStateReceiver();
        GlobalVariables.gPin = false;
        tv_SourceOfWater = (TextView) findViewById(R.id.tv_SourceOfWater);
        tv_CulturedOfFarm = (TextView) findViewById(R.id.tv_CulturedOfFarm);
        tv_NoOfFarm = (TextView) findViewById(R.id.tv_NoOfFarm);
        tv_Address = (TextView) findViewById(R.id.tv_Address);
        tv_TypeOfFarm = (TextView) findViewById(R.id.tv_TypeOfFarm);
        btn_setPin = (Button) findViewById(R.id.btn_setPin);

        Bundle intent = getIntent().getExtras();
        latitude = intent.getDouble("latitude");
        longitude = intent.getDouble("longitude");

        DBLoader dbLoader = new DBLoader(this);
        MarkerInfoModel = dbLoader.getMarkerInfo(latitude, longitude);

        if (MarkerInfoModel != null) {

            tv_SourceOfWater.setText(MarkerInfoModel.getSourceOfWater());
            tv_CulturedOfFarm.setText(MarkerInfoModel.getCulturedSpecies());
            tv_NoOfFarm.setText(MarkerInfoModel.getNumberOfFarm());
            tv_Address.setText(MarkerInfoModel.getAddress());
            tv_TypeOfFarm.setText(MarkerInfoModel.getTypeoOfFarm());

        }

        if (GlobalVariables.internet) {
            btn_setPin.setVisibility(View.VISIBLE);
        } else {
            btn_setPin.setVisibility(View.GONE);
        }

        btn_setPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVariables.internet) {
                    GlobalVariables.gPin = true;
                    Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putDouble("latitude", latitude);
                    bundle.putDouble("longitude", longitude);
                    bundle.putString("Name", MarkerInfoModel.getTypeoOfFarm());
                    bundle.putString("Street", MarkerInfoModel.getAddress());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MarkerInfo.this);
                    alert.setMessage("Can't pin your location, you are currently offline.");
                    alert.setCancelable(false);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
                //Add codes here

            }
        });


    }
}
