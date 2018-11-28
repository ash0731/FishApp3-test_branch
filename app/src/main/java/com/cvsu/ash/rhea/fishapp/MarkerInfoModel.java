package com.cvsu.ash.rhea.fishapp;

//LALAGYAN NG DATA GALING SA DATABASE
public class MarkerInfoModel {
    private String ID;
    private String Address;
    private String NumberOfFarm;
    private String TypeoOfFarm;
    private String CulturedSpecies;
    private String SourceOfWater;
    private double latitude;
    private double longitude;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNumberOfFarm() {
        return NumberOfFarm;
    }

    public void setNumberOfFarm(String numberOfFarm) {
        NumberOfFarm = numberOfFarm;
    }

    public String getTypeoOfFarm() {
        return TypeoOfFarm;
    }

    public void setTypeoOfFarm(String typeoOfFarm) {
        TypeoOfFarm = typeoOfFarm;
    }

    public String getCulturedSpecies() {
        return CulturedSpecies;
    }

    public void setCulturedSpecies(String culturedSpecies) {
        CulturedSpecies = culturedSpecies;
    }

    public String getSourceOfWater() {
        return SourceOfWater;
    }

    public void setSourceOfWater(String sourceOfWater) {
        SourceOfWater = sourceOfWater;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }




}
