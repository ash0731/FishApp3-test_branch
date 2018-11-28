package com.cvsu.ash.rhea.fishapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
            loc.setNumberOfFarm(res.getString(2));
            loc.setCulturedSpecies(res.getString(4));
            loc.setTypeoOfFarm(res.getString(3));
            loc.setSourceOfWater(res.getString(5));
            loc.setLatitude(res.getDouble(6));
            loc.setLongitude(res.getDouble(7));
            list.add(loc);
        }
        return list;
    }

    public MarkerInfoModel getMarkerInfo(double lat, double lang) {
        MarkerInfoModel MarkerInfoModel = null;
        sql = "SELECT * FROM locationtbl WHERE Latitude like'%" +lat+"%' and Longitude like '%"+lang+"%'";
        db = this.getWritableDatabase();
        res = db.rawQuery(sql, null);

        while(res.moveToNext()) {
            MarkerInfoModel = new MarkerInfoModel();
            MarkerInfoModel.setID(res.getString(0));
            MarkerInfoModel.setAddress(res.getString(1));
            MarkerInfoModel.setNumberOfFarm(res.getString(2));
            MarkerInfoModel.setCulturedSpecies(res.getString(4));
            MarkerInfoModel.setTypeoOfFarm(res.getString(3));
            MarkerInfoModel.setSourceOfWater(res.getString(5));
            MarkerInfoModel.setLatitude(res.getDouble(6));
            MarkerInfoModel.setLongitude(res.getDouble(7));

        }

        return  MarkerInfoModel;
    }


}
