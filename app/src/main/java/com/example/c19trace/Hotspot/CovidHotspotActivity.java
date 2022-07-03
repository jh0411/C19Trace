package com.example.c19trace.Hotspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.example.c19trace.Home.HomeFragment;
import com.example.c19trace.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.annotations.NotNull;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CovidHotspotActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private FusedLocationProviderClient locationProviderClient;
    private int GPS_REQUEST_CODE;
    private boolean isPermissionGranted;

    EditText location;
    ImageButton search;
    ZoomControls zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_hotspot);

        locationProviderClient = new FusedLocationProviderClient(CovidHotspotActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(CovidHotspotActivity.this);

        location = findViewById(R.id.et_Location);
        search = findViewById(R.id.ib_search);
        zoom = findViewById(R.id.zoomcontrol_map);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_location = location.getText().toString();
                Geocoder geocoder = new Geocoder(CovidHotspotActivity.this, Locale.getDefault());

                try {
                    List<Address> addressList = geocoder.getFromLocationName(user_location,10);

                    if(addressList.size() > 0){
                        Address address = addressList.get(0);
                        goToLocation(address.getLatitude(), address.getLongitude());

                        googleMap.addMarker(new MarkerOptions().position(new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude())));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.moveCamera(CameraUpdateFactory.zoomOut());
            }
        });
    }

    private void getCurrentLocation(){
        checkPermission();
        locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Location location = task.getResult();
                if(location == null){
                    Intent intent = new Intent(CovidHotspotActivity.this, HomeFragment.class);
                    startActivity(intent);
                    Toast.makeText(CovidHotspotActivity.this,"Location not Found!",Toast.LENGTH_SHORT).show();
                }
                else{
                    goToLocation(location.getLatitude(), location.getLongitude());
                }
            }
        });
    }

    private void goToLocation(double latitude, double longitude){
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        MarkerOptions options = new MarkerOptions().position(latLng);
        googleMap.addMarker(options);
        googleMap.moveCamera(cameraUpdate);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void initMap(){
        checkPermission();
        if(isPermissionGranted){
            if(isGPSEnabled()){
                SupportMapFragment supportMapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment_map);
                supportMapFragment.getMapAsync((OnMapReadyCallback) this);
                getCurrentLocation();
            }
        }
    }

    private boolean isGPSEnabled(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(providerEnable){
            return true;
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for C19Trace to work. Please enable GPS.")
                    .setPositiveButton("Yes",((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent,GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
        }
        return false;
    }

    private void checkPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(CovidHotspotActivity.this,"Permission Granted", Toast.LENGTH_SHORT).show();
                        isPermissionGranted = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(CovidHotspotActivity.this,"Permission Denied", Toast.LENGTH_SHORT).show();
                        isPermissionGranted = false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }


    public void onMapReady(@NonNull @NotNull GoogleMap map) {
        checkPermission();
        googleMap = map;
        googleMap.setMyLocationEnabled(true);
    }
}