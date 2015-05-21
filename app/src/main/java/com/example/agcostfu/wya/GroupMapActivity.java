package com.example.agcostfu.wya;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;


import com.example.agcostfu.main.PictureNode;
import com.example.agcostfu.main.Tag;
import com.example.agcostfu.server.AddTagToGroupClient;
import com.example.agcostfu.server.CreateGroupClient;
import com.example.agcostfu.server.GetGroupChatClient;
import com.example.agcostfu.server.GetGroupLocationClient;
import com.example.agcostfu.server.GetGroupPictureLocationClient;
import com.example.agcostfu.server.GetPictureFromServerClient;
import com.example.agcostfu.server.InviteClient;
import com.example.agcostfu.server.UpdatingClient;
import com.example.agcostfu.users.User;

import com.example.agcostfu.main.Main;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import android.provider.ContactsContract;

import static com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import static com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.util.StringTokenizer;

import com.example.agcostfu.wya.GPSTracker;
import com.example.agcostfu.wya.Location;
import com.example.agcostfu.wya.SplashScreen;


import java.lang.String;


public class GroupMapActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    GPSTracker gps;
    static LatLng nearest;
    static String name;
    double radius = 5.0;
    double lat;
    double lng;
    int chatLocation;
    private ArrayList<PictureNode> groupPics;
    Button invite;

    Handler updateHandler;
    static String number;


    static TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_map);
        SplashScreen splash = new SplashScreen();
        enableStrictMode();
        gps = new GPSTracker(GroupMapActivity.this);
        lat = gps.getLocation().getLatitude();
        lng = gps.getLocation().getLongitude();
        updateHandler = new Handler();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        number = tm.getLine1Number();
        System.out.println(number);
        test();
        chatLocation = 0;
        //setUpMap();
        setUpMapIfNeeded();
        startPeriodicUpdate();
        mMap.getMyLocation();


        groupPics = new ArrayList<PictureNode>();

        String name = "test";

        invite = (Button) findViewById(R.id.button);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });

        // This set the function for long press on the map.
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String title = "Test tag ";
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(title)
                        .snippet("need to add something for them to title"));
                lat = latLng.latitude;
                lng = latLng.longitude;
                Toast.makeText(getApplicationContext(),
                        "NEW MARKER SET!",
                        Toast.LENGTH_LONG).show();

                new AddTagToGroupClient(number, title, ""+lat, ""+lng);
            }

        });

        mMap.setOnMarkerClickListener(new OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(Marker marker) {
                /*LatLng pos = marker.getPosition();
                PictureNode pic = null;

                try {
                    pic = new GetPictureFromServerClient(number, pos.latitude, pos.longitude).getPictures();
                }catch(Exception e){

                }

*/
                return false;
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

/*        new CreateGroupClient("Group", number, name);
        new InviteClient(number, "" + 1234);
        new InviteClient(number, "" + 1111);*/

        setUpMapIfNeeded();
        new InviteClient(LonelyMapActivity.getThisNumber(), "1234");
    }

    public static String getThisNumber(){
        return number;
    }

    public void enableStrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }


    private void test() {

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data){
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode == 0){
            if(resultCode == Activity.RESULT_OK){
                Uri contact = data.getData();
                Cursor c = getContentResolver().query(contact, null, null, null, null);


                if(c.moveToNext()){
                    int columnIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
                    String ID = c.getString(columnIndex);

                    int hasPhoneNumber = c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

                    String has = c.getString(hasPhoneNumber);

                    if(has.equalsIgnoreCase("1")){
                        Cursor cn = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ID, null, null);
                        if(cn.moveToNext()){
                            int cin = cn.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            String number = cn.getString(cin);
                            new InviteClient(LonelyMapActivity.getThisNumber(), "" + number);

                        }
                    }
                }
        }
        }
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

    private void startPeriodicUpdate() {
        updateHandler.post(update);
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
                new UpdatingClient(number, gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
                String info = new GetGroupLocationClient(number).getInfoFromRequest();
                String pics = new GetGroupPictureLocationClient(number).getInfoFromRequest();
                mMap.clear();
                setUpMap(info, pics);
                updateHandler.postDelayed(update, 5000);
        }
    };

    /**
     * This is where we can add markers or lines, add listeners or move the camera
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {


        LatLng current = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());


        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(current).title("test").snippet("test").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
      /*  mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-130, -120))
                .title("USER 1")
                .snippet("This is User 1's current location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));*/
        //this sets the view of the map zoomed such that the user's current
        //location and the second users location is visible.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // This addMarker sets the location of the second User in Downtown SF.
    }

    private void setUpMap(String users, String pics) {
        ArrayList<User> group = new ArrayList<User>();
        ArrayList<Tag> pictureArray = new ArrayList<Tag>();
        String curr = "";
        int in = 0;
        String userinfo[] = new String[4];
        StringTokenizer tokenizer = new StringTokenizer(users);
        System.out.println("TOKENIZERS: " + pics);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude()))
                .title("USER 1")
                .snippet("This is User 1's current location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        try {
            int tokens = 0;
            //4 tokens per user
            while (tokenizer.hasMoreTokens()) {
                String name = tokenizer.nextToken();
                String number = tokenizer.nextToken();
                double la = Double.parseDouble(tokenizer.nextToken());
                double lo = Double.parseDouble(tokenizer.nextToken());

                User user = new User();
                user.setUsername(name);
                user.setPhoneNumber(number);
                user.setWorldPoint(la, lo);

                group.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tokenizer = new StringTokenizer(pics);
        try{
            while(tokenizer.hasMoreTokens()){
                String name = tokenizer.nextToken();
                String strla = tokenizer.nextToken();
                double la = Double.parseDouble(tokenizer.nextToken());
                double lo = Double.parseDouble(tokenizer.nextToken());



                pictureArray.add(new Tag(name, la, lo));
            }
        }catch(Exception e){

        }

        LatLng current = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());

        for (int i = 0; i < group.size(); i++) {
            User user = group.get(i);
            if(!user.getPhoneNumber().startsWith(number)) {
                LatLng lat = new LatLng(user.getLat(), user.getLong());
                mMap.addMarker(new MarkerOptions().position(lat).title(user.getUserName()).snippet(user.getPhoneNumber()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }

        for (int i = 0; i < pictureArray.size(); i++) {
            Tag user = pictureArray.get(i);
            LatLng lat = new LatLng(user.getLat(), user.getLon());
            mMap.addMarker(new MarkerOptions().position(lat).title(user.getTag()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }

       /* // This set the function for long press on the map.
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String title = "Test tag ";
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(title)
                        .snippet("need to add something for them to title"));
                lat = latLng.latitude;
                lng = latLng.longitude;
                Toast.makeText(getApplicationContext(),
                        "NEW MARKER SET!",
                        Toast.LENGTH_LONG).show();

                new AddTagToGroupClient(number, title, ""+lat, ""+lng);
            }

        });

        mMap.setOnMarkerClickListener(new OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(Marker marker) {
                *//*LatLng pos = marker.getPosition();
                PictureNode pic = null;

                try {
                    pic = new GetPictureFromServerClient(number, pos.latitude, pos.longitude).getPictures();
                }catch(Exception e){

                }

*//*
                return false;
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

        );*/




        //mMap.getMyLocation();
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
    }

    //Creates the menu options in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.group_menu_map, menu);
            return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Creates a new Group and Activity
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(GroupMapActivity.this, Settings.class);
                GroupMapActivity.this.startActivity(settings);
                return true;

            case R.id.action_chat:
                Intent chat = new Intent(GroupMapActivity.this, ChatBubbleActivity.class);
                GroupMapActivity.this.startActivity(chat);
                return true;

            case R.id.action_camera:
                Intent camera = new Intent(GroupMapActivity.this, CameraActivity.class);
                GroupMapActivity.this.startActivity(camera);
                return true;
        }

        return false;


        //send button*/

    }
}








