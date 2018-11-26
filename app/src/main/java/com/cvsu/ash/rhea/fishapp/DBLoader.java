package com.cvsu.ash.rhea.fishapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DBLoader extends DBHandler {
    private SQLiteDatabase db;
    private Cursor res;
    private String sql = "";
    private Context mContext;

    //QUERY SA DATABASE
    public DBLoader(Context context) {
        super(context);
        mContext = context;
    }

    public ArrayList<LocationModel> getFacilities() {

        ArrayList<LocationModel> list = new ArrayList<>();
        sql = "SELECT * FROM locationtbl";
        db = this.getWritableDatabase();
        res = db.rawQuery(sql, null);
        while(res.moveToNext()) {
            LocationModel loc = new LocationModel();
            loc.setID(res.getString(0));
            loc.setAddress(res.getString(1));
            loc.setNumberOfFarm(res.getString(3));
            loc.setTypeoOfFarm(res.getString(4));
            loc.setSourceOfWater(res.getString(5));
            loc.setLatitude(res.getDouble(6));
            loc.setLongitude(res.getDouble(7));
            list.add(loc);
        }
        return list;
    }


}
