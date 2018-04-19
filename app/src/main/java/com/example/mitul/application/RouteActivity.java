package com.example.mitul.application;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitul.application.api.directionapi.DirectionClient;
import com.example.mitul.application.api.directionapi.response.GoogleDirectionResponse;
import com.example.mitul.application.api.directionapi.response.Route;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RouteActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener{

    DataHelper dataHelper;
    LocationRequest mLocationRequest;
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;

    double longitudeSource,latitudeSource;
    double end_latitude, end_longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (googleServiceAvailable()) {
            init();
        }

        Button btnBook = findViewById(R.id.btnbookroute);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQr = new Intent(RouteActivity.this, TicketActivity.class);
                startActivity(intentQr);
            }
        });
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

    public void init() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Intent intent = getIntent();
        dataHelper = new DataHelper(getApplicationContext());
        String source = intent.getExtras().getString("source");
        String destination = intent.getExtras().getString("destination");

        TextView textView1 = (TextView) findViewById(R.id.textViewDes);
        TextView textView2 = (TextView) findViewById(R.id.textViewSou);
        TextView textView6 = findViewById(R.id.textView6);

        textView1.setText(destination);
        textView2.setText(source);

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
        mGoogleMap.setMyLocationEnabled(true);
        Location location = new Location("");
        LatLng my = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(my, 13);
        mGoogleMap.moveCamera(cameraUpdate);

        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                build();
        mGoogleApiClient.connect();

        latitudeSource = dataHelper.latitudeStation(source);
        longitudeSource = dataHelper.longitudeStation(source);
        end_latitude = dataHelper.latitudeStation(destination);
        end_longitude = dataHelper.longitudeStation(destination);

        LatLng sStation = new LatLng(latitudeSource,longitudeSource);
        mGoogleMap.addMarker(new MarkerOptions().position(sStation));

        float results[] = new float[10];
        Location.distanceBetween(latitudeSource,longitudeSource,end_latitude,end_longitude,results);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        markerOptions.position(new LatLng(end_latitude,end_longitude));
        markerOptions.title("Destination");
        markerOptions.snippet("Distance: " +results[0]/1000 + "km" );
        String d = String.valueOf(results[0]/1000);
        mGoogleMap.addMarker(markerOptions);
        textView4.setText(d);
       drawline(latitudeSource+","+longitudeSource, end_latitude+","+end_longitude);
    }

    private void drawline(String origin, String destination) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DirectionClient myClient = retrofit.create(DirectionClient.class);

        Call<GoogleDirectionResponse> call = myClient.getDirections(origin, destination, "AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g");

        call.enqueue(new Callback<GoogleDirectionResponse>() {
            @Override
            public void onResponse(Call<GoogleDirectionResponse> call, Response<GoogleDirectionResponse> response) {
                Log.e("Response", "Yes");

                GoogleDirectionResponse directionResponse = response.body();
                List<Route> routes = directionResponse.getRoutes();
                for (int i =0; i<routes.size(); i++){
                    String distance = routes.get(i).getLegs().get(i).getDistance().getText();
                    Log.e("Distance", distance);
                    String time = routes.get(i).getLegs().get(i).getDuration().getText();
                    String poly = routes.get(0).getOverviewPolyline().getPoints();
                    List<LatLng> latLngList = decodePoly(poly);
                    Polyline polyline = mGoogleMap.addPolyline(new PolylineOptions()
                            .addAll(latLngList)
                            .width(10)
                            .color(Color.BLACK)
                            .geodesic(true));
                }
            }

            @Override
            public void onFailure(Call<GoogleDirectionResponse> call, Throwable t) {
                Log.e("Response", "No");
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

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
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(surat, 12);
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

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
