package com.cvsu.ash.rhea.fishapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private IntentFilter networkChangeFilter;
    public static final String NETWORK_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private NetworkChangeStateReceiver networkChangeReceiver;
    private GoogleMap mMap;
    private double lat, lng;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private LatLng latLng, Goto;
    private DBLoader dbLoader;
    private Marker mMarker;
    private Location mLocation;
    private GoogleApiClient mClient;
    private Button fab;
    private String fName;
    private String sName;
    private double latitude;
    private double longitude;
    private AlertDialog alertLoc;
    private double latStorage = 0.0;
    private double lngStorage = 0.0;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        dbLoader = new DBLoader(MapsActivity.this);
        final GlobalFunction func = new GlobalFunction();
        checkLocationService();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (mClient == null) {
            buildGoogleApiClient();
            mClient.connect();
        }
        networkChangeFilter = new IntentFilter(NETWORK_CHANGE_ACTION);
        networkChangeReceiver = new NetworkChangeStateReceiver();
        fab = (Button) findViewById(R.id.fab);

        if (GlobalVariables.gPin) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }

    }

    public void animateToLocationFromRecyclerView(double lat, double lng, String street, View view) {
        GoogleMap gMap = mMap;
        String s = street;
        alertLoc.dismiss();
        if (marker != null) {
            marker.remove();
        }

        if (latStorage != lat && lngStorage != lng) {

            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng))      // Sets the center of the map to location user
                    .zoom(32)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

    }

    private void buildGoogleApiClient() {
        mClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void checkLocationService() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("GPS Network is disabled.");
            dialog.setPositiveButton("Open location Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    private void addMarkersToMap(double lat, double lon) {
        ArrayList<LocationModel> list  = dbLoader.getFacilities();
        int height = 200;
        int width = 200;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.mapmarker);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);


        if (!GlobalVariables.gPin) {
            for (int ctr = 0; ctr < list.size(); ctr++) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(list.get(ctr).getLatitude(), list.get(ctr).getLongitude()))
                        .title(list.get(ctr).getTypeoOfFarm())
                        .snippet(list.get(ctr).getAddress())
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                mMap.addMarker(markerOptions);
            }
        } else {
            Bundle intent = getIntent().getExtras();
            fName = intent.getString("Name");
            sName = intent.getString("Street");
            latitude = intent.getDouble("latitude");
            longitude = intent.getDouble("longitude");
            if (intent != null) {
                double latitude = intent.getDouble("latitude");
                double longitude = intent.getDouble("longitude");
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(fName)
                        .snippet(sName);
                mMap.addMarker(markerOptions);

                Goto = new LatLng(markerOptions.getPosition().latitude, markerOptions.getPosition().longitude);
            } else {

            }
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getBaseContext(), MarkerInfo.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", marker.getPosition().latitude);
                bundle.putDouble("longitude", marker.getPosition().longitude);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.animateCamera(cameraUpdate);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        addMarkersToMap(location.getLatitude(), location.getLongitude());

        locationManager.removeUpdates(this);

    }

    private String getDirectionsUrl(LatLng latLng, LatLng Goto) {

        String mSource = "origin=" + latLng.latitude + "," + latLng.longitude;
        String mDestination = "destination=" + Goto.latitude + "," + Goto.longitude;
        String mSensor = "sensor=false";
        String mMode = "mode=driving";
        String mParameters = mSource + "&" + mDestination + "&" + mMode + "&" + mSensor;
        String mOutput = "json";
        String murl = "https://maps.googleapis.com/maps/api/directions/" + mOutput + "?" + mParameters;
        return murl;
    }

    private String downloadUrl(String mUrl) throws IOException {
        String mdata = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(mUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String mLine = "";
            while ((mLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(mLine);
            }
            mdata = stringBuffer.toString();
            bufferedReader.close();
        } catch (Exception e) {

        } finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return mdata;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);
        if (mLocation != null) {
            lat = mLocation.getLatitude();
            lng = mLocation.getLongitude();
            latLng = new LatLng(lat, lng);
            addMarkersToMap(lat, lng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            if (GlobalVariables.gPin) {
                String url = getDirectionsUrl(latLng, Goto);
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(url);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Finding routes...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... url) {
            String mData = "";
            try {
                mData = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            Log.d("Background Task", mData);
            return mData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
            progressDialog.dismiss();
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, ArrayList<LatLng>> {
        ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);
        List<List<HashMap<String, String>>> textRoutes = new ArrayList<>();
        List<List<HashMap<String, Double>>> textRoutesPoints = new ArrayList<>();
        ArrayList<Directions> listPathText;
        boolean cannotRoute = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Finding routes...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<LatLng> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = new ArrayList<>();
            ArrayList<LatLng> points = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser directionsJSONParser = new DirectionsJSONParser();
                routes = directionsJSONParser.parse(jObject);
                if(jObject.getString("status").equalsIgnoreCase("ZERO_RESULTS")) {
                    cannotRoute = true;
                } else {
                    Log.d(getClass().getSimpleName(), "Result: " + routes.toArray().toString() + " Result size: " + routes.size());
                    for (int i = 0; i < routes.size(); i++) {
                        Log.d(getClass().getSimpleName(), "Loop: " + i);
                        points = new ArrayList<LatLng>();

                        List<HashMap<String, String>> path = routes.get(i);

                        for (int j = 0; j < path.size(); j++) {
                            Log.d(getClass().getSimpleName(), "Loop inner: " + j);
                            HashMap<String, String> latLngss = path.get(j);

                            double lat = Double.parseDouble(latLngss.get("lat"));
                            double lng = Double.parseDouble(latLngss.get("lng"));
                            LatLng position = new LatLng(lat, lng);
                            Log.d(getClass().getSimpleName(), "Hash : " + latLngss.toString());
                            points.add(position);
                        }
                    }
                }

                GlobalVariables.points = points.size();
                textRoutes = directionsJSONParser.parseText(jObject);
                textRoutesPoints = directionsJSONParser.parsePointsOfDirections(jObject);

                for (int i = 0; i < textRoutes.size(); i++) {
                    Log.d(getClass().getSimpleName(), "Loop: " + i);
                    List<HashMap<String, String>> textPath = textRoutes.get(i);
                    List<HashMap<String, Double>> textPathPoints = textRoutesPoints.get(i);
                    listPathText = new ArrayList<Directions>();
                    Directions direction1 = new Directions();
                    for (int j = 0; j < textPath.size(); j++) {
                        Directions directions = new Directions();
                        Log.d(getClass().getSimpleName(), "Loop inner: " + j);
                        HashMap<String, String> paths = textPath.get(j);
                        HashMap<String, Double> pathsPoints = textPathPoints.get(j);
                        directions.setDirection(paths.get("html_instructions"));
                        directions.setManeuver(paths.get("maneuver"));
                        directions.setStreetName("");
                        directions.setFacilityName("");
                        directions.setLat(pathsPoints.get("lat"));
                        directions.setLng(pathsPoints.get("lng"));

                        listPathText.add(directions);
                    }
                    direction1.setDirection("");
                    direction1.setManeuver("");
                    direction1.setLat(latitude);
                    direction1.setLng(longitude);
                    direction1.setFacilityName(fName);
                    direction1.setStreetName(sName);
                    listPathText.add(direction1);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return points;
        }

        @Override
        protected void onCancelled() {
            progressDialog.dismiss();
        }

        @Override
        protected void onPostExecute(final ArrayList<LatLng> points) {
            progressDialog.dismiss();
            if (points != null) {
                mMap.addPolyline(new PolylineOptions()
                        .addAll(points)
                        .width(5)
                        .color(Color.BLUE));
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = (LayoutInflater) MapsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View promptsView = inflater.inflate(R.layout.text_directions_container, null);
                        AlertDialog.Builder alerDialog = new AlertDialog.Builder(MapsActivity.this);
                        alerDialog.setView(promptsView);
                        alerDialog.setCancelable(true);
                        RecyclerView rvDirections = (RecyclerView) promptsView.findViewById(R.id.rvTextDirections);
                        rvDirections.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        TextDirectionsRecyclerView adapter = new TextDirectionsRecyclerView(MapsActivity.this, listPathText, fName, sName);
                        rvDirections.setAdapter(adapter);

                        rvDirections.setClickable(true);
                        alertLoc = alerDialog.create();
                        alertLoc.show();
                    }
                });
            }else {
                if(cannotRoute) {
                    GlobalFunction func = new GlobalFunction();
                    func.showDialog(MapsActivity.this, "", "Route not yet available.");
                    fab.setVisibility(View.GONE);
                } else {
                    GlobalFunction func = new GlobalFunction();
                    func.showDialog(MapsActivity.this, "", "Oops. Something went wrong.");
                    fab.setVisibility(View.GONE);
                }

            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMyLocationButtonClick() {

        //  Toast.makeText(this, "You Are Here", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onResume() {
        this.registerReceiver(networkChangeReceiver, networkChangeFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        this.unregisterReceiver(networkChangeReceiver);

        super.onPause();
    }


}
