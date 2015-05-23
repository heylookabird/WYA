package com.example.agcostfu.wya;
/*
Launcher Activity that handles getting phone number from the user and gives the users ways to get
into a group: create or check if he/she has been invited.  Does not send or receive data from the
server.


 */


import android.content.Context;
import android.os.StrictMode;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import com.example.agcostfu.server.AddToGroupClient;
import com.example.agcostfu.server.CreateGroupClient;
import com.example.agcostfu.server.GetInvitationsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;
import java.lang.String;


public class LonelyMapActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    GPSTracker gps;
    double lat;
    double lng;
    boolean invitesVisible = false;
    static String number;
    Button viewInvites, add1, add2, add3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmap_activity);
        SplashScreen splash = new SplashScreen();
        enableStrictMode();
        gps = new GPSTracker(LonelyMapActivity.this);
        lat = gps.getLocation().getLatitude();
        lng = gps.getLocation().getLongitude();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        number = tm.getLine1Number();
        setUpMapIfNeeded();
        mMap.getMyLocation();
        setUpButtons();
        hideButtons();

        setUpMapIfNeeded();
    }

    private void setUpButtons(){
        viewInvites = (Button) findViewById(R.id.button);
        viewInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!invitesVisible) {
                    showButtons();
                    ArrayList<String> invites = new GetInvitationsClient(number).getInvites();

                    for(int i= 0; i < invites.size(); i++){
                        if(invites.get(i).length() < 9)
                            invites.remove(i);
                    }

                    int size = invites.size();
                    if (size > 0)
                        add1.setText(invites.get(0));
                    if (size > 1)
                        add2.setText(invites.get(1));
                    if (size > 2)
                        add3.setText(invites.get(2));
                }else
                    hideButtons();
            }
        });
        add1 = (Button) findViewById(R.id.button1);
        add2 = (Button) findViewById(R.id.button2);
        add3 = (Button) findViewById(R.id.button3);

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invitesVisible) {
                    if(add1.getText().length() > 0) {
                        new CreateGroupClient("this", number, "this");
                        new AddToGroupClient(add1.getText().toString(), number);
                        Intent i = new Intent(LonelyMapActivity.this, GroupMapActivity.class);
                        LonelyMapActivity.this.startActivity(i);
                    }
                }
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invitesVisible)
                    if(add2.getText().length() > 0) {
                        new AddToGroupClient(number, add2.getText().toString());
                        Intent i = new Intent(LonelyMapActivity.this, GroupMapActivity.class);
                        LonelyMapActivity.this.startActivity(i);
                    }
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invitesVisible) {
                    if(add3.getText().length() > 0) {
                        new AddToGroupClient(number, add3.getText().toString());
                        Intent i = new Intent(LonelyMapActivity.this, GroupMapActivity.class);
                        LonelyMapActivity.this.startActivity(i);
                    }
                }
            }
        });
    }

    private void showButtons(){
        invitesVisible = true;
        add1.setAlpha(255);
        add2.setAlpha(255);
        add3.setAlpha(255);
    }

    private void hideButtons(){
        invitesVisible = false;
        add1.setAlpha(0);
        add2.setAlpha(0);
        add3.setAlpha(0);
    }
    public static String getThisNumber(){
        return number;
    }

    public void enableStrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
    private void setUpMap() {


        LatLng current = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());


        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(current).title("test").snippet("test").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        //this sets the view of the map zoomed such that the user's current
        //location and the second users location is visible.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // This addMarker sets the location of the second User in Downtown SF.
    }


    //Creates the menu options in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main_maps, menu);
            return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Creates a new Group and Activity
        switch (item.getItemId()) {
            case R.id.action_create_group:
                Intent newGroup = new Intent(LonelyMapActivity.this, CreateNewGroup.class);
                LonelyMapActivity.this.startActivity(newGroup);
                return true;

            case R.id.action_settings:
                Intent settings = new Intent(LonelyMapActivity.this, Settings.class);
                LonelyMapActivity.this.startActivity(settings);
                return true;
        }

        return false;


        //send button*/

    }
}








