package com.example.agcostfu.wya;

import android.annotation.TargetApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import static com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import com.example.agcostfu.wya.GPSTracker;
import  com.example.agcostfu.wya.Location;
import com.example.agcostfu.wya.SplashScreen;




public class MainMapsActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    GPSTracker gps;
    static LatLng nearest;
    static String name;
    double radius = 5.0;
    double lat;
    double lng;

    static TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmap_activity);
        SplashScreen splash = new SplashScreen();
        gps = new GPSTracker(MainMapsActivity.this);
        lat = gps.getLocation().getLatitude();
        lng = gps.getLocation().getLongitude();



        //setUpMap();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
// Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
// Try to obtain the map from the SupportMapFragment.
           //try{
               mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.the_map))
                       .getMap();
          // }catch(Exception e){
               //e.printStackTrace();
           //}
// Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng current = new LatLng(gps.getLocation().getLatitude(),gps.getLocation().getLongitude());

        mMap.addMarker(new MarkerOptions()
                .position(current)
                .title("USER 1")
                .snippet("This is User 1's current location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        mMap.setMyLocationEnabled(true);
        //this sets the view of the map zoomed such that the user's current
        //location and the second users location is visible.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 12));

        mMap.getMyLocation();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // This addMarker sets the location of the second User in Downtown SF.
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.773972, -122.431297))
                .title("USER 2")
                .snippet("This is User 2's Current Position.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        Toast.makeText(getApplicationContext(),
                "WELCOME!",
                Toast.LENGTH_LONG).show();

        // This set the function for long press on the map.
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Your location : ")
                        .snippet("lat : " + latLng.latitude + "\nlng : " + latLng.longitude)
                        .draggable(true));
                lat = latLng.latitude;
                lng = latLng.longitude;
                Toast.makeText(getApplicationContext(),
                        "NEW MARKER SET!",
                        Toast.LENGTH_LONG).show();
            }

        });

        mMap.setOnMarkerDragListener(new OnMarkerDragListener() {
                                         @Override
                                         public void onMarkerDragStart(Marker marker) {
                                         }

                                         @Override
                                         public void onMarkerDrag(Marker marker) {

                                         }

                                         @Override
                                         public void onMarkerDragEnd(Marker marker) {
                                             mMap.clear();
                                             LatLng latLng = marker.getPosition();
                                             mMap.addMarker(new MarkerOptions().position(latLng)
                                                     .title("Your location : ")
                                                     .draggable(true)
                                                     .snippet("lat : " + latLng.latitude + "\nlng : " + latLng.longitude));
                                             lat = latLng.latitude;
                                             lng = latLng.longitude;
                                             Toast.makeText(getApplicationContext(),
                                                     "Lat : " + latLng.latitude + " ",
                                                     Toast.LENGTH_LONG).show();
                                         }
                                     }

        );


    }


    //Creates the menu options in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_maps, menu);
        return super.onCreateOptionsMenu(menu);
    }
}