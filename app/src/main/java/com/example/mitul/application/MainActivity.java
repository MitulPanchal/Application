package com.example.mitul.application;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitul.application.Adapter.StationAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    DataHelper dataHelper;
    private ConstraintLayout rootLayout;

    private int REQUEST_LOCATION = 1;
    private int REQUEST_PERMISSION_SETTING = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rootLayout = findViewById(R.id.rootMainLayout);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headView = navigationView.getHeaderView(0);
        TextView profileName = (TextView) headView.findViewById(R.id.username);
        profileName.setText("mitulpanchal@gmail.com");

        //profileName.setTextColor(0xff0000ff);

        LinearLayout header = (LinearLayout) findViewById(R.id.header);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        if (googleServiceAvailable()) {
            init();
        } else {
            //no layout
        }
    }

    public void init() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServiceAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant Connect to Play Service", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //stationContainer = findViewById(R.id.station_container);
        dataHelper = new DataHelper(getApplicationContext());
        switch (id) {
            case R.id.nav_login:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.nav_register:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivity(intentRegister);
                break;

            case R.id.nav_search:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                break;

            case R.id.nav_all_station:
                Intent intentStation = new Intent(this, StationActivity.class);
                startActivity(intentStation);
                break;

            case R.id.nav_feedback:
                Intent intentFeedback = new Intent(this, FeedActivity.class);
                startActivity(intentFeedback);
                break;

            case R.id.nav_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                break;

            case R.id.fare:
                Intent intentFare = new Intent(this, FareActivity.class);
                startActivity(intentFare);
                break;

            case R.id.ticket:
                startActivity(new Intent(this, TicketActivity.class));
                break;

            case R.id.nav_bus:
                startActivity(new Intent(this, BusActivity.class));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                addMarkers();
            }
        } else {
            addMarkers();
        }
    }

    private void addMarkers() {
        Location location = new Location("");
        LatLng my = new LatLng(location.getLatitude(), location.getLongitude());

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
        mGoogleMap.setMyLocationEnabled(true);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(my, 13);
        mGoogleMap.moveCamera(cameraUpdate);

        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                build();
        mGoogleApiClient.connect();

        List<StationInfo> stationData = new ArrayList<>();

        //stationData.addAll(dataHelper.getAllStation());


        LatLng station1 = new LatLng(21.168605,72.822404);
        mGoogleMap.addMarker(new MarkerOptions().position(station1));

        LatLng station2 = new LatLng(21.163122,72.812147);
        mGoogleMap.addMarker(new MarkerOptions().position(station2));

        LatLng station3 = new LatLng(21.205138,72.840729);
        mGoogleMap.addMarker(new MarkerOptions().position(station3).title("Railway Station"));

        LatLng station4 = new LatLng(21.194735,72.843647);
        mGoogleMap.addMarker(new MarkerOptions().position(station4).title("Sahara Darwaja"));

        LatLng station5 = new LatLng(21.183424,72.830657);
        mGoogleMap.addMarker(new MarkerOptions().position(station5).title("Udhna Darwaja"));

        LatLng stayion6 = new LatLng(21.144093, 72.848720);
        mGoogleMap.addMarker(new MarkerOptions().position(stayion6).title("Udhna"));

        LatLng station7 = new LatLng(21.148576, 72.806491);
        mGoogleMap.addMarker(new MarkerOptions().position(station7).title("Althan"));

        LatLng station8 = new LatLng(21.159302, 72.774219);
        mGoogleMap.addMarker(new MarkerOptions().position(station8).title("Piplod"));

        LatLng station9 = new LatLng(21.145694, 72.759113);
        mGoogleMap.addMarker(new MarkerOptions().position(station9).title("V.R.Mall"));

        LatLng station10 = new LatLng(21.229404, 72.833270);
        mGoogleMap.addMarker(new MarkerOptions().position(station10).title("Katargam"));

        LatLng station11 = new LatLng(21.192596, 72.801856);
        mGoogleMap.addMarker(new MarkerOptions().position(station11).title("Adajan"));

        LatLng station12 = new LatLng(21.206324, 72.849549);
        mGoogleMap.addMarker(new MarkerOptions().position(station12).title("Varaccha"));

        LatLng station13 = new LatLng(21.198078, 72.829923);
        mGoogleMap.addMarker(new MarkerOptions().position(station13).title("Chowk Bazar"));
    }

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        LatLng surat = new LatLng(21.1702, 72.8311);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(surat, 10);
        mGoogleMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null){
            Toast.makeText(this, "Can't get current Location", Toast.LENGTH_LONG).show();
        }
        else{
            /*LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
            mGoogleMap.moveCamera(cameraUpdate);
            mGoogleMap.animateCamera(cameraUpdate);*/
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_LOCATION) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    addMarkers();
                }
                else if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                {
                    if (shouldShowRequestPermissionRationale(permissions[i])) {
                        Toast.makeText(MainActivity.this, "Permission Required", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Snackbar.make(rootLayout, "This Permission is required", Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.RED)
                                .setAction("Settings", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri =  Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    }
                                })
                                .show();
                    }
                }
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PERMISSION_SETTING && (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
            addMarkers();
        }
    }
}
