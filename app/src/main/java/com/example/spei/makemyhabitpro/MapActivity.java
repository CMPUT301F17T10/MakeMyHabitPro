/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST_CODE = 101;
    private String TAG = "MapDemo";
    private GoogleMap mMap;
    private LocationManager locationManager;
    private double lat;
    private double lng;
    private String user_data;
    private User local_user;
    private ArrayList<String> friends;
    private ArrayList<Event> eventList;
    private static final String FILENAME="Eventl.SAV";
    private ArrayList<String> uids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent uintent = getIntent();
        user_data=  uintent.getStringExtra(LogInActivity.EXTRA_MESSAGE);
        Gson gson = new Gson();
        local_user=gson.fromJson(user_data,User.class);
        friends=local_user.getFriends();
        uids=new ArrayList<String>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled((LocationManager.NETWORK_PROVIDER)))) {
            Toast.makeText(this, "Please open network or GPS", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }

        try {
            Location location;
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                Log.d(TAG, "onCreate.location = null");
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }


            Log.d(TAG, "onCreate.location =" + location);
            updateView(location);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 5, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                LOCATION_REQUEST_CODE);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

    }
    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
        getUid();
    }
    private void getUid(){
        uids.clear();
        String uid;
        for(String s:friends){
            uid=findUid(s);
            if (uid!=null) {
                uids.add(uid);
            }

        }

    }
    private String findUid(String s){
        ElasticsearchUser.GetUserTask g=new ElasticsearchUser.GetUserTask();
        g.execute(s);
        User u;
        try{
            u=g.get();
            return u.getUid();
        }catch (Exception e){
            return null;
        }
    }
    @Override
    protected void onDestroy() {
        try {
            locationManager.removeUpdates(locationListener);
        } catch (SecurityException e) {
        }
        super.onDestroy();
    }
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onProviderDisabled.location = " + location);
            updateView(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "onStatusChanged() called with " + "provider = [" + provider + "], status = [" + status + "], extras = [" + extras + "]");
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled() called with " + "provider = [" + provider + "]");
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                Log.d(TAG, "onProviderDisabled.location = " + location);
                updateView(location);
            } catch (SecurityException e) {

            }

        }
        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled() called with " + "provider= [" + provider + "]");
        }
    };
    private void updateView(Location location) {
        Geocoder gc = new Geocoder(this);
        List<Address> addresses = null;
        String msg = "";
        Log.d(TAG, "updateView.location=" + location);
        if (location != null) {
            try {
                addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Log.d(TAG, "updateView.addresses= " + addresses);
                if (addresses.size() > 0) {
                    msg += addresses.get(0).getAdminArea().substring(0, 2);
                    msg += " " + addresses.get(0).getLocality().substring(0, 2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            lng=location.getLongitude();
            lat =location.getLatitude();
        }
    }

    protected void requestPermission(String permissionType, int
            requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //lat=53.5;
        //lng=-113.5;


        if (mMap != null) {
            LatLng POSITION = new LatLng(lat, lng);
            Marker museum = mMap.addMarker(new MarkerOptions()
                    .position(POSITION)
                    .title("My Location"));
            //      .snippet("National Air and Space Museum"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(POSITION));
            for (Event e: eventList){
                String u=e.getUID();
                if (!uids.contains(u)){
                    continue;
                }
                Double la=e.getLatitude();
                Double lon=e.getLontitude();
                LatLng p=new LatLng(la,lon);
                if (CalculationByDistance(POSITION,p)>5){
                    Marker m=mMap.addMarker(new MarkerOptions().position(p).title(e.getHabit().getTitle()).icon(BitmapDescriptorFactory.defaultMarker(200)));
                }else{
                    Marker m=mMap.addMarker(new MarkerOptions().position(p).title(e.getHabit().getTitle()).icon(BitmapDescriptorFactory.defaultMarker(100)));
                }


            }

        }

    }
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]  grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this,"Unable to show location permission required",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Type listType = new TypeToken<ArrayList<Event>>() {
            }.getType();

            eventList = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            eventList = new ArrayList<Event>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}